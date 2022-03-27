package com.rsupport.notice.controller;

import com.rsupport.notice.dto.NoticeCreateAndUpdate;
import com.rsupport.notice.dto.NoticeDto;
import com.rsupport.notice.service.NoticeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/v1/notice")
@RequiredArgsConstructor
@Validated
public class NoticeController {

  private final NoticeService noticeService;

  @PostMapping
  public ResponseEntity<NoticeDto> create(@RequestBody @Valid NoticeCreateAndUpdate noticeCreate, @RequestParam("files") MultipartFile[] multipartFiles) {
    return ResponseEntity.ok(noticeService.create(noticeCreate, multipartFiles));
  }

  @PutMapping("/{id}")
  public ResponseEntity<NoticeDto> update(@PathVariable Long id, @RequestBody @Valid NoticeCreateAndUpdate request) {
    return ResponseEntity.ok(noticeService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    noticeService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<NoticeDto> notice(@PathVariable Long id) {
    return ResponseEntity.of(noticeService.notice(id));
  }

}
