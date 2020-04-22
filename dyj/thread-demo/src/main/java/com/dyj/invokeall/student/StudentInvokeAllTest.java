package com.dyj.invokeall.student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class StudentInvokeAllTest {

    private static List<StudentEntity> initData() {
        List<StudentEntity> allStudent = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            StudentEntity students = new StudentEntity();
            students.setClassName("ClassName_" + i);
            List<String> names = new ArrayList<>();
            List<Integer> ages = new ArrayList<>();
            for(int j = 0; j < 50; j ++) {
                names.add(students.getClassName() + "_name_" + j);
                ages.add(j);
            }
            students.setNames(names);
            students.setAges(ages);

            allStudent.add(students);
        }
        return allStudent;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<StudentEntity> studentEntities = initData();
        List<StudentCallable> task = new ArrayList<>();
        for(int i = 0; i < studentEntities.size(); i++) {
            task.add(new StudentCallable(studentEntities.get(i)));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<StudentEntity>> futuresList = executorService.invokeAll(task);

        for (Future<StudentEntity> studentEntityFuture : futuresList) {
            StudentEntity studentEntity = studentEntityFuture.get();
            System.out.println(studentEntity.getClassName());
        }
    }
}
