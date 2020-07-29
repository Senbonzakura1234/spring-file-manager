package com.app.manager.service.interfaceClass;

import com.app.manager.entity.File;
import com.app.manager.model.midware_model.ModelFile;
import com.app.manager.model.returnResult.DatabaseQueryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FileService {
    List<ModelFile> export();
    void getFileCapacity();
    void removeSlash();
    Page<String> getAllName(String queryName, File.StatusEnum status, Pageable pageable);
    Page<ModelFile> getAll(String queryName, File.StatusEnum status, Pageable pageable);
    DatabaseQueryResult save(ModelFile modelFile);
    Optional<File> getOne(String id);
    DatabaseQueryResult update(ModelFile modelFile, String id);
    DatabaseQueryResult updateStatus(File.StatusEnum status, String id);
    DatabaseQueryResult updateStatusMulti(File.StatusEnum status, List<String> listId);
    DatabaseQueryResult delete(String id);
}
