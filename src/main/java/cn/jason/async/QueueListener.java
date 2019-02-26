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

/**
 *一个队列的监听器，继承自spring 的事件监听器，监听上下文有修改时的事件(ContextRefreshedEvent)
 */
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    MockQueue mockQueue;

    @Autowired
    DeferredResultHolder deferredResultHolder;

    private Logger log= LoggerFactory.getLogger(QueueListener.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //此处开启线程是因为监听事件会阻塞应用的启动，一直卡在此处，无法正常启动服务
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
