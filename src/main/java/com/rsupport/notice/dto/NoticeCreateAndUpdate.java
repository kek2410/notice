package com.rsupport.notice.dto;

import com.rsupport.notice.entity.Notice;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@ToString
public class NoticeCreateAndUpdate {

  @Length(min = 1, max = 64) // 최대 길이 64
  @NotBlank // 빈문자열은 안됨
  private final String title;
  @NotNull
  private final LocalDateTime startOn;
  @NotNull
  private final LocalDateTime endOn;
  @Builder.Default
  private final Integer count = 0;
  @NotNull
  private final String userId;

  public Notice convertNotice() {
    return Notice.builder()
        .title(this.title)
        .startOn(this.startOn)
        .endOn(this.endOn)
        .createdBy(this.userId)
        .updatedBy(this.userId)
        .build();
  }
}
