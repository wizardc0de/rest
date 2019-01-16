package cn.jason.controller;

import cn.jason.model.User;
import cn.jason.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserInfo(@RequestParam(value = "userId") String userId) {
        Map<String,Object> result =new HashMap<>();
        try{
            User user = userService.getUserInfo(userId);
            result.put("status",0);
            result.put("userDetail",user);
        }catch (Exception e) {
            result.put("status",1);
        }
        return result;
    }
  /*  @RequestMapping(value = "/sel",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> sel(@RequestParam(value = "low")int low ,@RequestParam(value = "high")int high  ) {
        Map<String,Object> result =new HashMap<>();
        Map<String,Object> params =new HashMap<>();
        params.put("low",low);
        params.put("high",high);
        try{
            List<User> users = userService.selectUsers(params);
            result.put("status",0);
            result.put("userDetail",users);
        }catch (Exception e) {
            result.put("status",1);
        }
        return result;
    }*/

    @RequestMapping("/del")
    @ResponseBody
    public Map<String, Object> del(@RequestParam(value = "userId") String userId) {
        Map<String,Object> result =new HashMap<String,Object>();
        try {
            userService.deleteUser(userId);
            result.put("status",0);
            result.put("message","已删除");
        }catch (Exception e){
            result.put("status",1);
            result.put("message","删除出错");
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestParam(value = "userId") User user) {
        Map<String,Object> result =new HashMap<String,Object>();
        try {
            userService.updateUserInfo(user);
            result.put("status",0);
            result.put("message","已修改");
        }catch (Exception e){
            result.put("status",1);
            result.put("message","修改出错");
        }
        return result;
    }

}
