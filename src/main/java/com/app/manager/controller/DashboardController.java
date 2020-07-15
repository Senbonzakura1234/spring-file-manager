package com.app.manager.controller;

import com.app.manager.entity.File;
import com.app.manager.model.midware_model.ModelFile;
import com.app.manager.service.interfaceClass.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    FileService fileService;

    @GetMapping({"/dashboard", "/"})
    public String index(Model model) {
        Pageable pageableIndexNumber = PageRequest.of(0, 5, Sort.by("indexNumber").ascending());
        Pageable pageableUpdatedAt = PageRequest.of(0, 5, Sort.by("updatedat").descending());

        Page<ModelFile> pendingFiles = fileService.getAll("",
                File.StatusEnum.PENDING, pageableIndexNumber);
        Page<ModelFile> downloadingFiles = fileService.getAll("",
                File.StatusEnum.DOWNLOADING, pageableUpdatedAt);
        Page<ModelFile> unzippingFiles = fileService.getAll("",
                File.StatusEnum.UNZIPPING, pageableUpdatedAt);
        Page<ModelFile> uploadingFiles = fileService.getAll("",
                File.StatusEnum.UPLOADING, pageableUpdatedAt);

        model.addAttribute("pendingParam", File.StatusEnum.PENDING.toString());
        model.addAttribute("downloadingParam", File.StatusEnum.DOWNLOADING.toString());
        model.addAttribute("unzippingParam", File.StatusEnum.UNZIPPING.toString());
        model.addAttribute("uploadingParam", File.StatusEnum.UPLOADING.toString());

        model.addAttribute("pendingCount", pendingFiles.getTotalElements());
        model.addAttribute("downloadingCount", downloadingFiles.getTotalElements());
        model.addAttribute("unzippingCount", unzippingFiles.getTotalElements());
        model.addAttribute("uploadingCount", uploadingFiles.getTotalElements());

        model.addAttribute("pendingFiles", pendingFiles);
        model.addAttribute("downloadingFiles", downloadingFiles);
        model.addAttribute("unzippingFiles", unzippingFiles);
        model.addAttribute("uploadingFiles", uploadingFiles);

        return "views/dashboard";
    }
}
