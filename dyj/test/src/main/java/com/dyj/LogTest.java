package com.dyj;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTest {

    @GetMapping("/test")
    public String test() throws Exception {
        Test.printLog();
        return "123";
    }

}
