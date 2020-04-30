package com.dyj.test;

import com.dyj.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private Integer count = 0;

    @RequestMapping("/testLog")
    public void test() throws Exception {
        System.out.println("1：" + count);
        Test.printLog();
        System.out.println("2：" + count);
        count++;
    }
}
