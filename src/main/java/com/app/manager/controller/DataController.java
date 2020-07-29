package com.app.manager.controller;

import com.app.manager.model.midware_model.ModelFile;
import com.app.manager.model.returnResult.JsonResult;
import com.app.manager.service.interfaceClass.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    FileService fileService;

//    @PostMapping("/data/import")
    public ResponseEntity<?> importFile(
            @Valid @RequestBody List<ModelFile> files , Errors errors){
        if(errors.hasErrors()) return ResponseEntity.badRequest().body(errors.getAllErrors());
        List<JsonResult> results = new ArrayList<>();
        for (var file: files
        ) {
            System.out.println(file.getName());
//            var result = fileService.save(file);
//            results.add(JsonResult.castToJsonResult(result));
            results.add(new JsonResult(true, "Okay", HttpStatus.OK));
        }
        return ResponseEntity.ok(results);
    }

//    @GetMapping("/data/capacity")
    public ResponseEntity<?> addCapacity(){
        fileService.getFileCapacity();
        return ResponseEntity.ok("okay");
    }
//    @GetMapping("/data/file")
    public ResponseEntity<?> removeSlash(){
        fileService.removeSlash();
        return ResponseEntity.ok("okay");
    }
    @GetMapping("/data/export")
    public ResponseEntity<?> exportFile(){
        var files = fileService.export();
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] buffer = mapper.writeValueAsBytes(files);

            return ResponseEntity.ok().contentLength(buffer.length).contentType(
                    MediaType.parseMediaType("application/octet-stream"))
                    .header("Content-Disposition", "attachment; filename=\"database.json\"")
                    .body(new InputStreamResource(new ByteArrayInputStream(buffer)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
