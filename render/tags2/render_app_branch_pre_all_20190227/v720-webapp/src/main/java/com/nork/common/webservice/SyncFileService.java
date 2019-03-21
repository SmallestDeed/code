package com.nork.common.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SyncFileService {
	@WebMethod
	public void uploadFile(@WebParam(name="fileEntity") SyncFileEntity fileEntity,@WebParam(name="svrRootPath") String svrRootPath,@WebParam(name="svrDBPath") String svrDBPath);
	
}
