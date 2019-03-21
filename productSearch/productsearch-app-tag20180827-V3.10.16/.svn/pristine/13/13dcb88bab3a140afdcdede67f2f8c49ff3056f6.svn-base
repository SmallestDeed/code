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

/***
 * 更新设计方案任务
 * @author qiu.jun
 * @date 2016.06.16
 *
 */
public class UnitUpdateTask implements Callable<Result>{

	private DesignPlanService designPlanService=SpringContextHolder.getBean(DesignPlanService.class);
	private ResFileService resFileService = SpringContextHolder.getBean(ResFileService.class);
	private ResDesignService resDesignService = SpringContextHolder.getBean(ResDesignService.class);
	private UnitUpdateParameter parameter;
	
	public UnitUpdateTask(UnitUpdateParameter parameter){
		this.parameter=parameter;
	}
	
	@Override
	public Result call() throws Exception {
		// TODO Auto-generated method stub
		DesignPlan desPlan = designPlanService.get(parameter.getDesignPlanId());

		if (desPlan == null) {
			return null;
		}
		Integer designPlanId=parameter.getDesignPlanId();
		String context=parameter.getContext();
//		Integer userId=parameter.getUserId();
//		Integer planProductId=parameter.getPlanProductId();
//		Integer productId=parameter.getProductId();
//		String materialPicId=parameter.getMaterialPicId();
//		String msgId=parameter.getMsgId();
//		HttpServletRequest request=parameter.getRequest();
//		String splitTexturesChoose=parameter.getSplitTexturesChoose();
//		desPlan.setUserIdTemp(userId);
		// 替换配置内容
//		boolean flag = designPlanService.updateDesignPlan(desPlan, designPlanId, planProductId, productId,
//				materialPicId, context, request, splitTexturesChoose);
//		if (flag) {
			// 获取配置路径
			String filePath = "";
			try {
				if (desPlan.getConfigFileId() != null) {
					// ResFile resFile =
					// resFileService.get(desPlan.getConfigFileId());
					//ResFile resFile = null;
					ResDesign resDesign = resDesignService.get(desPlan.getConfigFileId());

					/*filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath().replace("/", "\\");
					if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
						filePath = Constants.UPLOAD_ROOT + resDesign.getFilePath();
					}*/
					filePath = Utils.dealWithPath(Utils.getAbsolutePath(resDesign.getFilePath(), null), null);
					/**
					 * TODO
					 * 获取上传方式。1：上传到web服务器；2：上传到ftp；3：同时上传到web服务器和ftp。如果没有取到值，
					 * 则上传到web服务器。
					 **/
					Long startTimeUp = System.currentTimeMillis();
					Integer ftpUploadMethod = Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD, 1);
					boolean uploadFtpFlag = false;
					// 上传方式为2或者3表示文件在ftp上
					if (ftpUploadMethod == 2 || ftpUploadMethod == 3) {
						uploadFtpFlag = FtpUploadUtils.replaceFile(resDesign.getFilePath(), context);
					} else if (ftpUploadMethod == 1) {
						uploadFtpFlag = Utils.replaceFile(filePath, context);
					}
					if (uploadFtpFlag) {
						boolean flags = designPlanService.updatePlanProductByConfig(context,designPlanId,false);
						if( !flags ){
							return null;
						}
					} else {
						return null;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		return null;
	}

}
