package com.app.manager.model.midware_model;

import com.app.manager.entity.File;
import com.app.manager.model.HelperMethod;

import java.util.ArrayList;
import java.util.List;

public class ModelFile {
    private String id;
    private int indexNumber;
    private String name;
    private double capacity;
    private File.StatusEnum status;
    private String capacityString;
    private String CreatedAt;
    private String UpdatedAt;

    private static final double BytesToKiloBytes = 1000;
    private static final double BytesToMegaBytes = BytesToKiloBytes*BytesToKiloBytes;
    private static final double BytesToGigaBytes = BytesToMegaBytes*BytesToKiloBytes;

    public ModelFile() {
    }

    public  ModelFile(int indexNumber, String name) {
        this.indexNumber = indexNumber;
        this.name = name;
    }

    public ModelFile(String id, int indexNumber, String name, double capacity,
                     File.StatusEnum status, String createdAt, String updatedAt) {
        this.id = id;
        this.indexNumber = indexNumber;
        this.name = name;
        this.capacity = capacity;
        this.status = status;
        capacityString = convertCapacity(capacity);
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
    }

    public static String convertCapacity(double capacity){
        try {
            if(capacity > BytesToGigaBytes)
                return capacity/BytesToGigaBytes + " GB";
            if(capacity > BytesToMegaBytes)
                return capacity/BytesToMegaBytes + " MB";
            if(capacity > BytesToKiloBytes)
                return capacity/BytesToKiloBytes + " KB";
            return capacity + " B";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "Undefined";
        }
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
                file.getName(), file.getCapacity(), file.getStatus(),
                HelperMethod.getDateString(file.getCreatedat()),
                HelperMethod.getDateString(file.getUpdatedat()));
    }

    public static File castToEntity(ModelFile modelFile){
        File file = new File();
        file.setName(modelFile.getName());
        file.setIndexNumber(modelFile.getIndexNumber());
        file.setCapacity(modelFile.getCapacity());
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

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getCapacityString() {
        return capacityString;
    }

    public void setCapacityString(String capacityString) {
        this.capacityString = capacityString;
    }
}
