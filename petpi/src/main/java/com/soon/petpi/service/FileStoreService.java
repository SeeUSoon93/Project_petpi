package com.soon.petpi.service;

import com.soon.petpi.model.entity.UploadImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStoreService {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        Path path = Paths.get("");
        String absolutePath = path.toAbsolutePath().toString();
        log.info(absolutePath);
        return absolutePath + fileDir + filename;
    }

    public UploadImage uploadFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreName(originalFilename); // 서버 저장 파일명 uuid.ext
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadImage(originalFilename, storeFileName);
    }

    public void delete(String fileName) {
        String filePath = getFullPath(fileName);
        File file = new File(filePath);
        file.delete();
    }

    private static String createStoreName(String originalFilename) {
        String ext = extractExt(originalFilename); // ext 추출
        String uuid = UUID.randomUUID().toString(); // uuid 생성
        return uuid + "." + ext;
    }

    private static String extractExt(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position +1);
    }
}
