package app.test.dubbo.service;



import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import app.test.dubbo.model.User;
@Path("demo")
public interface DemoRestService {

	String sayHello(String name);

	public List<User> getUsers();
	
	@POST
	@Path("user")
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	public User getUser(User user);
}