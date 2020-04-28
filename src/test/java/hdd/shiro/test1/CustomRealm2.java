package hdd.shiro.test1;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class CustomRealm2 extends AuthenticatingRealm {

    @Override
    public String getName(){
        return "customRealm1";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从token中获取用户身份信息
        String username = authenticationToken.getPrincipal().toString();
        //根据username到数据库查询 ...
        //如果查询不到返回null
        if(!"hdd".equals(username)) return null; //模拟查询不到
        //获取从数据库查询出来的密码（此处使用静态数据模拟）
        String password = "aa7c9c12fc740955ef4dfad670250ff4";//数据库中查询到的加密后的密码(加盐一次散列)
        String salt = "abcdef";//盐，随机数，此随机数也在数据库中存储
        //返回认证信息由父类AuthenticatingRealm进行认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes(salt),getName());
        return simpleAuthenticationInfo;
    }
}
