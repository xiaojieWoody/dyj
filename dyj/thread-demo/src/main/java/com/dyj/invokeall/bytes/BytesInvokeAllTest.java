package com.dyj.invokeall.bytes;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BytesInvokeAllTest {

    public static List<byte[]> initData() {
        List<byte[]> result = new ArrayList<>();

        for(int i = 49; i < 123; i++) {
            result.add(new byte[]{(byte)i});

            byte[] bytes = result.get(i - 49);
            String s = new String(bytes);
            System.out.println("init-------------" + s);
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<BytesCallable> taskList = new ArrayList<>();

        List<byte[]> bytesList = initData();
        int num = bytesList.size();
        int pageSize = 8;
        int totalPage = num % pageSize == 0 ? num / pageSize : num / pageSize + 1;
        for(int i = 0; i < totalPage; i ++) {
            int start = 0;
            int end = 0;
            if((i * pageSize + pageSize) <= num) {
                start = i * pageSize;
                end = i * pageSize + pageSize;
            } else if(( i * pageSize + pageSize) > num) {
                start = i * pageSize;
                end = num;
            } else if (num < pageSize) {
                start = 0;
                end = num;
            }
            List<byte[]> subBytesList = bytesList.subList(start, end);
            taskList.add(new BytesCallable(subBytesList));
        }
        List<Future<List<byte[]>>> futureList = executorService.invokeAll(taskList);
        List<byte[]> allRes = new ArrayList<>();
        for (Future<List<byte[]>> listFuture : futureList) {
            List<byte[]> bytes = listFuture.get();
            allRes.addAll(bytes);
        }
        for (byte[] allRe : allRes) {
            System.out.println(new String(allRe));
        }
    }

//    public static void main(String[] args) throws Exception {
//        List<byte[]> result = new ArrayList<>();
//
//        for(int i = 49; i < 123; i++) {
//            result.add(new byte[]{(byte)i});
//
//            byte[] bytes = result.get(i - 49);
//            String s = new String(bytes);
//            System.out.println("init-------------" + s);
//        }
//
//        List<byte[]> bytes = batchComplianceImageProcess(result);
//        for (byte[] allRe : bytes) {
//            System.out.println(new String(allRe));
//        }
//    }
}
