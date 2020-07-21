package com.app.manager.model.midware_model;

import javax.validation.constraints.NotBlank;

public class SearchModel {
    @NotBlank
    private String fileName;

    public SearchModel(@NotBlank String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
