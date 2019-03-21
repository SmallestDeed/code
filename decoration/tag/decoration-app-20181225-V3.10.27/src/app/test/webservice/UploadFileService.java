package app.test.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface UploadFileService {
	@WebMethod
	public void uploadFile(@WebParam(name="fileEntity") FileEntity fileEntity);
}
