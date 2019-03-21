package com.sandu.pay.system.websocket.obj;

import com.sandu.common.constant.SystemCommonConstant;
import com.sandu.common.util.SendEmail;
import com.sandu.common.util.StringUtils;
import com.sandu.pay.system.websocket.util.WebSocketUtils;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


@ClientEndpoint
public class WebSocketClient {
	private static Logger logger = Logger.getLogger(WebSocketClient.class);

	// 连接失败的tomcat
	private static Set<String> FAIL_TOMCAT_SERVERS = new TreeSet<>();
	private boolean isChange = false;
	private final int maxSendCount = 3;// 告警邮件最多连续发送3次

	@Autowired
	private SysUserService sysUserService;

	@OnOpen
	public void onOpen(Session session) {

	}

	@OnMessage
	public void onMessage(String message) {
		logger.info("get message: " + message);
	}

	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}

	public void start() {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		String failTomcatIp = "";
		try {
			String uri = WebSocketUtils.webSocket.getString("app.webSocket.message");
			int start = uri.lastIndexOf("//")+2;
			String serverUri = uri.substring(start);
			int end = serverUri.indexOf("/");
			serverUri = serverUri.substring(0,end);
			String localIp = null;
			localIp = InetAddress.getLocalHost().getHostAddress();
			// 需要连接的tomcat服务器地址
			String tomcatServers = WebSocketUtils.webSocket.getString("app.tomcatServer.ip");
			if( StringUtils.isNotBlank(tomcatServers) ){
				String[] serverArr = tomcatServers.split(",");
				if( serverArr != null && serverArr.length > 0 ){
					for( String ip : serverArr ){
						failTomcatIp = ip;
						uri = uri.replace(serverUri,ip);
						serverUri = ip;
						uri = uri + localIp;
						Session session = null;
						try {
							session = container.connectToServer(WebSocketClient.class, URI.create(uri));
							if( session != null ){
								if( FAIL_TOMCAT_SERVERS.remove(failTomcatIp) ) {
									isChange = true;
								}
							}
						} catch (Exception e) {
							if (!FAIL_TOMCAT_SERVERS.contains(failTomcatIp)) {
								if( FAIL_TOMCAT_SERVERS.add(failTomcatIp) ) {
									isChange = true;
								}
							}
						} 
//						finally {
//							if( session != null ){
//								try {
//									session.close();
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//							}
//						}
					}
				}
			}
			// 发送邮件
			if( isChange && FAIL_TOMCAT_SERVERS != null ) {
				isChange = false;
				for( int i=0;i<maxSendCount;i++ ) {
					sendEmail("【tomcat服务器异常】", "异常服务器：" + FAIL_TOMCAT_SERVERS);
					try {
						TimeUnit.MILLISECONDS.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendEmail(String subject,String content){
		// 发送邮件
		List<SysUser> warningUserList = sysUserService.getUserByRoleCode(SystemCommonConstant.RENDER_WARNING);
		if( warningUserList != null && warningUserList.size() > 0 ){
			StringBuffer toEmailsStr = new StringBuffer();
			int count = 0;
			for( SysUser warningUser : warningUserList ){
				if( StringUtils.isNotBlank(warningUser.getEmail()) ) {
					if( count < warningUserList.size() ) {
						toEmailsStr.append(warningUser.getEmail() + ",");
					}else{
						toEmailsStr.append(warningUser.getEmail());
					}
					count++;
				}
			}
			if( toEmailsStr.length() > 0 ) {
				String[] toEmails = toEmailsStr.toString().split(",");
				SendEmail.massSend(toEmails, subject, content);
			}
		}else{
			logger.error("warningUserList is null");
		}
	}

	public static void main(String[] args) {
		/*String a = "ws://192.168.2.13:18080/app/websocket/common/obj/message/";
		int start = a.lastIndexOf("//")+2;
		String serverUri = a.substring(start);
		int end = serverUri.indexOf("/");
		//System.out.println(serverUri.substring(0,end));*/
//		FAIL_TOMCAT_SERVERS.add("123");
//		FAIL_TOMCAT_SERVERS.add("aaa");
//		FAIL_TOMCAT_SERVERS.add("4563");
//		FAIL_TOMCAT_SERVERS.add("vcx");
//		FAIL_TOMCAT_SERVERS.add("123");
		//System.out.println(FAIL_TOMCAT_SERVERS.remove("123"));
		//System.out.println(FAIL_TOMCAT_SERVERS.toString());
	}
}
