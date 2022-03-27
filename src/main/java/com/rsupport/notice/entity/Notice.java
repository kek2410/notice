package com.rsupport.notice.entity;

import com.rsupport.notice.dto.NoticeCreateAndUpdate;
import com.rsupport.notice.dto.NoticeDto;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "notice")
@Table
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_id")
  private Long id;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private LocalDateTime startOn;
  @Column(nullable = false)
  private LocalDateTime endOn;
  @Column(nullable = false)
  @Builder.Default
  private Integer count = 0;
  @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<AttachFile> attachFiles = new HashSet<>();
  @Column(nullable = false)
  @Builder.Default
  private LocalDateTime createdOn = LocalDateTime.now();
  @Column(nullable = false)
  private String createdBy;
  @Column(nullable = false)
  @Builder.Default
  private LocalDateTime updatedOn = LocalDateTime.now();
  @Column(nullable = false)
  private String updatedBy;

  // domain method
  public void update(NoticeCreateAndUpdate request) {
    this.title = request.getTitle();
    this.startOn = request.getStartOn();
    this.endOn = request.getEndOn();
    this.updatedBy = request.getUserId();
    this.updatedOn = LocalDateTime.now();
  }

  public void addFile(AttachFile attachFile) {
    attachFile.setNotice(this);
    this.attachFiles.add(attachFile);
  }

  public void addAllFile(List<AttachFile> attachFiles) {
    if (CollectionUtils.isEmpty(attachFiles)) {
      return;
    }
    if (!CollectionUtils.isEmpty(this.attachFiles)) {
      this.attachFiles.clear();
    }
    attachFiles.forEach(dto -> dto.setNotice(this));
    this.attachFiles.addAll(attachFiles);
  }

  public void increaseCount() {
    this.count++;
  }

  // business method
  public NoticeDto convertDto() {
    var attachDto = !this.attachFiles.isEmpty() ?
        this.attachFiles.stream()
            .map(AttachFile::convertDto)
            .collect(Collectors.toList())
        : null;
    return NoticeDto.builder()
        .id(this.id)
        .title(this.title)
        .count(this.count)
        .createdOn(this.createdOn)
        .createdBy(this.createdBy)
        .updatedOn(this.updatedOn)
        .updatedBy(this.updatedBy)
        .attachFileDtos(attachDto)
        .build();

  }


}
