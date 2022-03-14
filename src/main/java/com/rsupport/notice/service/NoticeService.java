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

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;

  @Transactional
  public Long create(NoticeCreateAndUpdate noticeCreate) {
    var notice = noticeCreate.convertNotice();
    noticeRepository.saveAndFlush(notice);
    return notice.getId();
  }

  @Transactional
  public NoticeDto update(Long id, NoticeCreateAndUpdate request) {
    var notice = noticeRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    notice.update(request);
    return notice.convertDto();
  }

  @Transactional
  public void delete(Long id) {
    var notice = noticeRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    noticeRepository.delete(notice);
  }

  @Transactional(readOnly = true)
  public Optional<NoticeDto> notice(Long id) {
    var optionalNotice = noticeRepository.findById(id);
    if (optionalNotice.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(optionalNotice.get().convertDto());
  }

}
