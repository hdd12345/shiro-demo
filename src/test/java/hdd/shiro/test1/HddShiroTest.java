package hdd.shiro.test1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;
import org.apache.shiro.mgt.SecurityManager;

public class HddShiroTest {

    @Test
    public void testLoginLogout(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro.ini");
        //通过工厂创建SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //将SecurityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建Subject实例，该实例认证要使用SecurityManager进行
        Subject subject = SecurityUtils.getSubject();
        //创建token，记录用户认证的身份和凭证（即账号和密码）
        UsernamePasswordToken token = new UsernamePasswordToken("hdd","123");
        try{
            //用户登录
            subject.login(token);
        }catch (AuthenticationException e){
            e.printStackTrace();
        }
        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态："+isAuthenticated);
        subject.logout();
        isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态："+isAuthenticated);
    }
}
