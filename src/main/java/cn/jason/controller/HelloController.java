package cn.jason.controller;


import cn.jason.async.DeferredResultHolder;
import cn.jason.async.MockQueue;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    MockQueue mockQueue;

    @Autowired
    DeferredResultHolder deferredResultHolder;

    private Logger log= LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    @ResponseBody
    public DeferredResult<Map<String ,Object>> greeting(@RequestParam(value="id") int orderId, Model model) {
        log.info("主线程开始");
        String num =String.valueOf(orderId);
        mockQueue.setPlaceOrder(num);
        DeferredResult<Map<String ,Object>> result=new DeferredResult<>();
        deferredResultHolder.getMap().put(num,result);
        log.info("主线程返回");

        return result;
    }
    
}
