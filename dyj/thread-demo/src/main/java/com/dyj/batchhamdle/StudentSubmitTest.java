package com.dyj.batchhamdle;

import com.alibaba.fastjson.JSON;
import com.dyj.customize.CustomizeThreadPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class StudentSubmitTest {

    public static void main(String[] args) throws Exception {
        submitHandle();
//        invokeAllHandle();
    }

    /**
     * submit方式处理，无顺序
     * @throws IOException
     */
    public static void submitHandle() throws IOException {

        List<String> names = initData();

        List<StudentEntity> resEntities = new ArrayList<>();
        ExecutorService executorService = CustomizeThreadPool.executorService;
        List<Future<StudentEntity>> allFuture = new ArrayList<>();
        int total = names.size();
        int pageSize = 3;
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        for(int i = 0; i < totalPage; i++) {
            int start = 0;
            int end = 0;
            if((i * pageSize + pageSize) <= total) {
                start = i * pageSize;
                end = i * pageSize + pageSize;
            } else if((i * pageSize + pageSize) > total) {
                start = i * pageSize;
                end = total;
            } else if( pageSize > total) {
                start = 0;
                end = total;
            }
            List<String> subNames = names.subList(start, end);
            StudentEntity stuEntity = new StudentEntity();
            stuEntity.setNames(subNames);
            stuEntity.setClassName("class_" + i);
            Future<StudentEntity> future = executorService.submit(new StudentCallable(stuEntity));
            allFuture.add(future);
        }

        do {
            Iterator<Future<StudentEntity>> iterator = allFuture.iterator();
            allFuture = new ArrayList<>();
            while (iterator.hasNext()) {
                Future<StudentEntity> future = iterator.next();
                try {
                    StudentEntity resStuEntity = future.get(100, TimeUnit.MILLISECONDS);
                    resEntities.add(resStuEntity);
                } catch (TimeoutException e) {
                    allFuture.add(future);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }while (allFuture.size() > 0);
        System.out.println(JSON.toJSONString(resEntities));
    }

    /**
     * invokeAll方式，按提交List中顺序返回
     * @throws IOException
     * @throws InterruptedException
     */
    public static void invokeAllHandle() throws IOException, InterruptedException {
        List<String> names = initData();
        ExecutorService executorService = CustomizeThreadPool.executorService;

        List<StudentCallable> invokeAllStu = new ArrayList<>();
        List<StudentEntity> result = new ArrayList<>();

        int total = names.size();
        int pageSize = 3;
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        for(int i = 0; i < totalPage; i++) {
            int start = 0;
            int end = 0;
            if((i * pageSize + pageSize) <= total) {
                start = i * pageSize;
                end = i * pageSize + pageSize;
            } else if((i * pageSize + pageSize) > total) {
                start = i * pageSize;
                end = total;
            } else if(pageSize > total) {
                start = 0;
                end = total;
            }
            List<String> subNames = names.subList(start, end);
            StudentEntity stuEntity = new StudentEntity();
            stuEntity.setNames(subNames);
            stuEntity.setClassName("class_" + i);

            invokeAllStu.add(new StudentCallable(stuEntity));
        }

        List<Future<StudentEntity>> futures = executorService.invokeAll(invokeAllStu);
        do {
            Iterator<Future<StudentEntity>> iterator = futures.iterator();
            futures = new ArrayList<>();
            while (iterator.hasNext()) {
                Future<StudentEntity> future = iterator.next();
                try {
                    StudentEntity studentEntity = future.get(100, TimeUnit.MILLISECONDS);
                    result.add(studentEntity);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    futures.add(future);
                }
            }
        } while (futures.size() > 0);

        System.out.println(JSON.toJSONString(result));
    }

    /**
     * 初始化测试数据
     * @return
     */
    private static List<String> initData() {
        List<String> names = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            names.add("name_" + i);
        }
        return names;
    }
}
