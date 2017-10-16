package com.zust.zfm.test;

import static org.junit.Assert.*;

import java.util.Arrays;

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

public class RoleTest {
	
	@Test/*(expected = UnauthorizedException.class)*/  
	public void testCheckRole() {  
	    login("classpath:shiro-role.ini", "zhang", "123");  
	    //断言拥有角色：role1  
	    subject().checkRole("role1");  
	    //断言拥有角色：role1 and role3 失败抛出异常  
	    subject().checkRoles("role1", "role3");  
	}   
	
	@Test  
	public void testHasRole() {  
	    login("classpath:shiro-role.ini", "zhang", "123");  
	    //判断拥有角色：role1  
	    Assert.assertTrue(subject().hasRole("role1"));  
	    //判断拥有角色：role1 and role2  
	    Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));  
	    //判断拥有角色：role1 and role2 and !role3  
	    boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));  
	    Assert.assertEquals(true, result[0]);  
	    Assert.assertEquals(true, result[1]);  
	    Assert.assertEquals(false, result[2]);  
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
