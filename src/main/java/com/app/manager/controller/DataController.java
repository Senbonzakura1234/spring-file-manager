package com.app.manager.controller;

import com.app.manager.model.midware_model.ModelFile;
import com.app.manager.model.returnResult.JsonResult;
import com.app.manager.service.interfaceClass.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    FileService fileService;

//    @PostMapping("/data/file")
    public List<JsonResult> index(
            @RequestBody List<ModelFile> files){
        List<JsonResult> results = new ArrayList<>();
        for (var file: files
        ) {
            System.out.println(file.getName());
            var result = fileService.save(file);
            results.add(JsonResult.castToJsonResult(result));
        }
        return results;
    }

    @GetMapping("/data/capacity")
    public ResponseEntity<?> addCapacity(){
        fileService.getFileCapacity();
        return ResponseEntity.ok("okay");
    }
//    @GetMapping("/data/file")
    public ResponseEntity<?> removeSlash(){
        fileService.removeSlash();
        return ResponseEntity.ok("okay");
    }
}
