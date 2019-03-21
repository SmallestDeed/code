package com.nork.system.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.*;
import com.nork.design.model.DesginPlanSpellingflowerRecord;
import com.nork.design.service.DesginPlanSpellingflowerRecordService;
import com.nork.system.dao.UserSpellingflowerColletMapper;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResPic;
import com.nork.system.model.SysUser;
import com.nork.system.model.UserSpellingflowerCollet;
import com.nork.system.model.search.UserSpellingflowerColletSearch;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.SysUserService;
import com.nork.system.service.UserSpellingflowerColletService;
import com.nork.user.model.UserTypeCode;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**   
 * @Title: UserSpellingflowerColletServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:拼花-我的瓷砖拼花收藏ServiceImpl
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:38:44
 * @version V1.0   
 */
@Service("userSpellingflowerColletService")
public class UserSpellingflowerColletServiceImpl implements UserSpellingflowerColletService {
	private static Logger logger = Logger.getLogger(UserSpellingflowerColletServiceImpl.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ResDesignService resDesignService;
	@Autowired
	private DesginPlanSpellingflowerRecordService desginPlanSpellingflowerRecordService;
	@Autowired
	private UserSpellingflowerColletMapper userSpellingflowerColletMapper;
	@Autowired
	private ResPicService resPicService;
	/**
	 * 新增收藏拼花数据
	 * @param flowerCollet
	 * @param loginUser
	 * @return
	 */
	@Override
	public ResponseEnvelope add(UserSpellingflowerCollet flowerCollet, LoginUser loginUser) {
		logger.info("新增数据 service 开始：参数="+ JSON.toJSONString(flowerCollet)+"用户="+JSON.toJSONString(loginUser));
		String msgId = flowerCollet.getMsgId();
		ResDesign resDesign = null;
		ResPic resPic = null;
		File file = null;
		File picFile = null;
		String path = null;
		String picPath = null;

		/** 获取记录表、资源表数据，判断 */
		//1.获取已使用记录、用户信息、图片信息/或者收藏拼花信息
		UserSpellingflowerCollet usercollet = null;
		DesginPlanSpellingflowerRecord record = desginPlanSpellingflowerRecordService.get(Integer.parseInt(flowerCollet.getRecordId()));
		SysUser sysUser = sysUserService.get(loginUser.getId());
		//2.判断是否存在已使用记录
		if(null!=record){
			//3.判断是否存在收藏id
			if(null!=record.getSpellingflowerColletId()){
				//4.查询是否存在该收藏id的拼花数据
				usercollet =  userSpellingflowerColletMapper.selectByPrimaryKey(record.getSpellingflowerColletId());
			}
		}else{
			return new ResponseEnvelope(false,"不存在该Id的已使用拼花记录",msgId);
		}

		/** 判断该用户是否已经收藏过该已使用拼花 */
		//1.判断
		if(null!=usercollet){
			//2.判断该数据是否为已删除
			if(usercollet.getIsDeleted().equals(1)){
				//3.更改状态
				usercollet.setIsDeleted(0);
				usercollet.setRemark(";updateMark;");
				String sysCode = usercollet.getSysCode();
				if(StringUtils.isNotBlank(sysCode))
					usercollet.setSysCode(sysCode.substring(0,sysCode.indexOf("_del_")));
				usercollet.setGmtCreate(new Date());
				usercollet.setGmtModified(new Date());
				usercollet.setColletName(flowerCollet.getColletName());
				Integer count = userSpellingflowerColletMapper.updateByPrimaryKeySelective(usercollet);
				if(null!=count){
					return new ResponseEnvelope(true,"收藏拼花成功",msgId);
				}else{
					return new ResponseEnvelope(true,"收藏拼花失败（更改拼花状态失败）！",msgId);
				}
			}else{
				return new ResponseEnvelope(true,"该拼花已经在收藏列表",msgId);
			}

		}else{
			/** 首次收藏该已使用拼花  参数校验 */
			//3.获取产品Integer集合
			String productIds = record.getSpellingFlowerProduct();
			if(!loginUser.getUserType().equals(UserTypeCode.USER_TYPE_INNER)&&StringUtils.isNoneBlank(productIds)){
				String[] listT = productIds.split(",");
				Integer[] prodctIdList = new Integer[listT.length];
				for (int i =0 ;i < listT.length; i++) {
					prodctIdList[i] = Integer.parseInt(listT[i]);
				}
				//4.用户id与prodctIdList作为参数，查询匹配的产品code
				Integer brandId = userSpellingflowerColletMapper.selectBrandIdByUserId(sysUser.getId());
				String valueKey = userSpellingflowerColletMapper.getCompanyValueKey(brandId);
				String[] valueKeyList = valueKey.split(",");
				List<String> productCodeList = userSpellingflowerColletMapper.getProductCode(prodctIdList,valueKeyList,brandId);
				//5.判断是否有值或是否匹配
				if((null==productCodeList||productCodeList.size()==0)||(prodctIdList.length != productCodeList.size())){
					return new ResponseEnvelope(false,"只能收藏自己品牌的拼花样式",msgId);
				}
			}

			//6.获取资源信息
			resDesign = resDesignService.get(record.getSpellingFlowerFileId());
			resPic = resPicService.get(record.getSpellingFlowerPicId());

			//7.判断是否存在资源信息
			if(null==resDesign||null==resPic){
				return new ResponseEnvelope(false,"不存在该Id的已使用拼花记录的资源信息",msgId);
			}else{
				//8.获取当前系统完整路径
				path = Utils.getAbsolutePath(resDesign.getFilePath(), null);
				picPath = Utils.getAbsolutePath(resPic.getPicPath(),null);

				//9.以完整路径创建file
				file = new File(path);
				picFile = new File(picPath);

				//判断是否存在资源文本与图片数据
				if(!file.exists()){
					logger.info("不存在该path的文件="+path);
					return new ResponseEnvelope(false,"不存在资源文本数据",msgId);
				}
				if(!picFile.exists()){
					logger.info("不存在该picPath的文件="+picPath);
					return new ResponseEnvelope(false,"不存在图片资源数据",msgId);
				}
			}

		}

		/** 收藏拼花信息 */
		//1.获取收藏拼花存储路径
		String fileKey = "design.designPlan.spellingFlowerFile.collet.upload.path";
		String defaultPath = "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/collet/";
		String configKeyNew = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
		configKeyNew = Utils.replaceDate(configKeyNew);
		String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
		//这句话获取全部资源路径：在内部获取存储前缀（configKeyNew + fileName=后面路径）
		String filePath = Utils.getAbsolutePath(configKeyNew + fileName, null);
		if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
			filePath = filePath.replace("\\", "/");
		}

		//2.根据路径获取文件流
		File fileNew = new File(filePath);
		//3.根据已使用拼花File与收藏拼花File，在新路径下重新生成一份文件
		Boolean bool = FileUploadUtils.fileCopy(file,fileNew);
		//5.判断是否上传服务器
		if(!bool){
			return new ResponseEnvelope(false,"收藏拼花信息上传服务器失败",msgId);
		}
		//6.获取上传文件详细信息
		Map<String, String> map = FileUploadUtils.getMap(new File(filePath), fileKey, false);
		//7.保存资源路径到resDesign表
		Integer idResDesign = desginPlanSpellingflowerRecordService.saveSpellingFlowerFile(map,sysUser.getSysCode(),flowerCollet.getCreator(),sysUser.getId());
		if(null==idResDesign){
			return new ResponseEnvelope(false,"保存资源路径到resDesign出错",msgId);
		}


		/** 收藏拼花图片数据 */
		String picFileKey = "design.designPlan.spellingFlowerFile.collet.pic.upload.path";
		String picDefaultPath = "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/collet/pic/";
		// 获得当前时间
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// 转换为字符串
		String formatDate = format.format(new Date());
		// 随机生成文件编号
		int random = new Random().nextInt(10000);
		String imageName = new StringBuffer().append(formatDate).append(random).toString();
		String suffix = resPic.getPicSuffix();

		// 获取配置的业务文件目录
		String picFilePath = Utils.getPropertyName("config/res", picFileKey, picDefaultPath);
		picFilePath = Utils.replaceDate(picFilePath);
		// 数据库存储目录
		String dbFilePath = picFilePath + imageName + suffix;
		// 文件服务器的存储路径
		String realPath = Tools.getRootPath(picFilePath.replace("/", "\\"),"") + picFilePath;
		if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
			realPath = Tools.getRootPath(picFilePath,"") + picFilePath;
		}
		//新图片文件路径
		String lastpath = realPath + imageName + suffix;
		//2.根据路径获取文件流
		File picFileNew = new File(lastpath);
		//3.根据已使用拼花图片File与收藏拼花picFile，在新路径下重新生成一份文件
		Boolean picBool = FileUploadUtils.fileCopy(picFile,picFileNew);
		//5.判断是否上传服务器
		if(!picBool){
			return new ResponseEnvelope(false,"收藏拼花图片上传服务器失败",msgId);
		}

