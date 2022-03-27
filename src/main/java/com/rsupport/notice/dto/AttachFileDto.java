package com.rsupport.notice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttachFileDto {

  private final String name;
  private final String path;
  private final String type;
  private final Long size;

}
