
package com.nork.client.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.client.model.ResTextureResponse;
import com.nork.client.model.UploadFileResponse;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.DeCompressUtil;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignTemplet;
import com.nork.design.service.DesignTempletService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.GroupProduct;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.GroupProductService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.search.ResFileSearch;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;

/**
 * Created by zhangwj on 2016/9/13.
 */
@Controller
@RequestMapping("/{style}/client/file/")
public class FileController {

    Logger logger = Logger.getLogger(FileController.class);

    @Autowired
    private ResTextureService resTextureService;
    @Autowired
    private ResFileService resFileService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private ResModelService resModelService;
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private GroupProductService groupProductService;
    @Autowired
    private DesignTempletService designTempletService;

    /**
     * 上传文件
     * @param request
     * @param response
     * @param fileName
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public Object uploadFile(HttpServletRequest request, HttpServletResponse response,String fileName,String code,String msgId){
        if( StringUtils.isBlank(fileName) ){
            return new ResponseEnvelope<UploadFileResponse>(false, "参数fileName不能为空！", msgId);
        }
        
        logger.info("code---------------->"+code);
        logger.info("fileName---------------->"+fileName);
        logger.info("msgId---------------->"+msgId);
        
        // 文件后缀，用于判断文件类型
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        logger.info("fileSuffix---------------->"+fileSuffix);
        // 文件保存目录
        String savePath = "";
        String fileKey = "";
        if( "MaterialCfg".equals(fileSuffix) ){// 材质
        	fileKey = "product.material.upload.path";
            savePath = Utils.getPropertyName("client",fileKey,"/material/");
        }else if( "jpg".equalsIgnoreCase(fileSuffix) || "png".equalsIgnoreCase(fileSuffix) ){// 纹理
        	fileKey = "texture.upload.path";
            savePath = Utils.getPropertyName("client",fileKey,"/texture/");
        }else if( "ObjectCfg".equals(fileSuffix) ){// 产品
        	fileKey = "product.baseProduct.upload.path";
            savePath = Utils.getPropertyName("client",fileKey,"/product/baseProduct/");
        }else if( "ComboCfg".equals(fileSuffix) ){// 组合
        	fileKey = "product.productGroup.upload.path";
            savePath = Utils.getPropertyName("client",fileKey,"/product/groupProduct/");
        }else if( "SceneCfg".equals(fileSuffix) ){// 场景
        	fileKey = "scene.upload.path";
            savePath = Utils.getPropertyName("client",fileKey,"/scene/");
        }
        try {
        	savePath = Utils.replaceDate(savePath);
        	String name = fileName.substring(0, fileName.lastIndexOf("."));
            /*String filePath = Constants.UPLOAD_ROOT + savePath +name+"/"+ fileName;*/
        	String filePath = Utils.getAbsolutePath(savePath +name+"/"+ fileName, null);
            logger.info("filePath---------->"+filePath);
            File file = new File(filePath);
            if( !file.getParentFile().exists() ){
                file.getParentFile().mkdirs();
            }
            if( !file.exists() ){
                file.createNewFile();
            }

