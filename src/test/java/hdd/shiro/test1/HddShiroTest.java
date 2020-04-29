package hdd.shiro.test1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;
import org.apache.shiro.mgt.SecurityManager;

import java.util.Arrays;

public class HddShiroTest {

    /**
     * 1、 创建token令牌，token中有用户提交的认证信息即账号和密码
     * 2、 执行subject.login(token)，最终由securityManager通过Authenticator进行认证
     * 3、 Authenticator的实现ModularRealmAuthenticator调用realm从ini配置文件取用户真实的账号和密码，这里使用的是IniRealm（shiro自带）
     * 4、 IniRealm先根据token中的账号去ini中找该账号，如果找不到则给ModularRealmAuthenticator返回null，如果找到则匹配密码，匹配密码成功则认证通过。
     */
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

    @Test
    public void testCustomRealmLoginLogout(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-realm.ini");
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

    @Test
    public void testCustomRealmMD5LoginLogout(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-realm2.ini");
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

    @Test
    public void testPermission(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-permission.ini");
        //通过工厂创建SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //将SecurityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建Subject实例，该实例认证要使用SecurityManager进行
        Subject subject = SecurityUtils.getSubject();
        //创建token，记录用户认证的身份和凭证（即账号和密码）
        UsernamePasswordToken token = new UsernamePasswordToken("hdd","123");
        //用户登录
        subject.login(token);
        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态："+isAuthenticated);
        /**用户授权检测，基于角色授权**/
        //是否有某一个角色
        System.out.println("用户是否拥有role1角色："+subject.hasRole("role1"));
        //是否有多个角色
        System.out.println("用户是否拥有role1角色和role2角色："+subject.hasRoles(Arrays.asList("role1","role2")));
        //subject.checkRole("role1");//授权检测，检测失败抛出异常
        //subject.checkRoles(Arrays.asList("role1","role2"));
        /**基于资源授权**/
        //是否有某一个权限
        System.out.println("用户是否拥有某一个权限："+subject.isPermitted("user:update"));
        //是否有多个权限
        System.out.println("用户是否拥有多个权限："+subject.isPermittedAll("user:update","user:create"));
        //subject.checkPermission("user:update");//检测权限
        //subject.checkPermissions("user:update","user:create");
    }

    /**
     * 1、执行subject.isPermitted("user:create")
     * 2、securityManager通过ModularRealmAuthorizer进行授权
     * 3、ModularRealmAuthorizer调用realm获取权限信息
     * 4、ModularRealmAuthorizer再通过permissionResolver解析权限字符串，校验是否匹配
     */
    @Test
    public void testCustomRealmPermission(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-realm.ini");
        //通过工厂创建SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //将SecurityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建Subject实例，该实例认证要使用SecurityManager进行
        Subject subject = SecurityUtils.getSubject();
        //创建token，记录用户认证的身份和凭证（即账号和密码）
        UsernamePasswordToken token = new UsernamePasswordToken("hdd","123");
        //用户登录
        subject.login(token);
        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态："+isAuthenticated);
        /**用户授权检测，基于角色授权**/
        //是否有某一个角色
        System.out.println("用户是否拥有role1角色："+subject.hasRole("role1"));
        //是否有多个角色
        System.out.println("用户是否拥有role1角色和role2角色："+subject.hasRoles(Arrays.asList("role1","role2")));
        //subject.checkRole("role1");//授权检测，检测失败抛出异常
        //subject.checkRoles(Arrays.asList("role1","role2"));
        /**基于资源授权**/
        //是否有某一个权限
        System.out.println("用户是否拥有某一个权限："+subject.isPermitted("user:update"));
        //是否有多个权限
        System.out.println("用户是否拥有多个权限："+subject.isPermittedAll("user:update","user:create"));
        //subject.checkPermission("user:update");//检测权限
        //subject.checkPermissions("user:update","user:create");
    }

}
