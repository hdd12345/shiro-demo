package hdd.shiro.test1;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

public class OtherTest {

    @Test
    public void md5Test(){
        String pwd = "123";
        String pwd_md5 = new Md5Hash(pwd).toString();//md5不加盐
        System.out.println("MD5不加盐："+pwd_md5);
        String salt = "abcdef";//随机盐
        String pwd_md5_salt_1 = new Md5Hash(pwd,salt,1).toString();//MD5加盐一次散列 aa7c9c12fc740955ef4dfad670250ff4
        System.out.println("MD5加盐一次散列："+pwd_md5_salt_1);
        String simpleHash = new SimpleHash("MD5",pwd,salt,1).toString();
        System.out.println(simpleHash);
    }

}
