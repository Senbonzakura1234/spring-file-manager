package com.app.manager.controller;

import com.app.manager.entity.File;
import com.app.manager.model.midware_model.SearchModel;
import com.app.manager.service.interfaceClass.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ApiController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    FileService fileService;
    @PostMapping("/api/search")
    public ResponseEntity<?> index(@Valid @RequestBody SearchModel search, Errors errors) {
        if(errors.hasErrors()) return ResponseEntity.badRequest().body(errors.getAllErrors());
        Sort sortable = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(0, 20, sortable);
        Page<String> files = fileService.getAllName(search.getFileName(), File.StatusEnum.ALL, pageable);
        return ResponseEntity.ok(files);
    }
}
