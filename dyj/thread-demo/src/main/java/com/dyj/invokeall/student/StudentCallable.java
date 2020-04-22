package com.dyj.invokeall.student;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class StudentCallable implements Callable<StudentEntity> {

    private StudentEntity studentEntity;

    public StudentCallable() {}

    public StudentCallable(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    @Override
    public StudentEntity call() throws Exception {
//        StudentEntity result = new StudentEntity();

        List<Integer> ages = studentEntity.getAges();
        String className = studentEntity.getClassName();
        List<String> names = studentEntity.getNames();
        log.error("----------------------ClassName:{}", className);
        for( int i = 0; i < names.size(); i++) {
            log.error("---------name:{}", names.get(i));
            log.error("---------age:{}", ages.get(i));
        }

//        result.setClassName(className);
        return studentEntity;
    }
}
