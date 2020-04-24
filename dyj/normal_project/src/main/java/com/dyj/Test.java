package com.dyj;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

    public static void printLog() {
        for(int i = 0; i < 10; i++) {
            log.info("111111111111111111111111");
            log.error("22222222222222222222222");
        }
    }
}
