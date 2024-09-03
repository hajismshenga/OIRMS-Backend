package com.example.fileupload.repository;

import com.example.fileupload.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    void deleteByFileName(String fileName);
}
