package com.app.manager.model.midware_model;

import com.app.manager.entity.File;
import com.app.manager.model.HelperMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ModelFile {
    private String id;
    private int indexNumber;
    private String name;
    private BigDecimal capacity;
    private File.StatusEnum status;
    private String capacityString;
    private String CreatedAt;
    private String UpdatedAt;

    private static final BigDecimal BytesToKiloBytes = new BigDecimal(1000);
    private static final BigDecimal BytesToMegaBytes = BytesToKiloBytes.multiply(BytesToKiloBytes);
    private static final BigDecimal BytesToGigaBytes = BytesToMegaBytes.multiply(BytesToKiloBytes);

    public ModelFile() {
    }

    public  ModelFile(int indexNumber, String name) {
        this.indexNumber = indexNumber;
        this.name = name;
    }

    public ModelFile(String id, int indexNumber, String name, BigDecimal capacity,
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

    public static String convertCapacity(BigDecimal capacity){
        if (capacity == null) return "Undefined";
        try {
            if(capacity.compareTo(BytesToGigaBytes) > 0)
                return capacity.divide(BytesToGigaBytes, 2, RoundingMode.UP).toString() + " GB";
            if(capacity.compareTo(BytesToMegaBytes) > 0)
                return capacity.divide(BytesToMegaBytes, 2, RoundingMode.UP).toString() + " MB";
            if(capacity.compareTo(BytesToKiloBytes) > 0)
                return capacity.divide(BytesToKiloBytes, 2, RoundingMode.UP).toString() + " KB";
            return capacity.toString() + " B";
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

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getCapacityString() {
        return capacityString;
    }

    public void setCapacityString(String capacityString) {
        this.capacityString = capacityString;
    }
}
