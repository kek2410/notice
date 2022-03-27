package com.rsupport.notice.service;

import com.rsupport.notice.entity.AttachFile;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  private static final String TEMP_PATH = "./temp";

  public List<AttachFile> stores(MultipartFile[] multipartFiles) {
    if (multipartFiles == null || multipartFiles.length == 0) {
      return Collections.emptyList();
    }
    return Arrays.stream(multipartFiles)
        .map(this::store)
        .collect(Collectors.toList());
  }

  public AttachFile store(MultipartFile multipartFile) {
    var file = new File(TEMP_PATH + File.separator + multipartFile.getOriginalFilename());
    try {
      multipartFile.transferTo(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return AttachFile.builder()
        .path(TEMP_PATH)
        .size(multipartFile.getSize())
        .name(multipartFile.getOriginalFilename())
        .type(multipartFile.getContentType())
        .build();
  }

}
