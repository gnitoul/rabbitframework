package com.rabbitframework.web;

/**
 * 请求头部信息
 */
public class RequestHeaderInfo {
    private Integer operationSource;

    public void setOperationSource(Integer operationSource) {
        this.operationSource = operationSource;
    }

    public Integer getOperationSource() {
        return operationSource;
    }
}
