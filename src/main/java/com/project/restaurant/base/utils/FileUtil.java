package com.project.restaurant.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
public class FileUtil {
    private FileUtil() {
    }

    public static void uploadFile(String uploadDir, MultipartFile picture) {
        try {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
            Path path = Paths.get(uploadDir + filename);
            Files.copy(picture.getInputStream(), path,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("IO Exception {}", e.getMessage());
        }
    }
}
