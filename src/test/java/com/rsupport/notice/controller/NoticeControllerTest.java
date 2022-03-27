package com.rsupport.notice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsupport.notice.dto.NoticeCreateAndUpdate;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ActiveProfiles("test")
//@ContextConfiguration(classes = NoticeController.class)
@SpringBootTest
//@AutoConfigureMockMvc
class NoticeControllerTest {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  private static final String BASE_URL = "/v1/notice";

  @BeforeEach
  void init() {
    Locale.setDefault(Locale.US); // locale 설정에 따라 에러 메시지가 달라진다.
    // given
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @DisplayName("DTO 검증")
  @Test
  void validate() {

    // given
    final var validator = Validation.buildDefaultValidatorFactory().getValidator();

    var noticeCreate = NoticeCreateAndUpdate.builder()
        .build();

    // when
    final var constraintViolations = validator.validate(noticeCreate);
    // then
    System.out.printf("constraintViolations: \n%s %n", constraintViolations);
    assertEquals(4, constraintViolations.size());
//    assertEquals("must not be blank", constraintViolations.iterator().next().getMessage());
  }


  @DisplayName("생성 검증")
  @Test
  void createValid() throws Exception {
    //given
    var request = post(BASE_URL).contentType(MediaType.APPLICATION_JSON);
    var resultActions = mockMvc.perform(request);
    //when
    //then
    resultActions
        .andDo(print())
        .andExpect(status().isOk());
  }

}