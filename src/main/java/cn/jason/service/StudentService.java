package cn.jason.service;

import cn.jason.mapper.slave.StudentMapper;
import cn.jason.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public Student getStuById(int sid) {
        Student stu = studentMapper.getStuById(sid);
        return stu;
    }
}
