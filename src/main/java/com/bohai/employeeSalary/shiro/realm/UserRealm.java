package com.bohai.employeeSalary.shiro.realm;


import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.bohai.employeeSalary.dao.SysUserMapper;
import com.bohai.employeeSalary.entity.SysUser;

public class UserRealm extends AuthorizingRealm {
    
    static Logger logger = Logger.getLogger(UserRealm.class);
    
    @Autowired
    private SysUserMapper sysUserMapper;

//登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        
    	UsernamePasswordToken token=(UsernamePasswordToken) authcToken;
        String username = (String) token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        
        SysUser sysUser = this.sysUserMapper.queryUserByUsername(username);
        if(sysUser == null){
            logger.warn("无此用户信息,用户名："+username);
            throw new UnknownAccountException("无此用户信息,用户名："+username);
        }
        
        logger.info("token: "+JSON.toJSONString(token));
        
        //return new SimpleAuthenticationInfo(username, sysUser.getPassword(), getName());
        return new SimpleAuthenticationInfo(username, password, getName());
        
    }

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

}
