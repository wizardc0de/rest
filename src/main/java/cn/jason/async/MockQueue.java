package cn.jason.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {

    private String placeOrder;

    private String completeOrder;

    private Logger log= LoggerFactory.getLogger(MockQueue.class);
    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(()->{
            log.info(Thread.currentThread().getName());
            log.info("接到订单："+placeOrder);
            this.completeOrder= placeOrder;
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
