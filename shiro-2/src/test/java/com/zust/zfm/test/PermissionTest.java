package com.zust.zfm.test;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PermissionTest {

	@Test/*(expected = UnauthorizedException.class)*/  
	public void testCheckPermission () {  
	    login("classpath:shiro-permission.ini", "zhang", "123");  
	    //断言拥有权限：user:create  
	    subject().checkPermission("user:create");  
	    //断言拥有权限：user:delete and user:update  
	    subject().checkPermissions("user:delete", "user:update");  
	    //断言拥有权限：user:view 失败抛出异常  
	    subject().checkPermissions("user:view");  
	}   
	
	@Test
	public void testIsPermitted() {
		login("classpath:shiro-permission.ini", "zhang", "123");
		
		//判断拥有权限：user:create  
	    Assert.assertTrue(subject().isPermitted("user:create"));  
	    //判断拥有权限：user:update and user:delete  
	    Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));  
	    //判断没有权限：user:view  
	    Assert.assertFalse(subject().isPermitted("user:view"));  
	}
	
	private Subject subject() {
		return SecurityUtils.getSubject();
	}

	private void login(String configFile, String username, String password) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
		SecurityManager manager = factory.getInstance();
		
		SecurityUtils.setSecurityManager(manager);
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		
		Subject subject = SecurityUtils.getSubject();
		
		subject.login(token);

	}
	
	
	
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }

}
