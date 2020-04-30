package hdd.realm;

import hdd.entity.ActiveUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Override
    public String getName(){
        return "userRealm";
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        if(!"hdd".equals(username))return null; //模拟查询不到
        String password = "123";//从数据库查询出的密码（静态数据模拟）
        List<String> menuList = new ArrayList<>();
        //构建用户信息
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUserId(username);
        activeUser.setUsername(username);
        activeUser.setUserCode(username);
        activeUser.setMenus(menuList);
        return new SimpleAuthenticationInfo(activeUser,password,getName());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取身份信息
        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
        String userId = activeUser.getUserId();//用户ID
        //根据用户id从数据库查询权限数据（此处使用静态数据模拟
        List<String> permissions = new ArrayList<>();
        //将权限信息封闭为AuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        permissions.forEach(p -> simpleAuthorizationInfo.addStringPermission(p));
        return simpleAuthorizationInfo;
    }
}
