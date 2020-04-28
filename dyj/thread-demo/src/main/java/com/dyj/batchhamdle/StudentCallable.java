package com.dyj.batchhamdle;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
        Thread.sleep(200);
        List<String> names = studentEntity.getNames();
        List<String> aliasNames = new ArrayList<>();
        for (String name : names) {
            aliasNames.add(name);
        }
        studentEntity.setAlisNames(aliasNames);
        log.info("-------------{}",studentEntity.getClassName());
        return studentEntity;
    }
}
