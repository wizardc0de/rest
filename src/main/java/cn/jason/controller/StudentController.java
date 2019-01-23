package cn.jason.controller;

import cn.jason.model.Student;
import cn.jason.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/student/get", method = RequestMethod.GET)
    public Map<String, Object> getBySid(int sid) {
        Map<String, Object> res = new HashMap<>();
        try {
            Student stu = studentService.getStuById(sid);
            res.put("status", 0);
            res.put("stuDetail", stu);
        } catch (Exception e) {
            res.put("status", 1);
        }
        return res;
    }
}
