package com.example.noormart.Service.ServiceImpl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
        String imageName=multipartFile.getOriginalFilename();
        String random= UUID.randomUUID().toString();
        String fileName=random.concat(imageName.substring(imageName.lastIndexOf(".")));
        String filePath=path+ File.separator+fileName;
        File f=new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        return fileName;
    }
    public String deleteImage(String path,String fileName) throws IOException {
        String fullPath=path+File.separator+fileName;
        Files.deleteIfExists(Path.of(fullPath));
        return "Image deleted successfully";
    }
    public InputStream getSource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }
}
