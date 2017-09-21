package com.zust.zfm.test;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

public class AuthenticatorTest {

	@Test  
	public void testAllSuccessfulStrategyWithSuccess() {  
		login("classpath:shiro-authenticator-all-success.ini");
		
		
	}
	
	private void login(String configFile) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
		SecurityManager manager = factory.getInstance();
		
		SecurityUtils.setSecurityManager(manager);

	}
	
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }

}
