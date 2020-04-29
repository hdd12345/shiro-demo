package hdd.shiro.test1;

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

public class CustomRealm1 extends AuthorizingRealm {

    @Override
    public String getName(){
        return "customRealm1";
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取身份信息
        String username = principalCollection.getPrimaryPrincipal().toString();
        /** 从数据库获取权限信息，此处使用静态数据模拟 **/
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:create");
        permissions.add("user:update");
        //将权限信息放入AuthorizationInfo中
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        permissions.forEach(permission -> simpleAuthorizationInfo.addStringPermission(permission));
        return simpleAuthorizationInfo;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从token中获取用户身份信息
        String username = authenticationToken.getPrincipal().toString();
        //根据username到数据库查询 ...
        //如果查询不到返回null
        if(!"hdd".equals(username)) return null; //模拟查询不到
        //获取从数据库查询出来的密码（此处使用静态数据模拟）
        String password = "123";
        //返回认证信息由父类AuthenticatingRealm进行认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,getName());
        return simpleAuthenticationInfo;
    }
}
