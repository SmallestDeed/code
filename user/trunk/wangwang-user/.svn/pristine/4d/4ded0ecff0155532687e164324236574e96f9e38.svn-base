package com.sandu.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sandu.UserProvider;
import com.sandu.api.user.service.TestService;

@EnableAspectJAutoProxy
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserProvider.class)
public class UserLoginTest {
	@Autowired
	private TestService testService;
    @Test
    public void Test() { 
    	//testService.test("abd");
    }
}