		File f = new File(lastpath);
		Map picmap = FileUploadUtils.getMap(f, Tools.getRootPath(lastpath, ""), false);

		//5.保存资源路径到resPic表
		Integer picId = desginPlanSpellingflowerRecordService.saveSpellingFlowerFilePic(picmap,sysUser.getSysCode(),flowerCollet.getCreator(),sysUser.getId());
		if(null==picId){
			return new ResponseEnvelope(false,"保存图片资源路径到resPic出错",msgId);
		}

		/* ftp上传 */
		try {
			boolean flag = false;
			// 仅支持ftp服务器上传
			if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
				logger.info("------ 拼花图片上传ftp服务器删除本地文件");
				flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath + imageName + suffix, picFilePath);
				if (flag) {
					// 删除本地
					FileUploadUtils.deleteFile(dbFilePath);
				} else {
					logger.info("---------仅支持ftp服务器文件上传异常！");
				}
			}
			// 3 本地和ftp同时上传(默认是本地上传)
			if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 3) {
				logger.info("------拼花图片上传ftp服务器不删除本地文件");
				flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath + imageName + suffix, picFilePath);
				if (!flag) {
					logger.info("---------本地和ftp服务器同时文件上传异常！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("----ftp上传异常：" + e.getMessage());
		}

		//8.设值需要保存的信息
		flowerCollet.setSpellingFlowerFileId(idResDesign);
		flowerCollet.setSpellingFlowerPicId(picId);
		flowerCollet.setUserId(sysUser.getId());
		flowerCollet.setSysCode(sysUser.getSysCode());
		flowerCollet.setSpellingFlowerProduct(record.getSpellingFlowerProduct());
		//9.保存拼花信息到收藏表
		Integer idCollet = userSpellingflowerColletMapper.insertSelective(flowerCollet);
		//10.判断是否保存成功
		if(null==idCollet){
			return new ResponseEnvelope(false,"保存拼花到收藏表出错",msgId);
		}

		/** 回写到已使用记录表 */
		//1.设值回写参数
		DesginPlanSpellingflowerRecord recordU = new DesginPlanSpellingflowerRecord();
		recordU.setSpellingflowerColletId(flowerCollet.getId());
		recordU.setId(Integer.parseInt(flowerCollet.getRecordId()));
		//2.调用service，修改
		Integer idrecordU = desginPlanSpellingflowerRecordService.update(recordU);
		//3.判断是否回写成功
		if(null==idrecordU){
			return new ResponseEnvelope(false,"回写收藏id到已使用表失败",msgId);
		}

		/** 返回成功 */
		return new ResponseEnvelope(true,"收藏拼花成功",msgId);
	}



	/**
	 * 分页获取数据
	 * @param  colletSearch
	 * @return   ResponseEnvelope
	 */
	@Override
	public ResponseEnvelope getPaginatedList(UserSpellingflowerColletSearch colletSearch) {
		String msgId = colletSearch.getMsgId();
		ResponseEnvelope<UserSpellingflowerCollet> responseEnvelope = null;
		long totalCount = 0;
		List<UserSpellingflowerCollet> colletList = null;

		/** 设值查询参数 */
		colletSearch.setIsDeleted(0);
		colletSearch.setOrder("ct.gmt_create");
		colletSearch.setOrderNum("DESC");
		colletSearch.setSch_ColletName_(colletSearch.getColletName());
		colletSearch.setColletName(null);

		/** 获取总数量与数据 */
		totalCount = userSpellingflowerColletMapper.selectCount(colletSearch);
		if(totalCount>0){
			colletList = userSpellingflowerColletMapper.selectPaginatedList(colletSearch);
		}else{
			new ResponseEnvelope(true,"未获取到该用户收藏拼花数据！",msgId);
		}

		/** 判断集合与获取相对应的资源信息，并解析 */
		//1.判断
		if(null!=colletList&&colletList.size()>0){
			try {
				//2.循环数据
				for(int i = 0;i<colletList.size();i++){
					//3.获取当前对象
					UserSpellingflowerCollet collet = colletList.get(i);
					//4.获取产品ids
					String productIds = collet.getSpellingFlowerProduct();
					//5.获取收藏拼花所对应的产品map集合
					Map<String,Object> spellingFlowerProductMap = desginPlanSpellingflowerRecordService.spellingFlowerData(productIds);
					//6.设值到当前对象
					collet.setSpellingFlowerProductMap(spellingFlowerProductMap);
				}
			}catch (Exception e){
				logger.error("获取该用户收藏拼花产品数据失败"+e.getMessage(),e);
				return new ResponseEnvelope(false,"获取该用户收藏拼花产品数据失败！",msgId);
			}
		}

		return new ResponseEnvelope<UserSpellingflowerCollet>(msgId,true,"获取用户收藏拼花数据成功！",totalCount,colletList);

	}


	/**
	 *    更新数据
	 *
	 * @param flowerCollet
	 * @return  int 
	 */
	@Override
	public ResponseEnvelope update(UserSpellingflowerCollet flowerCollet) {
		String msgId = flowerCollet.getMsgId();
		/** 判断当前数据是否是当前用户 */
		UserSpellingflowerCollet collet = userSpellingflowerColletMapper.selectByPrimaryKey(flowerCollet.getId());
		if(null!=collet){
			if(!collet.getUserId().equals(flowerCollet.getUserId())){
				return new ResponseEnvelope(false,"不能修改别人的收藏数据",msgId);
			}
			if(collet.getIsDeleted()>0){
				return new ResponseEnvelope(false,"不能修改已删除的收藏数据",msgId);
			}
		}else{
			return new ResponseEnvelope(false,"不存在当前id的收藏拼花数据",msgId);
		}


		/** 调用mapper,修改数据 */
		flowerCollet.setGmtModified(new Date());
		Integer count = userSpellingflowerColletMapper.updateByPrimaryKeySelective(flowerCollet);
		if(null!=count&&count>0){
			return new ResponseEnvelope(true,"修改成功",msgId);
		}else{
			return new ResponseEnvelope(false,"修改失败",msgId);
		}
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public ResponseEnvelope delete(Integer id,Integer UserId,String msgId) {
		/** 判断当前数据是否是当前用户 */
		UserSpellingflowerCollet collet = userSpellingflowerColletMapper.selectByPrimaryKey(id);
		if(null!=collet){
			if(!collet.getUserId().equals(UserId)){
				return new ResponseEnvelope(false,"不能删除别人的收藏数据",msgId);
			}
			if("1".equals(collet.getIsDeleted())){
				return new ResponseEnvelope(false,"该收藏拼花已删除",msgId);
			}
		}else{
			return new ResponseEnvelope(false,"不存在当前id的收藏拼花数据",msgId);
		}

		/** 调用mapper,删除数据 */
		Integer count = userSpellingflowerColletMapper.deleteByPrimaryKey(id);
		if(null!=count&&count>0){
			return new ResponseEnvelope(true,"删除成功",msgId);
		}else{
			return new ResponseEnvelope(false,"删除失败",msgId);
		}
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserSpellingflowerCollet 
	 */
	@Override
	public UserSpellingflowerCollet get(Integer id) {
		return userSpellingflowerColletMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  userSpellingflowerCollet
	 * @return   List<UserSpellingflowerCollet>
	 */
	@Override
	public List<UserSpellingflowerCollet> getList(UserSpellingflowerCollet userSpellingflowerCollet) {
	    return userSpellingflowerColletMapper.selectList(userSpellingflowerCollet);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  userSpellingflowerColletSearch
	 * @return   int
	 */
	@Override
	public Integer getCount(UserSpellingflowerColletSearch userSpellingflowerColletSearch){
		return  userSpellingflowerColletMapper.selectCount(userSpellingflowerColletSearch);	
    }
	

	/**
	 * 其他
	 * 
	 */

}
