package com.rsupport.notice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.rsupport.notice.dto.NoticeCreateAndUpdate;
import com.rsupport.notice.dto.NoticeDto;
import com.rsupport.notice.entity.Notice;
import com.rsupport.notice.repository.NoticeRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//@SpringBootTest
//@Transactional
@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

  @Mock NoticeService noticeService;
  @Mock NoticeRepository noticeRepository;
  @Mock FileService fileService;

  private Long id;

  @Test
  void mock() {
    // given
    var noticeDto = NoticeDto.builder().id(1L).title("testNotice").build();
    given(noticeService.notice(1L)).willReturn(Optional.of(noticeDto));
    // when
    when(noticeService.notice(1L));
    // then
  }

  @DisplayName("기본데이터 저장")
  @BeforeEach
  void set() {
    var entity =
        Notice.builder()
            .title("this.title")
            .startOn(LocalDateTime.now())
            .endOn(LocalDateTime.now().plusMonths(2))
            .createdBy("this.userId")
            .updatedBy("this.userId")
            .build();
    var test = noticeRepository.saveAndFlush(entity);
    var delete = noticeRepository.saveAndFlush(entity);

    id = test.getId();
  }

  @DisplayName("공지사항 저장")
  @Test
  void create() {
    // given
    var request =
        NoticeCreateAndUpdate.builder()
            .userId("test")
            .startOn(LocalDateTime.now())
            .endOn(LocalDateTime.now().plusMonths(1))
            .title("testTitle111")
            .build();
    // when
    var id = noticeService.create(request, null);
    var notice = noticeRepository.findById(id);
    // then
    assertAll(
        () -> assertTrue(notice.isPresent(), "not found"),
        () -> assertEquals(request.getUserId(), notice.get().getCreatedBy()),
        () -> assertEquals(request.getTitle(), notice.get().getTitle()));
  }

  @DisplayName("공지사항 수정")
  @Test
  void update() {
    // given
    var updateDto =
        NoticeCreateAndUpdate.builder()
            .title("updateTitle")
            .userId("updateUser")
            .startOn(LocalDateTime.now())
            .endOn(LocalDateTime.now().plusMonths(1))
            .build();
    // when
    var update = noticeService.update(id, updateDto);
    // then
    assertAll(
        () -> assertEquals("updateTitle", update.getTitle()),
        () -> assertEquals("updateUser", update.getUpdatedBy()));
  }

  @DisplayName("공지사항 조회")
  @Test
  void notice() {
    // given
    // when
    var notice = noticeService.notice(id);
    // then
    assertTrue(notice.isPresent(), "ERROR FIND");
    //    assertEquals("this.title", notice.get().getTitle());
  }

  @DisplayName("공지사항 삭제")
  @Test
  void delete() {
    // given
    // when
    noticeService.delete(id);
    var notice = noticeRepository.findById(id);
    // then
    assertTrue(notice.isEmpty(), "No delete yet.");
  }
}
