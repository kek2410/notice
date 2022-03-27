package com.rsupport.notice.service;

import com.rsupport.infra.exception.NotFoundException;
import com.rsupport.notice.dto.NoticeCreateAndUpdate;
import com.rsupport.notice.dto.NoticeDto;
import com.rsupport.notice.repository.NoticeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final FileService fileService;

  public NoticeDto create(NoticeCreateAndUpdate noticeCreate, MultipartFile[] multipartFiles) {
    var notice = noticeCreate.convertNotice();
    notice.addAllFile(fileService.stores(multipartFiles));
    noticeRepository.saveAndFlush(notice);
    return notice.convertDto();
  }

  public NoticeDto update(Long id, NoticeCreateAndUpdate request) {
    var notice = noticeRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    notice.update(request);
    noticeRepository.flush();
    return notice.convertDto();
  }

  public void delete(Long id) {
    var notice = noticeRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    noticeRepository.delete(notice);
    noticeRepository.flush();
  }

  public Optional<NoticeDto> notice(Long id) {
    var optionalNotice = noticeRepository.findById(id);
    if (optionalNotice.isEmpty()) {
      return Optional.empty();
    }
    var notice = optionalNotice.get();
    notice.increaseCount();
    return Optional.of(notice.convertDto());
  }

}
