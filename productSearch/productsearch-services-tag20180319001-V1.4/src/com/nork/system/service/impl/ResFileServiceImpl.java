package com.nork.system.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.properties.AppProperties;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.StructureProduct;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.StructureProductService;
import com.nork.sync.model.ResEntity;
import com.nork.system.dao.ResFileMapper;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResFile;
import com.nork.system.model.search.ResFileSearch;
import com.nork.system.service.ResFileService;

/**   
 * @Title: ResFileServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-文件资源库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-07-02 17:36:00
 * @version V1.0   
 */
@Service("resFileService")
@Transactional
public class ResFileServiceImpl implements ResFileService {

	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	private StructureProductService structureProductService;
	
	private ResFileMapper resFileMapper;

	private static Logger logger = Logger.getLogger(ResFileServiceImpl.class);
	
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	@Autowired
	private DesignPlanService designPlanService;

	@Autowired
	public void setResFileMapper(
			ResFileMapper resFileMapper) {
		this.resFileMapper = resFileMapper;
	}

	
	/**
	 * 新增数据
	 *
	 * @param resFile
	 * @return  int 
	 */
	@Override
	public int add(ResFile resFile) {
		resFileMapper.insertSelective(resFile);
		return resFile.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resFile
	 * @return  int 
	 */
	@Override
	public int update(ResFile resFile) {
		return resFileMapper
				.updateByPrimaryKeySelective(resFile);
	}
	
	/**
	 *    更新数据
	 *
	 * @param resFile
	 * @return  int 
	 */
	@Override
	public int update(ResFile resFile,Integer businessId,String fileKey) {
		//获取多个 resPic filekeys ,busniessIds
		String key = resFile.getFileKey();
		Integer bId = resFile.getBusinessId();
		//当前资源 业务Id不为空，且fileKeys为空，就必须给fileKeys赋值 
		if(bId != null && bId > 0 && !bId.equals(businessId) && StringUtils.isBlank(resFile.getFileKeys())){
			resFile.setFileKeys(key);
		}
		String fileKeys = "" ;
		//如果fileKeys不为空，累加fileKeys
		if(StringUtils.isNotBlank(resFile.getFileKeys())){
			fileKeys = resFile.getFileKeys()+","+fileKey;
		}else{
			fileKeys = fileKey+"";
		}
		resFile.setFileKeys(fileKeys);
		
		String businessIds = "";
		//业务Ids为空或不为空，则赋值或累加
		if( bId != null && bId > 0 && !bId.equals(businessId) && StringUtils.isBlank(resFile.getBusinessIds()) ){
			businessIds = resFile.getBusinessId()+","+businessId;
			resFile.setBusinessId(0);
		}else if(StringUtils.isNotBlank(resFile.getBusinessIds())){
			businessIds = resFile.getBusinessIds()+","+businessId;
			resFile.setBusinessId(0);
		}else{
			businessIds = businessId+"";
			resFile.setBusinessId(businessId);
		}
		resFile.setBusinessIds(businessIds);
		return resFileMapper.updateByPrimaryKeySelective(resFile);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		/*删除物理文件*/
		ResFile resFile=get(id);
		if(resFile!=null){
			String filePath = app.getString("")+resFile.getFilePath();
			File file = new File(filePath);
			if(file.exists())
				file.delete();
		}
		return resFileMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResFile 
	 */
	@Override
	public ResFile get(Integer id) {
		return resFileMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resFile
	 * @return   List<ResFile>
	 */
	@Override
	public List<ResFile> getList(ResFile resFile) {
	    return resFileMapper.selectList(resFile);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resFile
	 * @return   int
	 */
	@Override
	public int getCount(ResFileSearch resFileSearch){
		return  resFileMapper.selectCount(resFileSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resFile
	 * @return   List<ResFile>
	 */
	@Override
	public List<ResFile> getPaginatedList(
			ResFileSearch resFileSearch) {
		return resFileMapper.selectPaginatedList(resFileSearch);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 将图片信息记录到数据库中
	 */
	@Override
	public void saveFiles(String planId,List<Map> list,String level){
		if(StringUtils.isNotBlank(planId) ){
			DesignPlan designPlan = designPlanService.get(Integer.valueOf(planId));
			ResFile resFile = null;
			for( Map map : list ){
				String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
				String fileName = map.get("fileName").toString();
				resFile = new ResFile();
				resFile.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				resFile.setFileCode(resFile.getSysCode());
				resFile.setGmtCreate(new Date());
				resFile.setGmtModified(new Date());
				resFile.setCreator(designPlan.getCreator());
				resFile.setModifier(designPlan.getCreator());
				resFile.setIsDeleted(0);
				resFile.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
				resFile.setFileSize(map.get("size").toString());
				resFile.setFileSuffix(fileName.substring(fileName.lastIndexOf(".")));
				resFile.setBusinessId(Integer.valueOf(planId));
				resFile.setFileOriginalName(dbFilePath.substring(dbFilePath.lastIndexOf("/") + 1, dbFilePath.lastIndexOf(".")));
				resFile.setFilePath(dbFilePath);
				resFile.setFileKey(map.get(FileUploadUtils.UPLOADPATHTKEY).toString());
				resFile.setFileLevel(level);
				resFile.setFileType("无");
				int rows = resFileMapper.insertSelective(resFile);
				//将图片信息记录到数据库中
			}
		}
	}
	
	/**
	 * 回填文件资源的businessId
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void backFillResFile(Integer businessId,Integer resFileId,String fileKey){
		ResFile resFile = this.get(resFileId);
		if( resFile != null ) {
			this.update(resFile,businessId,fileKey);
		}
	}
	
	/**
	 * 删除当前文件资源信息
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void clearBackfillResFile(Integer businessId,Integer resFileId){
		StringBuffer bidSb = new StringBuffer();
		StringBuffer keySb = new StringBuffer();
		ResFile resFile = this.get(resFileId);
		if( resFile != null ) {
			String bids = resFile.getBusinessIds();
			String keys = resFile.getFileKeys();
			if( bids != null ){
				String bidArr[] = bids.split(",");
				String keyArr[] = keys.split(",");
				for( int i=0;i<bidArr.length;i++ ){
					if(!bidArr[i].equals(businessId+"")){
						bidSb.append(bidArr[i]).append(",");
						keySb.append(keyArr[i]).append(",");
					}
				}
				String bid = bidSb.toString();
				String key = keySb.toString();
				if(bid.length()>0){
					bid = bid.substring(0, bid.length()-1);
					key = key.substring(0, key.length()-1);
				}
				resFile.setBusinessIds(bid);
				resFile.setFileKeys(key);
				//判断bid，给businessId赋值
				if(StringUtils.isNotBlank(bid)){
					if(bid.contains(","))
						resFile.setBusinessId(0);
					else
						resFile.setBusinessId(Utils.getIntValue(bid));
				}else{
					resFile.setBusinessId(0);
				}
				this.update(resFile);
			}
		}
	}

	@Override
	public int filePathCount(String filePath) {
		return resFileMapper.filePathCount(filePath);
	}

	@Override
	public void setBusinessId(Integer materialFileId, Integer id) {
		ResFile resFile=get(materialFileId);
		if(resFile!=null){
			resFile.setBusinessId(id);
			update(resFile);
		}
	}

	@Override
	public boolean updatePath(Integer configFileId, String code) {
		ResFile resFile=get(configFileId);
		if(resFile==null){
			return false;
		}
		String fileKey=resFile.getFileKey();
		String correctFilePath=app.getString(fileKey+".upload.path");
		correctFilePath=correctFilePath.replace("[code]", code);
		logger.info("------correctFilePath:"+correctFilePath);
		if(resFile.getFilePath().indexOf(correctFilePath)==0){
			/*不用更新*/
			return false;
		}else{
			/*需要更新*/
			/*复制本地文件*/
			String fileName=resFile.getFilePath().substring(resFile.getFilePath().lastIndexOf("/")+1,resFile.getFilePath().length());
			String localUrl1=Tools.getRootPath(resFile.getFilePath(), "")+resFile.getFilePath();//原文件路径
			String localUrl2=Tools.getRootPath(correctFilePath, "")+correctFilePath+fileName;//更新后文件路径
			try {
				FileUploadUtils.copyFile2(localUrl1, localUrl2);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			/*复制ftp文件*/
			int type=Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD);
			if(type==2||type==3){
				FtpUploadUtils.copyFileFromFtp(resFile.getFilePath(), correctFilePath);
			}
			resFile.setFilePath(correctFilePath+fileName);
			update(resFile);
			return true;
		}
	}

	@Override
	public Integer copyFile(Integer fileId, HttpServletRequest request) {
		ResFile resFile = get(fileId);
		if(resFile==null){
			return 0;
		}
		resFile.setFileCode(null);
		resFile.setSysCode(null);
		sysSave(resFile, request);
		resFile.setId(null);
		resFile.setFileKeys(null);
		resFile.setBusinessIds(null);
		resFile.setBusinessId(null);
		add(resFile);
		return resFile.getId();
		
	}
	
	@Override
	public ResEntity selectResEntity(Integer id){
		return resFileMapper.selectResEntity(id);
	}

	/**
	 * 保存ResEntity对象
	 * @param resEntity
	 * @return
	 */
	@Override
	public int insertEntity(ResEntity resEntity){
		resFileMapper.insertEntity(resEntity);
		return resEntity.getId();
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResFile model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }
				 
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
		}
	}
	
	
	
	/**
	 * 处理原原件
	 */
	public void processingSourceFile(){
		String delPath = Utils.getValueByFileKey(AppProperties.APP, AppProperties.DEL_RESOURCE_BAK, "/del_resource_bak");
		String uploadPath = Utils.getValueByFileKey(AppProperties.APP, AppProperties.UPLOAD_ROOT_FILEKEY, "");
		if(StringUtils.isEmpty(delPath)){
			logger.error(" fileCopy method : app.properties not found " + AppProperties.DEL_RESOURCE_BAK);
			return;
		}
		if(StringUtils.isEmpty(uploadPath)){
			logger.error(" fileCopy method : app.properties not found " + AppProperties.UPLOAD_ROOT_FILEKEY);
			return;
		}
		String srcUploadPath = uploadPath + "/src";
		if(!new File(srcUploadPath).exists()){
			logger.error(" fileCopy method : path not found " + srcUploadPath);
			return;
		}
        copy(srcUploadPath,delPath+"/src");  
        logger.info("文件拷贝完成!");
	} 
 
  
    private static void copy(String src, String des) {  
        File file1 = new File(src);  
        File[] fs = file1.listFiles();  
        File file2 = new File(des);  
        if(!file2.exists()){  
            file2.mkdirs();  
        }  
        for (File f : fs) {  
            if(f.isFile()){  
                fileCopy(f,des+"/"+f.getName());  
            }else if(f.isDirectory()){  
                copy(f.getPath(),des+"/"+f.getName());  
            }
        }  
    }  
    

    private static void fileCopy(File file, String des) {  
    	String src = file.getPath();
        BufferedReader br= null;  
        PrintStream ps = null;  
        
        boolean flag = false;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));  
            ps = new PrintStream(new FileOutputStream(des));  
            String s = null;  
            while((s = br.readLine()) != null){  
                ps.println(s);  
                ps.flush();  
            }
            flag = true;
            logger.info("processingSourceFile success !" + file.getName());
        	System.out.println("processingSourceFile success !" + file.getName());
        } catch (FileNotFoundException e) {
        	logger.error(file.getName() + " processingSourceFile succee !!!:" + e);  
        } catch (IOException e) {  
        	logger.error(file.getName() + " processingSourceFile succee !!!:" + e);  
        }finally{
                try {
                    if(br != null)  br.close();  
                    if(ps != null)  ps.close();  
                    if(flag){
                    	file.delete();
                    }
                } catch (IOException e) {
                	logger.error(" fileCopy method :" + e);  
                }
        }   
    }  
    
    
    
    
    public static void main(String[] args) {
    	processingDeletedFile();
    }
    
    
    /**
     * 定时处理删除30天后的文件
     */
    public static void processingDeletedFile(){
    	
    	String delPath = Utils.getValueByFileKey(AppProperties.APP, AppProperties.DEL_RESOURCE_BAK, "/del_resource_bak");
    	if(StringUtils.isEmpty(delPath)){
			logger.error(" fileCopy method : app.properties not found " + AppProperties.DEL_RESOURCE_BAK);
			return;
		}
    	try{
	    	Calendar cal_today = Calendar.getInstance();    
	    	cal_today.setTime(new Date()); 
	    	long today = cal_today.getTimeInMillis();       
	    	File file = new File(delPath);
	    	File[] fileList = file.listFiles();
	    	logger.info("开始清理,最后修改时间为30天前的文件!");
	    	processing(today,fileList);
	    	logger.info("最后修改时间为30天前的文件,清理完成!");
    	}catch (Exception e) {
    		logger.error(" processingDeletedFile method :" + e);  
		}
    }
  
	

