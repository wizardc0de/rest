package cn.jason.service;

import cn.jason.mapper.master.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 构建一个从数据读取的用户并授权admin权限的用户
     * 返回一个继承UserDetails接口的User类
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        cn.jason.model.User u=userMapper.findUserByUserName(s);
        String password =u.getPassword();
        log.info("username: "+s);
        //springsecurity5.0以后必须对password进行加密，否则汇报error
        PasswordEncoder encoder =PasswordEncoderFactories.createDelegatingPasswordEncoder();
        /*User的默认构造方法有7个参数，前面3个username，password,authority,用于验证
        后面4个参数accountNonExpired，accountNonLocked，credentialsNonExpired，enabled;用于账户的设置*/
        return User.builder().username(s).password(encoder.encode(password)).roles("admin").accountLocked(true).build();
    }
}
