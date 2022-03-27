package com.rsupport.notice.entity;

import com.rsupport.notice.dto.AttachFileDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "attach_file")
public class AttachFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attach_file_id")
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String path;
  @Column(nullable = false)
  private String type;
  @Column(nullable = false)
  private Long size;

  @Builder.Default
  @JoinColumn(name = "notice_id", nullable = false)
  @ManyToOne
  private Notice notice = new Notice();

  public void setNotice(Notice notice) {
    this.notice = notice;
  }

  public AttachFileDto convertDto() {
    return AttachFileDto.builder()
        .name(this.name)
        .path(this.path)
        .type(this.type)
        .size(this.size)
        .build();
  }

}
