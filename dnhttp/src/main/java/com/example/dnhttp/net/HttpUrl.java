package com.example.dnhttp.net;


import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrl {
    private String host;
    private String file;
    private String protocol;
    private int port;

    public HttpUrl(String url) {
        try {
            URL urll = new URL("http://www.kuaidi100.com/query?type=yuantong&postid=111111111");
            host = urll.getHost();
            file = urll.getFile();
            file = TextUtils.isEmpty(file)?"/":file;
            protocol = urll.getProtocol();
            port = urll.getPort();
            port = port==-1?urll.getDefaultPort():port;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
