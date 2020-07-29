package com.app.manager.repository;

import com.app.manager.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FileRepository extends JpaRepository<File, String> {
    List<File> findByOrderByIndexNumber();
    List<File> findByIdIn(List<String> listId);
    Page<File> findByNameContains(String queryName, Pageable pageable);
    Page<File> findByNameContainsAndStatus(String queryName, File.StatusEnum status, Pageable pageable);
    Page<File> findByStatus(File.StatusEnum status, Pageable pageable);
    Page<File> findBy(Pageable pageable);
}
