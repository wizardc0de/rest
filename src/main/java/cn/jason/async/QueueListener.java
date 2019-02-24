package cn.jason.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    MockQueue mockQueue;

    @Autowired
    DeferredResultHolder deferredResultHolder;

    private Logger log= LoggerFactory.getLogger(QueueListener.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(()->{
            while (true){
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())){
                    String orderNum=mockQueue.getCompleteOrder();
                    log.info("开始处理订单:"+orderNum);
                    Map<String,Object> res=new HashMap<>();
                    res.put("resp",orderNum+"处理完成");
                    deferredResultHolder.getMap().get(orderNum).setResult(res);
                    mockQueue.setCompleteOrder(null);
                }else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
