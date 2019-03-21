package com.nork.home.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;

/**
 * @Title: WebSpaceCommonController.java
 * @Package com.nork.home.web.controller
 * @Description:户型房型-通用空间表Controller
 * @createAuthor xiaoxc
 * @CreateDate 2015-09-17 19:48:39
 * @version V1.0
 */
@Controller
@RequestMapping("/{style}/web/home/spaceCommon")
public class WebSpaceCommonController {
	private static Logger logger = Logger.getLogger(WebSpaceCommonController.class);
	
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public Object uploadFile(@RequestParam(value = "resPicFile",required = false) MultipartFile resPicFile,String msgId,HttpServletRequest request) throws IOException {
	    if (resPicFile != null && !resPicFile.isEmpty()) {
	        // 获取图片的文件名
	        String fileName = resPicFile.getOriginalFilename();
	        logger.info("uploadFile =====> file name ="+ fileName);
	        //保存  
	        try {  
	        	String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.appSystemOperationLog.upload.path",""),null);
	        	File picFileName = new File(path+fileName);
	        	 if(!picFileName.exists()){  
	        		 picFileName.mkdirs();  
	 	        } 
	        	 resPicFile.transferTo(picFileName);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            new  ResponseEnvelope<LoginUser>(false, "传输文件失败");
	        } 
	    }  
	    return new  ResponseEnvelope<LoginUser>(true, "传输文件成功",msgId);
	}

}