            // 保存文件
            ServletInputStream sis = request.getInputStream();
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] media = new byte[1024];
            int length = sis.read(media, 0, 1024);
            while( length != -1 ){
                fos.write(media, 0 ,length);
                length = sis.read(media, 0, 1024);
            }
            fos.close();
            sis.close();

            // 保存数据
            Map<String,String> map = FileUploadUtils.getMap(file,fileKey,false);
            
            logger.info("~~~~~~~~~~~~~~~~~~~fileSuffix~~~~~~~~~~~~~~~~~~~~~~"+fileSuffix);
            
            if( "MaterialCfg".equals(fileSuffix) ){// 材质
                ResFile resFile = new ResFile();
                resFile.setFileCode(fileName.substring(0,fileName.indexOf(".")));
                resFile.setFileDesc("材质配置文件");
                resFile.setFileName(map.get(FileModel.FILE_NAME));
                resFile.setFileKey(map.get(FileModel.FILE_KEY));
                resFile.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
                resFile.setFileSize(map.get(FileModel.FILE_SIZE));
                resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
                resFile.setFilePath(map.get(FileModel.FILE_PATH));
                sysSave(resFile,request);
                resFileService.add(resFile);
                ResTexture resTexture = new ResTexture();
                resTexture.setType("material");
                resTexture.setAtt1("material");
                resTexture.setPicId(resFile.getId());
                resTextureService.saveParamsByResFile(resTexture,resFile);
            }else if( "jpg".equalsIgnoreCase(fileSuffix) || "png".equalsIgnoreCase(fileSuffix) ){// 纹理
                ResPic resPic = new ResPic();
                resPic.setPicCode(fileName.substring(0,fileName.indexOf(".")));
                resPic.setPicDesc("纹理图片");
                resPic.setPicName(map.get(FileModel.FILE_NAME));
                resPic.setFileKey(map.get(FileModel.FILE_KEY));
                resPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME));
                resPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE)));
                resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX));
                resPic.setPicPath(map.get(FileModel.FILE_PATH));
                sysSave(resPic,request);
                resPicService.add(resPic);
                ResTexture resTexture = new ResTexture();
                resTexture.setAtt1("texture");
                resTexture.setPicId(resPic.getId());
                sysSave(resTexture,request);
                resTextureService.saveParamsByResPic(resTexture,resPic);
            }else if( "ObjectCfg".equals(fileSuffix) ){// 产品
            	logger.info("进入 ObjectCfg------>"+fileSuffix);
                ResFile resFile = new ResFile();
                resFile.setFileCode(fileName.substring(0,fileName.indexOf(".")));
                resFile.setFileDesc("产品配置文件");
                resFile.setFileName(map.get(FileModel.FILE_NAME));
                logger.info("map.get(FileModel.FILE_NAME)---------->"+map.get(FileModel.FILE_NAME));
                resFile.setFileKey(map.get(FileModel.FILE_KEY));
                resFile.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
                resFile.setFileSize(map.get(FileModel.FILE_SIZE));
                resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
                resFile.setFilePath(map.get(FileModel.FILE_PATH));
                sysSave(resFile,request);
                resFileService.add(resFile);
                int resId = resFile.getId();
                BaseProduct base = baseProductService.selectByProductCode(code);
                logger.info("base.getId()---------->"+base.getId());
                BaseProduct baseProduct = new BaseProduct();
                baseProduct.setProductCode(code);
                baseProduct.setConfigId(resId);
                baseProduct.setId(base.getId());
                baseProductService.update(baseProduct);
            }else if("ComboCfg".equals(fileSuffix) ){// 组合
            	ResFile resFile = new ResFile();
                resFile.setFileCode(fileName.substring(0,fileName.indexOf(".")));
                resFile.setFileDesc("组合配置文件");
                resFile.setFileName(map.get(FileModel.FILE_NAME));
                resFile.setFileKey(map.get(FileModel.FILE_KEY));
                resFile.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
                resFile.setFileSize(map.get(FileModel.FILE_SIZE));
                resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
                resFile.setFilePath(map.get(FileModel.FILE_PATH));
                sysSave(resFile,request);
                resFileService.add(resFile);
            }else if("SceneCfg".equals(fileSuffix) ){// 场景
            	ResFile resFile = new ResFile();
                resFile.setFileCode(fileName.substring(0,fileName.indexOf(".")));
                resFile.setFileDesc("场景配置文件");
                resFile.setFileName(map.get(FileModel.FILE_NAME));
                resFile.setFileKey(map.get(FileModel.FILE_KEY));
                resFile.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
                resFile.setFileSize(map.get(FileModel.FILE_SIZE));
                resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
                resFile.setFilePath(map.get(FileModel.FILE_PATH));
                sysSave(resFile,request);
                resFileService.add(resFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            return new ResponseEnvelope<UploadFileResponse>(uploadFileResponse,msgId,false);
        }
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        return new ResponseEnvelope<UploadFileResponse>(uploadFileResponse,msgId,true);
    }

    /**
     * 上传缩略图
     * @param request
     * @param type
     * @param fileName
     * @param msgId
     * @return
     */
    @RequestMapping(value = "/uploadThumbnail")
    @ResponseBody
    public Object uploadThumbnail(HttpServletRequest request,String type,String fileName,String msgId){
        if( StringUtils.isBlank(fileName) ){
            return new ResponseEnvelope<UploadFileResponse>(false, "参数fileName不能为空！", msgId);
        }
        if( StringUtils.isBlank(type) ){
            return new ResponseEnvelope<UploadFileResponse>(false, "参数type不能为空！", msgId);
        }
        // 文件保存目录
        String savePath = "";
        if( "material".equals(type) ){// 材质
            savePath = Utils.getPropertyName("client","product.material.pic.small.upload.path","/material/pic/small/");
        }else if( "texture".equalsIgnoreCase(type) ){// 纹理
            savePath = Utils.getPropertyName("client","texture.pic.small.upload.path","/texture/pic/small/");
        }else if( "product".equals(type) ){// 产品
            savePath = Utils.getPropertyName("client","product.baseProduct.pic.small.upload.path","/product/baseProduct/pic/small/");
        }else if( "group".equals(type) ){// 组合
            savePath = Utils.getPropertyName("client","product.productGroup.pic.small.upload.path","/product/groupProduct/pic/small/");
        }else if( "scene".equals(type) ){// 场景
            savePath = Utils.getPropertyName("client","scene.pic.small.upload.path","/scene/pic/small/");
        }
        try{
        	savePath = Utils.replaceDate(savePath);
        	String name = fileName.substring(0, fileName.lastIndexOf("."));
            /*String filePath = Constants.UPLOAD_ROOT + savePath + name + "/" + fileName;*/
        	String filePath = Utils.getAbsolutePath(savePath + name + "/" + fileName, null);
            // 保存文件
            File file = new File(filePath);
            if( !file.getParentFile().exists() ){
                file.getParentFile().mkdirs();
            }
            if( !file.exists() ){
                file.createNewFile();
            }
            ServletInputStream sis = request.getInputStream();
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] media = new byte[1024];
            int length = sis.read(media, 0, 1024);
            while( length != -1 ){
                fos.write(media, 0 ,length);
                length = sis.read(media, 0, 1024);
            }
            fos.close();
            sis.close();

            // 保存数据
            Map<String,String> map = FileUploadUtils.getMap(file,"product.material.upload.path",false);
            ResPic resPic = new ResPic();
            resPic.setPicCode(fileName.substring(0,fileName.indexOf(".")));
            resPic.setPicDesc("纹理图片");
            resPic.setPicName(map.get(FileModel.FILE_NAME));
            resPic.setFileKey(map.get(FileModel.FILE_KEY));
            resPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME));
            resPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE)));
            resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX));
            resPic.setPicPath(map.get(FileModel.FILE_PATH));
            sysSave(resPic,request);
            resPicService.add(resPic);

            // 回填到业务表中
            String businessCode = fileName.substring(0,fileName.lastIndexOf("."));
            if( "material".equals(type) ){// 材质
                ResTexture resTexture = new ResTexture();
                resTexture.setName(businessCode);
                List<ResTexture> list = resTextureService.getList(resTexture);
                if( list != null && list.size() > 0 ){
                    resTexture = list.get(0);
                    resTexture.setThumbnailId(resPic.getId());
                    resTextureService.update(resTexture);
                }
            }else if( "texture".equalsIgnoreCase(type) ){// 纹理

            }else if( "product".equals(type) ){// 产品
                BaseProduct baseProduct = new BaseProduct();
                baseProduct.setProductCode(businessCode);
                List<BaseProduct> list = baseProductService.getList(baseProduct);
                if( list != null && list.size() > 0 ){
                    baseProduct = list.get(0);
                    baseProduct.setPicId(resPic.getId());
                    baseProductService.update(baseProduct);
                }
            }else if( "group".equals(type) ){// 组合
                GroupProduct groupProduct = new GroupProduct();
                groupProduct.setGroupCode(businessCode);
                List<GroupProduct> list = groupProductService.getList(groupProduct);
                if( list != null && list.size() > 0 ){
                    groupProduct = list.get(0);
                    groupProduct.setPicId(resPic.getId());
                    groupProductService.update(groupProduct);
                }
            }else if( "scene".equals(type) ){// 场景
                DesignTemplet designTemplet = new DesignTemplet();
                designTemplet.setDesignCode(businessCode);
                List<DesignTemplet> list = designTempletService.getList(designTemplet);
                if( list != null && list.size() > 0 ){
                    designTemplet = list.get(0);
                    designTemplet.setThumbnailId(resPic.getId());
                    designTempletService.update(designTemplet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEnvelope<UploadFileResponse>(false, "上传缩略图异常！", msgId);
        }
        return new ResponseEnvelope<UploadFileResponse>(new UploadFileResponse(),msgId,true);
    }

    public ResPic sysSave(ResPic resPic,HttpServletRequest request){
        if(resPic != null){
            LoginUser loginUser = new LoginUser();
            if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
                loginUser.setLoginName("nologin");
            }else{
                loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
            }

            if(resPic.getId() == null){
                resPic.setGmtCreate(new Date());
                resPic.setCreator(loginUser.getLoginName());
                resPic.setIsDeleted(0);
                if(resPic.getSysCode()==null || "".equals(resPic.getSysCode())){
                    resPic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }

            resPic.setGmtModified(new Date());
            resPic.setModifier(loginUser.getLoginName());
        }
        return resPic;
    }

    public ResFile sysSave(ResFile resFile,HttpServletRequest request){
        if(resFile != null){
            LoginUser loginUser = new LoginUser();
            if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
                loginUser.setLoginName("nologin");
            }else{
                loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
            }

            if(resFile.getId() == null){
                resFile.setGmtCreate(new Date());
                resFile.setCreator(loginUser.getLoginName());
                resFile.setIsDeleted(0);
                if(resFile.getSysCode()==null || "".equals(resFile.getSysCode())){
                    resFile.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }

            resFile.setGmtModified(new Date());
            resFile.setModifier(loginUser.getLoginName());
        }
        return resFile;
    }

    public ResTexture sysSave(ResTexture resTexture,HttpServletRequest request){
        if(resTexture != null){
            LoginUser loginUser = new LoginUser();
            if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
                loginUser.setLoginName("nologin");
            }else{
                loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
            }

            if(resTexture.getId() == null){
                resTexture.setGmtCreate(new Date());
                resTexture.setCreator(loginUser.getLoginName());
                resTexture.setIsDeleted(0);
                if(resTexture.getSysCode()==null || "".equals(resTexture.getSysCode())){
                    resTexture.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }

            resTexture.setGmtModified(new Date());
            resTexture.setModifier(loginUser.getLoginName());
        }
        return resTexture;
    }

    /**
     * 解压配置文件
     * @param file
     * @param fileDir
     * @return
     */
    public static String doCompress(File file,String fileDir){
        String result = "";
        String fileName = file.getName().substring(0,file.getName().lastIndexOf("."));
        String filePath = file.getPath();
        String newFilePath = filePath.substring(0,filePath.lastIndexOf(".")) + ".rar";
        // 拷贝资源文件为zip格式
        FileUploadUtils.copyfile(filePath,newFilePath);
        File newFile = new File(newFilePath);
        //解压zip
        try {
            /*DeCompressUtil.deCompress(newFile.getPath(),Constants.UPLOAD_ROOT + fileDir,"解压失败！");*/
        	DeCompressUtil.deCompress(newFile.getPath(), Utils.getAbsolutePath(fileDir, null),"解压失败！");
            /*result = FileUploadUtils.getFileContext(Constants.UPLOAD_ROOT + fileDir + fileName + "\\" + fileName);*/
        	result = FileUploadUtils.getFileContext(Utils.getAbsolutePath(fileDir + fileName + "\\" + fileName, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     * FBX上传文件
     * @param request
     * @param response
     * @param fileName
     */
    @RequestMapping(value = "/uploadFbxFile")
    @ResponseBody
    public Object uploadFbxFile(HttpServletRequest request, HttpServletResponse response,String fileName,String fileCode,String filePath,String fileType,String msgId){
    	
    	logger.info("fileName---------->"+fileName);
    	logger.info("fileCode---------->"+fileCode);
    	logger.info("filePath---------->"+filePath);
    	logger.info("fileType---------->"+fileType);
    	logger.info("msgId---------->"+msgId);
    	
        if( StringUtils.isBlank(fileName) ){
            return new ResponseEnvelope<UploadFileResponse>(false, "参数fileName不能为空！", msgId);
        }
        if( StringUtils.isBlank(fileCode) ){
            return new ResponseEnvelope<UploadFileResponse>(false, "参数fileCode不能为空！", msgId);
        }
        if( StringUtils.isBlank(filePath) ){
            return new ResponseEnvelope<UploadFileResponse>(false, "参数filePath不能为空！", msgId);
        }
        if( StringUtils.isBlank(fileType) ){
            return new ResponseEnvelope<UploadFileResponse>(false, "参数fileType不能为空！", msgId);
        }
        // 文件后缀，用于判断文件类型
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        
        logger.info("fileSuffx---------->"+fileSuffix);
        try {
        	filePath = Utils.replaceDate(filePath);
            /*String fbxPath = Constants.UPLOAD_ROOT + filePath +"/"+ fileName;*/
        	String fbxPath = Utils.getAbsolutePath(filePath +"/"+ fileName, null);
            File file = new File(fbxPath);
            if( !file.getParentFile().exists() ){
                file.getParentFile().mkdirs();
            }
            if( !file.exists() ){
                file.createNewFile();
            }

            // 保存文件
            ServletInputStream sis = request.getInputStream();
            FileOutputStream fos = new FileOutputStream(fbxPath);
            byte[] media = new byte[1024];
            int length = sis.read(media, 0, 1024);
            while( length != -1 ){
                fos.write(media, 0 ,length);
                length = sis.read(media, 0, 1024);
            }
            fos.close();
            sis.close();

            // 保存数据
            Map<String,String> map = FileUploadUtils.getMap(file,filePath,false);
            if( "fbx".equals(fileSuffix.toLowerCase()) ){// FBX文件
                ResFile resFile = new ResFile();
                resFile.setFileCode(fileName.substring(0,fileName.indexOf(".")));
                resFile.setFileDesc("FBX配置文件");
                resFile.setFileName(map.get(FileModel.FILE_NAME));
                resFile.setFileKey(map.get(FileModel.FILE_KEY));
                resFile.setFileOriginalName(map.get(FileModel.FILE_ORIGINAL_NAME));
                resFile.setFileSize(map.get(FileModel.FILE_SIZE));
                resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX));
                resFile.setFilePath(map.get(FileModel.FILE_PATH));
                sysSave(resFile,request);
                int i = resFileService.add(resFile);
                if( i>0 ){
                	logger.info("fileType----11111------>"+fileType);
                	if ("product".equals(fileType)) {
		                // 通过产品编码查找产品
		                BaseProduct baseProduct = new BaseProduct();
		                baseProduct.setProductCode(fileCode);
		                List<BaseProduct> list = baseProductService.getList(baseProduct);
		                if( list != null && list.size() > 0 ){
		                    baseProduct = list.get(0);
		                    BaseProduct bp = new BaseProduct();
		                    bp.setId(baseProduct.getId());
		                    logger.info("产品ID:"+baseProduct.getId()+" baseProduct.getFbxFileId()--------->"+baseProduct.getFbxFileId());
		                    if(baseProduct.getFbxFileId() != null && baseProduct.getFbxFileId() != 0){
                                bp.setFbxState(1);//已处理
		                    }else{
                                bp.setFbxState(0);//未处理
		                    }
                            bp.setFbxFileId(resFile.getId());
                            baseProductService.update(bp);
                            ResFile resFile_f  = new ResFile();
                            resFile_f.setId(i);
                            resFile_f.setBusinessId(baseProduct.getId());
                            resFileService.update(resFile_f);
		                }else{
		                    return new ResponseEnvelope<UploadFileResponse>(false, "找不到该产品编码！", msgId);
		                }
                	}else if("scene".equals(fileType)){
                		// 通过样板间编码查找对应的样板间
                		DesignTemplet designTemplet = new DesignTemplet();
                		designTemplet.setDesignCode(fileCode);
                		designTemplet.setIsDeleted(0);
                		DesignTemplet design = null;
                		List<DesignTemplet> dtList = designTempletService.getList(designTemplet);
                		if(null != dtList && dtList.size() > 0){
                			design = dtList.get(0);
                			logger.info("design.getFbxFileId()------>"+design.getFbxFileId());
                			if(design.getFbxFileId() != 0){
                				design.setFbxState(1);
                			}else{
                				design.setFbxState(0);
                			}
                			design.setFbxFileId(resFile.getId());
                		}
                		designTempletService.update(design);
                	}
                }else{
                	return new ResponseEnvelope<UploadFileResponse>(false, "上次"+fileName+"文件失败！", msgId);
                }
            }else if( "jpg".equalsIgnoreCase(fileSuffix) || "png".equalsIgnoreCase(fileSuffix) ){// fbx贴图
                ResPic resPic = new ResPic();
                resPic.setPicCode(fileName.substring(0,fileName.indexOf(".")));
                resPic.setPicDesc("FBX贴图");
                resPic.setPicName(map.get(FileModel.FILE_NAME));
                resPic.setFileKey(map.get(FileModel.FILE_KEY));
                resPic.setPicFileName(map.get(FileModel.FILE_ORIGINAL_NAME));
                resPic.setPicSize(Integer.valueOf(map.get(FileModel.FILE_SIZE)));
                resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX));
                resPic.setPicPath(map.get(FileModel.FILE_PATH));
                sysSave(resPic,request);
                int i = resPicService.add(resPic);
                if( i>0 ){
                	if ("product".equals(fileType)) {
		                // 通过产品编码查找产品
		                BaseProduct baseProduct = new BaseProduct();
		                baseProduct.setProductCode(fileCode);
		                List<BaseProduct> list = baseProductService.getList(baseProduct);
		                if( list != null && list.size() > 0 ){
		                    baseProduct = list.get(0);
		                    BaseProduct bp = new BaseProduct();
		                    bp.setId(baseProduct.getId());
		                    if( StringUtils.isNotBlank(baseProduct.getFbxTexture()) ){
		                    	String picIds = baseProduct.getFbxTexture();
                                bp.setFbxTexture(picIds+","+i);
		                    }else{
                                bp.setFbxTexture(i+"");
		                    }
                            baseProductService.update(bp);
		                    ResPic resPic_p = new ResPic();
                            resPic_p.setId(resPic.getId());
                            resPic_p.setBusinessId(baseProduct.getId());
                            resPicService.update(resPic_p);
		                }else{
		                    return new ResponseEnvelope<UploadFileResponse>(false, "找不到该产品编码！", msgId);
		                }
                	}else if("sence".equals(fileType)){
                		// 通过样板间编码查找对应的样板间
                		DesignTemplet designTemplet = new DesignTemplet();
                		designTemplet.setDesignCode(fileCode);
                		designTemplet.setIsDeleted(0);
                		DesignTemplet design = null;
                		List<DesignTemplet> dtList = designTempletService.getList(designTemplet);
                		if(null != dtList && dtList.size() > 0){
                			design = dtList.get(0);
                			if(StringUtils.isNotBlank(design.getFbxTexture())){
                				String picIds = design.getFbxTexture();
                				design.setFbxTexture(picIds+","+i);
                			}else{
                				design.setFbxTexture(i+"");
                			}
                		}
                		designTempletService.update(design);
                	}
                }else{
                	return new ResponseEnvelope<UploadFileResponse>(false, "上次"+fileName+"贴图失败！", msgId);
                }
                
            }else if( "max".equals(fileSuffix) ){// fbx max
            	ResModel resModel = new ResModel();
            	resModel.setModelCode(fileName.substring(0,fileName.indexOf(".")));
            	resModel.setModelDesc("产品配置文件");
            	resModel.setModelName(map.get(FileModel.FILE_NAME));
            	resModel.setFileKey(map.get(FileModel.FILE_KEY));
            	resModel.setModelFileName(map.get(FileModel.FILE_ORIGINAL_NAME));
            	resModel.setModelSize(map.get(FileModel.FILE_SIZE));
            	resModel.setModelSuffix(map.get(FileModel.FILE_SUFFIX));
            	resModel.setModelPath(map.get(FileModel.FILE_PATH));
                sysSave(resModel,request);
                int i = resModelService.add(resModel);
                if( i>0 ){
                	if ("product".equals(fileType)) {
		                // 通过产品编码查找产品
		                BaseProduct baseProduct = new BaseProduct();
		                baseProduct.setProductCode(fileCode);
		                List<BaseProduct> list = baseProductService.getList(baseProduct);
		                if( list != null && list.size() > 0 ){
		                    baseProduct = list.get(0);
		                    baseProduct.setWindowsU3dModelId(resModel.getId());
		                }else{
		                    return new ResponseEnvelope<UploadFileResponse>(false, "找不到该产品编码！", msgId);
		                }
		                baseProductService.update(baseProduct);
                	}else if("sence".equals(fileType)){
                		// 通过样板间编码查找对应的样板间
                		DesignTemplet designTemplet = new DesignTemplet();
                		designTemplet.setDesignCode(fileCode);
                		designTemplet.setIsDeleted(0);
                		DesignTemplet design = null;
                		List<DesignTemplet> dtList = designTempletService.getList(designTemplet);
                		if(null != dtList && dtList.size() > 0){
                			design = dtList.get(0);
                			design.setIosModelU3dId(resModel.getId());
                		}
                		designTempletService.update(design);
                	}
                }else{
                	return new ResponseEnvelope<UploadFileResponse>(false, "上次"+fileName+"失败！", msgId);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            return new ResponseEnvelope<UploadFileResponse>(uploadFileResponse,msgId,false);
        }
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        return new ResponseEnvelope<UploadFileResponse>(uploadFileResponse,msgId,true);
    }
    
    
    /**
	 * 自动存储系统字段
	 */
	private void sysSave(ResModel model,HttpServletRequest request){
		if (model != null) {
			/* 获得操作者信息 */
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
						
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
			}
			model.setSysCode(model.getModelCode());
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
    
    /**
	 * 获取单品、场景、组合配置列表
	 */
	@RequestMapping(value = "/getResourceList")
	@ResponseBody
	public Object getResourceList( @ModelAttribute("resTextureSearch") ResFileSearch resFileSearch,String type, HttpServletRequest request, HttpServletResponse response) {
		if( StringUtils.isEmpty(type) ){
			return new ResponseEnvelope<ResTexture>(false, "参数type为空!","");
		}
		ResTextureResponse resTextureResponse = null;
		//查询配置文件列表(type)标识
		resFileSearch.setFileSuffix(type);
		resFileSearch.setIsDeleted(0);
		List<ResTextureResponse> list = new ArrayList<ResTextureResponse> ();
		int total = 0;
		try {
			total = resFileService.getCount(resFileSearch);
            
			if (total > 0) {
				List<ResFile> resList = resFileService.getPaginatedList(resFileSearch);	
				for(ResFile rf : resList){
					resTextureResponse = new ResTextureResponse();
					resTextureResponse.setFileName(rf.getFileName());
					list.add(resTextureResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResTexture>(false, "数据异常!",resFileSearch.getMsgId());
		}
		return new ResponseEnvelope<ResTextureResponse>(total, list, resFileSearch.getMsgId());
	}

    public static void main(String[] args) {
        try {
//            DeCompressUtil.deCompress("/scene/123.SceneCfg","F:\\resources-test\\resources-test\\scene\\111","解压失败！");
//            File file = new File("F:\\resources-test\\resources-test\\scene\\123.SceneCfg");
            String res = FileController.doCompress(new File("F:\\resources-test\\resources-test\\scene\\DCC7BDFDF49CE6111B576F81E39B4B36.SceneCfg"),Utils.getPropertyName("client","scene.upload.path",""));
            //System.out.println(res);
//            DeCompressUtil.deCompress("F:\\resources-test\\resources-test\\scene\\DCC7BDFDF49CE6111B576F81E39B4B36.zip","F:\\resources-test\\resources-test\\scene\\","空间");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
