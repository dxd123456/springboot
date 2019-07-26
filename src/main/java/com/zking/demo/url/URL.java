package com.zking.demo.url;

import com.zking.demo.util.HttpServletUtil;

import java.io.Serializable;

public class URL implements Serializable {
    private static final long serialVersionUID = -7266578122460485781L;
    private String url;
    public URL(){}

    /**
     * 封装url地址,自动添加应用上下文路径
     *
     * @param url URL地址
     */
    public URL(String url){
        this.url = HttpServletUtil.getRequest().getContextPath() +url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
