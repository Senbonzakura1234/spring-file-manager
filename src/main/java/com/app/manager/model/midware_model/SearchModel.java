package com.app.manager.model.midware_model;

import javax.validation.constraints.NotNull;

public class SearchModel {
    @NotNull
    private String fileName;

    public SearchModel() {
    }

    public SearchModel(@NotNull String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
