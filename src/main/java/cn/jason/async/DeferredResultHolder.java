package cn.jason.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<Map<String ,Object>>> map=new HashMap<>();

    public Map<String, DeferredResult<Map<String ,Object>>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<Map<String ,Object>>> map) {
        this.map = map;
    }
}
