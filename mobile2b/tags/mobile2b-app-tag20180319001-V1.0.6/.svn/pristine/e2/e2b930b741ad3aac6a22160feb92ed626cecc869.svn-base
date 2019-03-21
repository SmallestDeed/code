package com.nork.home.controller.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nork.common.model.ResponseEnvelope;
import com.nork.home.model.BaseHouseApply;
import com.nork.home.service.BaseHouseApplyService;
import com.nork.system.model.ResHousePic;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResHousePicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;

@Controller
@RequestMapping("/{style}/web/home/baseHouseApply")
public class WebBaseHouseApplyController {

	private static Logger logger = Logger.getLogger(WebBaseHouseApplyController.class);

	@Autowired
	private ResHousePicService resHousePicService;
	@Autowired
	private BaseHouseApplyService baseHouseApplyService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 新建户型申请
	 * @author huangsongbo
	 * @param file
	 * @param baseHouseApply
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public Object create(@RequestParam(value="file",required=false) MultipartFile file,BaseHouseApply baseHouseApply, String msgId) {
		/*限制文件类型*/
		List<SysDictionary> list=sysDictionaryService.findAllByTypeAndAtt1("baseHousePicType", "1");
		if(list==null||list.size()==0)
			return new ResponseEnvelope<>(false, "管理原还未设置可上传的图片格式,请联系管理员", msgId);
		String originalFilename = file.getOriginalFilename();
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		boolean flag=false;
		StringBuffer typeStr=new StringBuffer("");
		for(SysDictionary sysDictionary:list){
			typeStr.append(sysDictionary.getName()+",");
			if(suffix.toLowerCase().endsWith(sysDictionary.getName().toLowerCase())){
				flag=true;
			}
		}
		if(!flag){
			return new ResponseEnvelope<>(flag, "文件类型不支持,支持类型:"+(typeStr.length()==0?"":typeStr.subSequence(0, typeStr.length()-1)), msgId);
		}
		/*限制文件类型->end*/
		/*保存图片文件*/
		SysUser sysUser=null;
		if(baseHouseApply.getUserId()!=null){
			sysUser=sysUserService.get(baseHouseApply.getUserId());
			if(sysUser==null)
				sysUser=new SysUser();
		}
		Integer picId=resHousePicService.saveMultipartFile(file, "home.baseHouseApply.pic.upload.path",sysUser);
		/* 保存图片文件->end */
		baseHouseApply.setPicId(picId);
		baseHouseApplyService.sysSave(baseHouseApply,sysUser);
		Integer applyId=baseHouseApplyService.add(baseHouseApply);
		ResHousePic resHousePic=resHousePicService.get(picId);
		if(resHousePic!=null){
			ResHousePic newResHousePic = new ResHousePic();
			newResHousePic.setId(resHousePic.getId());
			newResHousePic.setBusinessId(applyId);
			resHousePicService.update(newResHousePic);
		}
		return new ResponseEnvelope<>(true, "success", msgId);
	}
	
}
