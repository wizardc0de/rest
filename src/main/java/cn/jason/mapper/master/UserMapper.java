package cn.jason.mapper.master;

import cn.jason.model.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;


public interface UserMapper extends BaseMapper<User> {
    public User findUserInfo(int userId);
    public void updateUser(User user);
    public void deleteUser(String id);
    public void insertUser(User user);
}