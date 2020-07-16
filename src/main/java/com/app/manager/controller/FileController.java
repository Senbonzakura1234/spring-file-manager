package com.app.manager.controller;

import com.app.manager.entity.File;
import com.app.manager.model.SelectOption;
import com.app.manager.model.midware_model.ModelFile;
import com.app.manager.model.returnResult.DatabaseQueryResult;
import com.app.manager.service.interfaceClass.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class FileController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    FileService fileService;

    @GetMapping({"/file", "/file/index"})
    public String index(Model model,
        @RequestParam(value = "queryName", required = false, defaultValue = "") String queryName,
        @RequestParam(value = "fileStatus", required = false) File.StatusEnum fileStatus,
        @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
        @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {

        List<SelectOption> statusList = new ArrayList<>();
        for (File.StatusEnum item: File.StatusEnum.values()
        ) {
            var selectedNotnull = fileStatus != null;
            statusList.add(new SelectOption(item.name(),
                    item == File.StatusEnum.UNDEFINED? "-select status-": item.name(),
                    selectedNotnull && item == fileStatus));
        }
        model.addAttribute("statusList", statusList);
        model.addAttribute("queryName", queryName);


        var status = fileStatus != null? fileStatus : File.StatusEnum.UNDEFINED;
        Sort sortable = sort.equals("DESC")?
                Sort.by("indexNumber").descending():
                Sort.by("indexNumber").ascending();

        Pageable pageable = PageRequest.of(page <= 0? 0: page - 1, size, sortable);
        Page<ModelFile> files = fileService.getAll(queryName, status, pageable);


        var totalPages = files.getTotalPages();
        var currentPage = pageable.getPageNumber();
        var totalItems = files.getTotalElements();
        var offset = (totalItems - files.getPageable().getOffset());
        var currentPageItems = offset < size? offset : size;


        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("offset", offset);
        model.addAttribute("page", page <= 0? 1: page);
        model.addAttribute("count", currentPageItems);
        model.addAttribute("files", files);
        model.addAttribute("query", queryName);
        model.addAttribute("status", status);
        return "views/files/index";
    }

    @PostMapping({"/file", "/file/index"})
    public String updateStatus(
            @RequestParam(value = "checkedItem", required = false) List<String> listId,
            @RequestParam(value = "status", required = false) File.StatusEnum status){
        if (status != null && status != File.StatusEnum.UNDEFINED
            && listId != null && listId.size() > 0) {
            DatabaseQueryResult result = fileService.updateStatusMulti(status, listId);
            System.out.println(result.getDescription());
        }
        return "redirect:/file";
    }
}
