package org.novi.languagelearner.services;

import org.novi.languagelearner.entities.Photo;
import org.novi.languagelearner.repositories.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class PhotoService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final FileUploadRepository repo;

    public PhotoService(@Value("${my.upload.location}") String fileStorageLocation, FileUploadRepository repo) throws IOException {

        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.repo = repo;

        Files.createDirectories(fileStoragePath);

    }

    public String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + "\\" + filename);


        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        repo.save(new Photo(filename));
        return filename;
    }

    public Resource downloadFile(String filename) {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(filename);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());

        } catch ( MalformedURLException e ) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("The file doesn't exist or is not readable");
        }
    }
}













