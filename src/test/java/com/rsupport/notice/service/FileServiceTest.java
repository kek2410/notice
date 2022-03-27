package com.rsupport.notice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

//@SpringBootTest
class FileServiceTest {

//  @Autowired
//  private FileService fileService;

  @Test
  void store() throws IOException {
    //given
    var fileName = "test";
    var contentType = "jpg";
    var filePath = "src/test/temp/image1.jpg";
    //when
    var mockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);
    var getFileName = mockMultipartFile.getOriginalFilename().toLowerCase();
    var storedFile = fileName.toLowerCase() + "." + contentType;
    //then
    System.out.printf("getFileName if %s = %s %n", getFileName, storedFile);
    assertEquals(getFileName, storedFile, "not");
  }

  private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
    var fileInputStream = new FileInputStream(path);
    return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
  }


}