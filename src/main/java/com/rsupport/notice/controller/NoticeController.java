package com.rsupport.notice.controller;

import com.rsupport.notice.dto.NoticeCreateAndUpdate;
import com.rsupport.notice.dto.NoticeDto;
import com.rsupport.notice.service.NoticeService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody NoticeCreateAndUpdate noticeCreate) {
    return ResponseEntity.created(URI.create("/v1/notice/" + noticeService.create(noticeCreate)))
        .build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<NoticeDto> update(@PathVariable Long id, NoticeCreateAndUpdate request) {
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
