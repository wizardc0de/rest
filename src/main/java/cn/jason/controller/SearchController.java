package cn.jason.controller;

import cn.jason.config.service.ElasticsearchService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/search")
    public Map<String,Object> search(@RequestParam(defaultValue = "1") int pageNo,@RequestParam(defaultValue = "10") int pageSize ,String keyword){
        Map<String,Object> result=new HashMap<>();
        try{
            Page<Map<String,Object>> page=elasticsearchService.searchDocument(pageNo,pageSize,keyword,"titile","content");
            result.put("hits",page);
            result.put("status",0);
        }catch(Exception e){
            e.printStackTrace();
            result.put("status",1);
        }
        return result;
    }
}
