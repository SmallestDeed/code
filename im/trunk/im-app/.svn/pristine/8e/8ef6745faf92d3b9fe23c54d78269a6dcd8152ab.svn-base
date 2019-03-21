package com.sandu.im;

import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.sandu.im.cache.CacheInstances;



@SpringBootApplication
@ImportResource(value = {"classpath:spring-sso-config.xml"})
@MapperScan("com.sandu.im.dao")
public class ChatApplication {
	private static final Logger logger = LoggerFactory.getLogger(ChatApplication.class);
	  @Value("${ssl.keystore.filePath}")     
	  private String keystoreFilePath;
	  @Value("${ssl.keystore.password}")    
	  private  String keystorePassword;

	@Autowired
	@Bean  
    public SocketIOServer socketIOServer()   
    {  

		//System.out.println(System.getProperty("java.version"));
    	Configuration config = new Configuration();
		config.setHostname("0.0.0.0");
		config.setPort(15001);
		SocketConfig socketConfig = new SocketConfig();
		
		/*socketConfig.setReuseAddress(true);
		socketConfig.setTcpKeepAlive(true);
		socketConfig.setSoLinger(0);
		socketConfig.setTcpNoDelay(true);*/
		config.setSocketConfig(socketConfig);
		config.setHttpCompression(false);
		config.setWebsocketCompression(false);
		
		
//		config.setOrigin("http://im.sandu.com");
		
		config.setWorkerThreads(100);
		config.setKeyStorePassword(keystorePassword);
	    InputStream stream = ChatApplication.class.getResourceAsStream(keystoreFilePath);
	    config.setKeyStore(stream);
	        
//		config.setStoreFactory(new HazelcastStoreFactory());
		config.setAuthorizationListener(new AuthorizationListener() {
			public boolean isAuthorized(HandshakeData data) {
		    	//String timestamp = data.getSingleUrlParam("timestamp");
		    	//String sign = data.getSingleUrlParam("sign");
		    	//if(sign==md5(appId+userSessionId+timestamp+key))
				String appId = data.getSingleUrlParam("appId") ; //登录端
		    	String userSessionId = data.getSingleUrlParam("userSessionId");
		    	if(StringUtils.isBlank(appId) || StringUtils.isBlank(userSessionId)) {
		  //  		logger.warn("appId-->("+appId+"),userSessionId-->("+userSessionId+")");
		    		return false;
		    	}
		    	return true;
			}
		});
		
		SocketIOServer server  = new SocketIOServer(config);
        return server; 
    } 
      
    @Bean  
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {  
        return new SpringAnnotationScanner(socketServer);  
    }  

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
}
