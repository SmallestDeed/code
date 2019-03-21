package com.nork.common.async;

import java.util.concurrent.Callable;

import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.system.model.ResDesign;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;

public class UpdatePlanConfigTask implements Callable<Result> {
	
	private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
	private ResFileService resFileService = SpringContextHolder.getBean(ResFileService.class);
	private ResDesignService resDesignService = SpringContextHolder.getBean(ResDesignService.class);
	 
	private UpdatePlanConfigParameter parameter;
	public UpdatePlanConfigTask(UpdatePlanConfigParameter parameter){
		this.parameter = parameter;
	}

	@Override
	public Result call() throws Exception {
		Integer planId = parameter.getPlanId();
		String context = parameter.getContext();
		try {
			DesignPlan designPlan = designPlanService.get(planId);

			/*解除planProductId设计产品的组(解组)->end*/
			//ResFile resFile=null;
			ResDesign resDesign = resDesignService.get(designPlan.getConfigFileId());

			/*String filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath().replace("/", "\\");
			if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
				filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath();
			}*/
			String filePath = Utils.dealWithPath(Utils.getAbsolutePath(resDesign.getFilePath(), null), null);
			/**
			 * TODO
			 * 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，则上传到web服务器。
			 **/
			Integer ftpUploadMethod = Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD, 1);
			boolean uploadFtpFlag = false;
			// 上传方式为2或者3表示文件在ftp上
			if (ftpUploadMethod == 1 || ftpUploadMethod == 3) {
				// 替换本地
				uploadFtpFlag = Utils.replaceFile(filePath, context);
				if (ftpUploadMethod == 3) {
					// 替换ftp
					uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
				}
			} else if (ftpUploadMethod == 2) {
				// 替换ftp
				uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
			}
			if (uploadFtpFlag) {
				boolean flag = designPlanService.updatePlanProductByConfig(context,planId,false);
				if( !flag ){
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
