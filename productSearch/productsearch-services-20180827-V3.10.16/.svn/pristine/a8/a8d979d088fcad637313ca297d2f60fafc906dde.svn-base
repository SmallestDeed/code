package com.nork.system.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.system.dao.ResmodelPathAndProductIdMapper;
import com.nork.system.model.ResmodelPathAndProductId;
import com.nork.system.service.ProductModelPathService;

/*
 *2017年8月28日下午6:49:28	
 *Author:ws
 *
 */
@Service("productModelPathService")
public class ProductModelPathServiceImpl implements ProductModelPathService {

	public static final String SERVER_PATH = "http://res3.sanduspace.cn";
	
	private static Logger logger = Logger.getLogger(ProductModelPathServiceImpl.class);

	@Autowired
	private ResmodelPathAndProductIdMapper resmodelPathAndProductIdMapper;

	// 查找不存在的模型路径并将其ID存储在本地硬盘
	public void checkModelPath() throws Exception {
		logger.error("Begin checkModelPath");
		List<StringBuilder> list = new ArrayList<StringBuilder>();
		
		String rootPath = Utils.getValue("app.upload.root", "/sz/sdkj/sandu_resource_src").trim();
		for(int i = 0 ; i < 300; i ++ ) {
			int start = i*500;
			int limit =  500;
			List<ResmodelPathAndProductId> res = resmodelPathAndProductIdMapper.getAllProductIdToResmodelPath(start,limit);
			logger.error("Get ResmodelPathAndProductId size = " + res.size());
			for (ResmodelPathAndProductId resmodelPathAndProductId : res) {
				String modelPath = resmodelPathAndProductId.getModel_path();
				modelPath = rootPath + modelPath;
				File destination = new File(modelPath);
				
				if(!destination.exists() || !destination.isFile()) {
					StringBuilder strBuilder = new StringBuilder();
//					strBuilder.append(resmodelPathAndProductId.getId());
//					strBuilder.append(",");
					strBuilder.append(resmodelPathAndProductId.getProduct_code());
//					strBuilder.append(",");
//					strBuilder.append(resmodelPathAndProductId.getModel_path());
//					strBuilder.append(",");
//					if(resmodelPathAndProductId.getGmt_create() != null) {
//						strBuilder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resmodelPathAndProductId.getGmt_create()));
//					}
					list.add(strBuilder);
					destination = null;
				}
			}
		}
		
		
		if (null == list || list.isEmpty()) {
			return;
		}
		File file =new File("/opt/ModelPathNoExist.txt");
        if(!file.exists()){  
        	 logger.error("create ModelPathNoExist");
            file.createNewFile();
        } 
        FileOutputStream fos = new FileOutputStream(file,true);
        OutputStreamWriter out =new OutputStreamWriter(fos,"utf-8");
        BufferedWriter bw = new BufferedWriter(out);
        logger.error("list==>size==" + list.size());
		for (StringBuilder strItem : list) {
			bw.write(strItem.toString());
			bw.newLine();
			bw.flush();
		}
		bw.close();
		logger.error("success");
	}

}
