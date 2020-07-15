package com.app.manager.model.midware_model;

import com.app.manager.entity.File;
import com.app.manager.model.HelperMethod;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class ModelFile {
    private String id;
    private int indexNumber;
    private String name;
    private File.StatusEnum status;
    private String CreatedAt;
    private String UpdatedAt;

    public ModelFile() {
    }

    public ModelFile(int indexNumber, String name) {
        this.indexNumber = indexNumber;
        this.name = name;
    }

    public ModelFile(String id, int indexNumber, String name,
                     File.StatusEnum status, String createdAt, String updatedAt) {
        this.id = id;
        this.indexNumber = indexNumber;
        this.name = name;
        this.status = status;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
    }


    public static List<ModelFile> castToListModels(List<File> files){
        List<ModelFile> modelFiles = new ArrayList<>();
        if (files == null) return modelFiles;
        for (var item: files) {
            modelFiles.add(castToObjectModel(item));
        }
        return modelFiles;
    }

    public static ModelFile castToObjectModel(File file){
        if(file == null) return new ModelFile();
        return new ModelFile(file.getId(), file.getIndexNumber(),
                file.getName(), file.getStatus(),
                HelperMethod.getDateString(file.getCreatedat()),
                HelperMethod.getDateString(file.getUpdatedat()));
    }

    public static File castToEntity(ModelFile modelFile){
        File file = new File();
        file.setName(modelFile.getName());
        file.setIndexNumber(modelFile.getIndexNumber());
        return file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File.StatusEnum getStatus() {
        return status;
    }

    public void setStatus(File.StatusEnum status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }
}
