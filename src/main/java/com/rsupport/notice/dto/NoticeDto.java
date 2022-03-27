package com.rsupport.notice.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class NoticeDto {

  private final Long id;
  private final String title;
  private final Integer count;
  private final LocalDateTime createdOn;
  private final String createdBy;
  private final LocalDateTime updatedOn;
  private final String updatedBy;
  private final List<AttachFileDto> attachFileDtos;

}
