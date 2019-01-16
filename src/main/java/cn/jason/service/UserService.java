package cn.jason.service;

import cn.jason.mapper.master.UserMapper;
import cn.jason.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserInfo(String userId){
        User user = userMapper.findUserInfo(Integer.parseInt(userId));
        return user;
    }

    public void updateUserInfo(User user){
        userMapper.updateUser(user);
    }

    public void deleteUser(String userId){
        userMapper.deleteUser(userId);
    }
    public void insertUser(User user){
        userMapper.insertUser(user);
    }

}
