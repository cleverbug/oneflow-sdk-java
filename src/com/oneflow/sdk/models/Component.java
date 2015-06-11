package com.oneflow.sdk.models;

public class Component {
    protected String code;
    protected String path;
    protected boolean fetch;
    protected boolean infotech;
    protected boolean localFile;
    protected boolean preflight;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFetch() {
        return fetch;
    }

    public void setFetch(boolean fetch) {
        this.fetch = fetch;
    }

    public boolean isInfotech() {
        return infotech;
    }

    public void setInfotech(boolean infotech) {
        this.infotech = infotech;
    }

    public boolean isLocalFile() {
        return localFile;
    }

    public void setLocalFile(boolean localFile) {
        this.localFile = localFile;
    }

    public boolean isPreflight() {
        return preflight;
    }

    public void setPreflight(boolean preflight) {
        this.preflight = preflight;
    }
}
