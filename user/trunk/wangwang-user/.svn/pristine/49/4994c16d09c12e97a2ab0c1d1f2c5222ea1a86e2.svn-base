package com.sandu.user;

import com.sandu.common.http.HttpClientUtil;
import com.sandu.config.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sandu.UserProvider;
import com.sandu.api.user.input.UserLogin;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserProvider.class)
public class UserLoginTest {

	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6InBjXzJiX3VzZXJfdG9rZW46IiwiZXh0IjoxNTI2MDI0MzEyNTY1LCJ1aWQiOjQ4NjksIm10eXBlIjoiMyIsInVuYW1lIjoiQzAwMDAxMzBFSVUwMDAzIiwidXR5cGUiOjEsImFwcEtleSI6Ijc5MTdkZTVmLWMwYzItNDUxMC1iNDMxLTgyNWI3ZjFiMzAwNSIsInNlc3Npb25UaW1lb3V0IjoyNTkyMDAsInVwaG9uZSI6IiIsInVrZXkiOiI3OTE3ZGU1Zi1jMGMyLTQ1MTAtYjQzMS04MjViN2YxYjMwMDUiLCJpYXQiOjE1MjU3NjUxMTI1NjV9.fs0rt4pXJCw2DhEC1LZJbXrvkuUzaEeM1GFkCnJkGzc";

    @Test
    public void Test() {


		HttpClientUtil.doPostMethod(ResourceConfig.PAY_PACKAGE_ADD_URL,null,token);

		UserLogin userLogin = new UserLogin();
    	userLogin.setAccount("18098976455");
    	userLogin.setPassword("54a8df651a6f1b5c359c6e1d106da20d");
    	userLogin.setMediaType(1);
    	userLogin.setUsbTerminalImei("test");
    	userLogin.setTerminalImei("40167EA84221");
    	userLogin.setPlatformCode("pc2b");
    	//SysUserBizService sysUserBizService = (SysUserBizService)SpringContextHolder.getBean("pc2b");
    	//Object bo = sysUserBizService.login(userLogin);
    	//System.out.println(bo);
    }


   /* @Autowired
    private SysUserService sysUserService;

    @Test
    public void testGetExpireUser(){
		List<UserWillExpireVO> userWillExpire = sysUserService.getUserWillExpire(5, 2);
		System.out.println(userWillExpire.size());
		System.out.println(userWillExpire.toArray().toString());
	}*/
}
