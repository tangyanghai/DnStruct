package com.example.dnhttp.net;

import java.util.Map;

/**
 * 存储请求内容
 */
public class Request {
    private Map<String,String> headers;
    private String method;
    private HttpUrl url;
    private RequestBody body;
}
