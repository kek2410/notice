package com.rsupport.notice.dto;

import com.rsupport.notice.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class NoticeCreateAndUpdate {

  public Notice convertNotice() {
    return Notice.builder().build();
  }
}
