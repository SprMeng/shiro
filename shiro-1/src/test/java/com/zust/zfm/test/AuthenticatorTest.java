package com.zust.zfm.test;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

import junit.framework.Assert;

public class AuthenticatorTest {

	@Test  
	public void testAllSuccessfulStrategyWithSuccess() {  
		login("classpath:shiro-authenticator-all-success.ini");
		
		Subject subject = SecurityUtils.getSubject();
		
		PrincipalCollection collection = subject.getPrincipals();
		
		Assert.assertEquals(2, collection.asList().size());
		
	}
	
	private void login(String configFile) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
		SecurityManager manager = factory.getInstance();
		
		SecurityUtils.setSecurityManager(manager);
		
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		
		Subject subject = SecurityUtils.getSubject();
		
		subject.login(token);

	}
	
	
	
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }

}
