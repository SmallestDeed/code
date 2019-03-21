package com.nork.common.async;

import java.util.Date;
import java.util.concurrent.Callable;

import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.system.model.ResDesign;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;

public class AddDesignPlanProductTask implements Callable<Result>{

	private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
	private ResFileService resFileService = SpringContextHolder.getBean(ResFileService.class);
	
	private ResDesignService resDesignService  = SpringContextHolder.getBean(ResDesignService.class);
	private AddDesignPlanProductParameter parameter ;
	
	public AddDesignPlanProductTask(AddDesignPlanProductParameter parameter){
		this.parameter = parameter;
	}
	@Override
	public Result call() throws Exception {
		
		Integer designPlanId = parameter.getDesignPlanId();
		String context = parameter.getContext();
		try {
			DesignPlan designPlan = designPlanService.get(designPlanId);
			//把新组装的内容写入配置文件
			ResDesign resDesign = resDesignService.get(designPlan.getConfigFileId());
			/*String filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath().replace("/", "\\");*/
			String filePath = Utils.getAbsolutePath(resDesign.getFilePath().replace("/", "\\"), Utils.getAbsolutePathType.encrypt);
			
			if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
				/*filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath();*/
				filePath = Utils.getAbsolutePath(resDesign.getFilePath(), Utils.getAbsolutePathType.encrypt);
			}

			/**TODO 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。**/
			Integer ftpUploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
			//上传方式为2或者3表示文件在ftp上
			if( ftpUploadMethod == 1 || ftpUploadMethod == 3 ){
				//替换本地
				Utils.replaceFile(filePath, context);
				if( ftpUploadMethod == 3 ) {
					//替换ftp
					 FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
				}
			}else if( ftpUploadMethod == 2 ){
				//替换ftp
				FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		boolean flag1 = designPlanService.updatePlanProductByConfig(context,designPlanId,false);
		if( !flag1 ){
//			return new ResponseEnvelope<DesignPlan>(false, "配置文件更新异常！", msgId);
			return null;
		}
		DesignPlan designPlan = designPlanService.get(designPlanId);
		if( designPlan != null ){
			designPlan.setGmtModified(new Date());
		}
//	    return new ResponseEnvelope<DesignPlan>(designPlanProductId, msgId, true);
		return null;
	}
	
	

}
