package com.app.manager.service.implementClass;

import com.app.manager.entity.File;
import com.app.manager.model.HelperMethod;
import com.app.manager.model.midware_model.ModelFile;
import com.app.manager.model.returnResult.DatabaseQueryResult;
import com.app.manager.repository.FileRepository;
import com.app.manager.service.interfaceClass.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileServiceImp implements FileService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    FileRepository fileRepository;
    private static final double KiloBytesToBytes = 1000;
    private static final double MegaBytesToBytes = KiloBytesToBytes*KiloBytesToBytes;
    private static final double GigaBytesToBytes = MegaBytesToBytes*KiloBytesToBytes;

    @Override
    public List<ModelFile> export() {
        try {
            List<File> files = fileRepository.findByOrderByIndexNumber();
            return ModelFile.castToListModels(files);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void getFileCapacity() {
        try {
            List<File> files = fileRepository.findAll();

            for (File item: files
            ) {
                try {
                    var oldName = item.getName();
                    Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(oldName);
                    while(m.find()) {
                        String capacity = m.group(1);
                        double capacityDouble = Double.parseDouble(
                                capacity.replaceAll("[^\\d.]", ""));

                        if(capacity.contains(" B")){
                            item.setCapacity(capacityDouble);
                        } else if(capacity.contains(" kB")){
                            item.setCapacity(capacityDouble*KiloBytesToBytes);
                        } else if(capacity.contains(" MB")){
                            item.setCapacity(capacityDouble*MegaBytesToBytes);
                        } else if(capacity.contains(" GB")){
                            item.setCapacity(capacityDouble*GigaBytesToBytes);
                        } else {
                            continue;
                        }
                        fileRepository.save(item);
                        System.out.println(item.getName() + ": " + item.getCapacity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeSlash() {
        try {
            List<File> files = fileRepository.findAll();
            for (File item: files
                 ) {
                var oldName = item.getName();
                if(oldName != null && !oldName.isEmpty()){
                    var replace  = oldName.replaceAll("/"," ");
                    item.setName(replace);
                    fileRepository.save(item);
                }
                System.out.println(item.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Page<String> getAllName(String queryName, File.StatusEnum status, Pageable pageable) {
        try {
            Page<File> files;
            if(queryName != null && !queryName.isEmpty()){
                if(status != null && status != File.StatusEnum.ALL){
                    files = fileRepository.findByNameContainsAndStatus(queryName, status, pageable);
                }else {
                    files = fileRepository.findByNameContains(queryName, pageable);
                }
            }else {
                if(status != null && status != File.StatusEnum.ALL){
                    files = fileRepository.findByStatus(status, pageable);
                }else {
                    files = fileRepository.findBy(pageable);
                }
            }
            return files.map(File::getName);
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty();
        }
    }

    @Override
    public Page<ModelFile> getAll(String queryName, File.StatusEnum status, Pageable pageable) {
        try {
            Page<File> files;
            if(queryName != null && !queryName.isEmpty()){
                if(status != null && status != File.StatusEnum.ALL){
                    files = fileRepository.findByNameContainsAndStatus(queryName, status, pageable);
                }else {
                    files = fileRepository.findByNameContains(queryName, pageable);
                }
            }else {
                if(status != null && status != File.StatusEnum.ALL){
                    files = fileRepository.findByStatus(status, pageable);
                }else {
                    files = fileRepository.findBy(pageable);
                }
            }
            return  files.map(file -> new ModelFile(file.getId(), file.getIndexNumber(),
                    file.getName(), file.getCapacity(), file.getStatus(),
                    HelperMethod.getDateString(file.getCreatedat()),
                    HelperMethod.getDateString(file.getUpdatedat())));
        } catch (Exception e) {
            e.printStackTrace();
            return Page.empty();
        }
        
    }

    @Override
    public DatabaseQueryResult save(ModelFile modelFile) {
        try {
            fileRepository.save(ModelFile.castToEntity(modelFile));
            return new DatabaseQueryResult(true,
                    "save file success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseQueryResult(false,
                    "save file failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<File> getOne(String id) {
        try {
            return fileRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public DatabaseQueryResult update(ModelFile modelFile, String id) {
        return new DatabaseQueryResult(true, "on development", HttpStatus.ACCEPTED);
    }

    @Override
    public DatabaseQueryResult updateStatus(File.StatusEnum status, String id) {
        if (status != null && status != File.StatusEnum.ALL
                && id != null && !id.isEmpty()) {
            try {
                Optional<File> file = fileRepository.findById(id);
                if(file.isEmpty()) return new DatabaseQueryResult(
                        false, "file not found", HttpStatus.NOT_FOUND);
                File fileUpdate = file.get();
                fileUpdate.setStatus(status);
                fileUpdate.setUpdatedat(System.currentTimeMillis());
                fileRepository.save(fileUpdate);
                return new DatabaseQueryResult(
                        true, "file updated", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new DatabaseQueryResult(
                        false, e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new DatabaseQueryResult(
                false, "request not pass validation", HttpStatus.BAD_REQUEST);
    }

    @Override
    public DatabaseQueryResult updateStatusMulti(File.StatusEnum status, List<String> listId) {
        try {
            if(status != null && status != File.StatusEnum.ALL
                    && listId != null && listId.size() > 0){
                List<File> files = fileRepository.findByIdIn(listId);
                for (File file: files
                     ) {
                    file.setStatus(status);
                    file.setUpdatedat(System.currentTimeMillis());
                }
                if(files.size() > 0){
                    fileRepository.saveAll(files);
                }
            }
            return new DatabaseQueryResult(
                    true, "check log for more result",
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseQueryResult(
                    false, "check log for more result",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public DatabaseQueryResult delete(String id) {
        return new DatabaseQueryResult(true, "on development", HttpStatus.ACCEPTED);
    }
}
