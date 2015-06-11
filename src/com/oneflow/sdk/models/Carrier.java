package com.oneflow.sdk.models;

public class Carrier {
    protected String code;
    protected String service;
    protected String alias;

    public Carrier() {

    }

    public Carrier(String code, String service) {
        this.code = code;
        this.service = service;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
