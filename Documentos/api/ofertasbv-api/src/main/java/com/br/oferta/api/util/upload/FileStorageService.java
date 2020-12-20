package com.br.oferta.api.util.upload;

import com.br.oferta.api.util.upload.exception.FileStorageException;
import com.br.oferta.api.util.upload.exception.MyFileNotFoundException;
import com.br.oferta.api.util.upload.property.OfertaApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private String nomeCompleto;

    @Autowired
    public FileStorageService(OfertaApiProperty fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String[] output = fileName.split("\\.");
        String nome = output[0];
        String extenção = output[1];
        UUID d = UUID.randomUUID();
        nome = d.toString();

        nomeCompleto = nome + "." + extenção;
        System.out.println("fileName: " + nome);

        try {
            // Check if the file's name contains invalid characters
            if (nomeCompleto.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + nomeCompleto);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(nomeCompleto);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return nomeCompleto;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + nomeCompleto + ". Please try again!", ex);
        }
    }

    public String nomeArquivoDownload() {
        return nomeCompleto;
    }

    public Resource loadFileAsResource(String fileName) {

        try {

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
