package com.nork.common.webservice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;

public class SyncFileServiceImpl implements SyncFileService{

	@Override
	public void uploadFile(SyncFileEntity fileEntity,String svrRootPath,String svrDBPath) {
		DataHandler handler = fileEntity.getFile();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = handler.getInputStream();
			os = new FileOutputStream(svrRootPath + svrDBPath + fileEntity.getFileName() + "." + fileEntity.getFileType());
			int n = 0;
			byte[] b = new byte[1024];
			while ((n = is.read(b)) != -1) {
				os.write(b, 0, n);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
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
			}
		}
	}
}
