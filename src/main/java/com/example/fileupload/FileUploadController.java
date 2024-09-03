package com.example.fileupload;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileupload.model.FileEntity;
import com.example.fileupload.service.FileStorageService;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public FileEntity uploadFile(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping
    public List<FileEntity> listAllFiles() {
        return fileStorageService.getAllFiles();
    }

    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        fileStorageService.deleteFile(fileName);
        return ResponseEntity.ok("File deleted successfully");
    }

    @PutMapping("/update/{fileName:.+}")
    public ResponseEntity<FileEntity> updateFile(@PathVariable String fileName, @RequestParam("file") MultipartFile file) {
        FileEntity updatedFile = fileStorageService.updateFile(fileName, file);
        return ResponseEntity.ok(updatedFile);
    }
}
