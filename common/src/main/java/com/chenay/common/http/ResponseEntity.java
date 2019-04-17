package com.chenay.common.http;

public class ResponseEntity<T> extends HttpEntity<T> {

    private Object status;

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }
}
