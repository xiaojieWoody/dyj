package com.dyj.invokeall.bytes;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.concurrent.Callable;

public class BytesCallable implements Callable<List<byte[]>> {
    private List<byte[]> bytes;

    public BytesCallable() {}

    public BytesCallable(List<byte[]> bytes) {
        this.bytes = bytes;
    }

    @Override
    public List<byte[]> call() throws Exception {

        System.out.println("--------------------------" + JSON.toJSONString(bytes));

        return bytes;
    }
}
