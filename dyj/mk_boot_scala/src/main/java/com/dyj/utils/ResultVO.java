package com.dyj.utils;

import lombok.Data;

import java.io.Serializable;

/***
 * http 请求返回的对象
 * @param <T>
 */
@Data
public class ResultVO<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;
}
