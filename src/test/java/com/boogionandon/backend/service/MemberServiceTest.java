package com.boogionandon.backend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.boogionandon.backend.domain.Member;
import com.boogionandon.backend.dto.PageRequestDTO;
import com.boogionandon.backend.dto.member.AdminResponseDTO;
import com.boogionandon.backend.dto.member.WorkerResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@Transactional
class MemberServiceTest {

  @Autowired
  private MemberService memberService;

  @Test
  @DisplayName("getWorkerProfile 테스트")
  void testGetWorkerProfile() {

    Long workerId = 8L; // initData 에 들어가 있는 Worker -> 8L, 9L, 10L, 11L;

    WorkerResponseDTO workerProfile = memberService.getWorkerProfile(workerId);

    log.info("workerProfile : " + workerProfile);

  }

  @Test
  @DisplayName("getAdminProfile 테스트")
  void testGetAdminProfile() {

    Long adminId = 5L; // initData 에 들어가 있는 Worker -> 8L, 9L, 10L, 11L;

    AdminResponseDTO adminProfile = memberService.getAdminProfile(adminId);

    log.info("adminProfile : " + adminProfile);

  }

  @Test
  @DisplayName("getMemberByRegularAdmin 테스트")
  void testGetMemberByRegularAdmin() {
    Long adminId = 6L; // initData 에 들어가 있는 Worker -> 8L, 9L, 10L, 11L;

    String nameSearch = "";

    String tabCondition = "전체"; // 전체, 조사/청소, 수거자

    // pageable 생성
    PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
    Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize(),
        pageRequestDTO.getSort().equals("desc") ?
            Sort.by("startDate").descending() :
            Sort.by("startDate").ascending()
    );

    Page<Member> memberList = memberService.getMemberByRegularAdmin(adminId, tabCondition, nameSearch, pageable);

    log.info("memberList : " + memberList);
  }

  @Test
  @DisplayName("getMemberBySuperAdmin 테스트")
  void testGetMemberBySuperAdmin() {
    Long adminId = 1L; // initData 에 들어가 있는 Worker -> 1L, 2L, 3L

    String nameSearch = "";

    String tabCondition = "수거자"; // 관리자, 조사/청소, 수거자

    // pageable 생성
    PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
    Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize(),
        pageRequestDTO.getSort().equals("desc") ?
            Sort.by("createdDate").descending() :
            Sort.by("createdDate").ascending()
    );

    Page<Member> memberList = memberService.getMemberBySuperAdmin(adminId, tabCondition, nameSearch, pageable);

    log.info("memberList : " + memberList);
  }
}