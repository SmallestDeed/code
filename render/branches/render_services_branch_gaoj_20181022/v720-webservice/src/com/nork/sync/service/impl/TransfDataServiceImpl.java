package com.nork.sync.service.impl;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import com.nork.sync.model.FileEntity;
import com.nork.sync.service.TransfDataService;

//@SOAPBinding(style = Style.RPC) 

public class TransfDataServiceImpl implements TransfDataService{

	@Override
	@WebMethod
	public Integer uploadFile(@WebParam(name="fileEntity")FileEntity fileEntity) {
		Integer resId = -1;
		if(fileEntity==null){
			return null;
		}
		DataHandler handler = fileEntity.getFile();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = handler.getInputStream();
			os = new FileOutputStream("D:/websercie/server/" + fileEntity.getFileName() + "."
					+ fileEntity.getFileType());
			int n = 0;
			byte[] b = new byte[1024];
			while ((n = is.read(b)) != -1) {
				os.write(b, 0, n);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		//插入对应的数据库表结构
		
		return resId;
	}
	

	
}