    public static void processing(long today ,File[] fileList){
    	for (File file_ : fileList) { 
    		if(file_.isFile()){  
        		long file_time = file_.lastModified();   
        		long between_days = (today - file_time)/( 1000 * 3600 * 24); 
        		if(Integer.parseInt(String.valueOf(between_days)) >= 30){
        			file_.delete();
        		}
    		}else if(file_.isDirectory()){  
    			processing(today,file_.listFiles());
    		}
    	}
    }


    /**
	 * 将结构拼花文件信息保存到数据库中
	 * @author zhaobl
	 * @param designPlan
	 * @param map
	 * @return
	 */
	@Override
	public boolean saveStructureSpellingFlowerFile(StructureProduct structureProduct, Map map){
		if( map != null && map.size() > 0 ){
			ResFile resFile = new ResFile();
			String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resFile.setSysCode(structureProduct.getSpaceCode());
			resFile.setFileCode(structureProduct.getSpaceCode());
			resFile.setGmtCreate(new Date());
			resFile.setGmtModified(new Date());
			resFile.setCreator(structureProduct.getCreator());
			resFile.setModifier(structureProduct.getCreator());
			resFile.setIsDeleted(0);
			resFile.setFileName(fileName);
			resFile.setFileSize(map.get(FileModel.FILE_SIZE).toString());
			resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resFile.setBusinessId(Integer.valueOf(structureProduct.getId()));
			resFile.setFileOriginalName(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			resFile.setFilePath(dbFilePath);
			resFile.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resFile.setFileLevel("0");
			resFile.setFileType("结构拼花文件");
			Integer id = 0;
			if(structureProduct.getSpellingFlowerFileId()!=null && structureProduct.getSpellingFlowerFileId().intValue() > 0) {
				ResFile resFile_ = this.get(structureProduct.getSpellingFlowerFileId());
				if(resFile_ != null) {
					resFile_.setId(structureProduct.getSpellingFlowerFileId());
					this.update(resFile_);
				}else {
					id = this.add(resFile);
				}
			}else {
				id = this.add(resFile);
			}
			if( id > 0 ) {
				StructureProduct newStructureProduct = new StructureProduct();
				newStructureProduct.setId(structureProduct.getId());
				newStructureProduct.setSpellingFlowerFileId(id);
				structureProductService.update(newStructureProduct);
			}
		}else{
			return false;
		}
		return  true;
	}
	
	
	
	
	/**
	 * 将产品拼花文件信息保存到数据库中
	 * @author zhaobl
	 * @param designPlan
	 * @param map
	 * @return
	 */
	@Override
	public boolean saveProductSpellingFlowerFile(BaseProduct baseProduct, Map map){
		if( map != null && map.size() > 0 ){
			ResFile resFile = new ResFile();
			String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resFile.setSysCode(baseProduct.getProductCode());
			resFile.setFileCode(baseProduct.getProductCode());
			resFile.setGmtCreate(new Date());
			resFile.setGmtModified(new Date());
			resFile.setCreator(baseProduct.getCreator());
			resFile.setModifier(baseProduct.getCreator());
			resFile.setIsDeleted(0);
			resFile.setFileName(fileName);
			resFile.setFileSize(map.get(FileModel.FILE_SIZE).toString());
			resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resFile.setBusinessId(Integer.valueOf(baseProduct.getId()));
			resFile.setFileOriginalName(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
			resFile.setFilePath(dbFilePath);
			resFile.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resFile.setFileLevel("0");
			resFile.setFileType("产品拼花文件");
			Integer id = 0;
			if(baseProduct.getSpellingFlowerFileId()!=null && baseProduct.getSpellingFlowerFileId().intValue() > 0) {
				ResFile resFile_ = this.get(baseProduct.getSpellingFlowerFileId());
				if(resFile_ != null) {
					resFile_.setId(baseProduct.getSpellingFlowerFileId());
					this.update(resFile_);
				}else {
					id = this.add(resFile);
				}
			}else {
				id = this.add(resFile);
			}
			if( id > 0 ) {
				BaseProduct newBaseProduct = new BaseProduct();
				newBaseProduct.setId(baseProduct.getId());
				newBaseProduct.setSpellingFlowerFileId(id);
				baseProductService.update(newBaseProduct);
			}
		}else{
			return false;
		}
		return  true;
	}
	
	
}
