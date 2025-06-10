package org.suhodo.boot01.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.suhodo.boot01.dto.upload.UploadFileDTO;
import org.suhodo.boot01.dto.upload.UploadResultDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;


@RestController
@Log4j2
public class UpDownController {

    @Value("{org.suhodo.upload.path}")
    private String uploadPath;

    @Operation(description = "POST 방식으로 파일 업로드")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){
        
        log.info(uploadFileDTO);

        // 업로드된 파일들이 존재한다면
        if(uploadFileDTO.getFiles() != null){

            // 업로드된 파일을 가공 저장할 리스트 자료구조 생성
            final List<UploadResultDTO> list = new ArrayList<>();

            uploadFileDTO.getFiles().forEach(multipartFile -> {

                // 원본 파일 명
                String originalName = multipartFile.getOriginalFilename();
                log.info(originalName);

                // Unique ID : 모든 파일을 중복되지 않기 위해서 
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

                boolean image = false;

                try{
                    // 업로드된 파일을 저장소로 이동
                    multipartFile.transferTo(savePath);

                    // 파일 형식이 이미지 파일인지
                    if(Files.probeContentType(savePath).startsWith("image")){

                        // s_ 같 붙은 파일은 썸네일 이미지 파일을 생성한다.(200 x 200)
                        image = true;
                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
                        
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }

                    list.add(UploadResultDTO.builder()
                                .uuid(uuid)
                                .fileName(originalName)
                                .img(image).build());
                }catch(IOException e){
                    e.printStackTrace();
                }
            });

            return list;
        }

        return null;
    }
}
