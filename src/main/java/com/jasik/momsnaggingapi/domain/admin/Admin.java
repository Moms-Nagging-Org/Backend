package com.jasik.momsnaggingapi.domain.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Admin {
    @Schema(description = "대시보드 서비스 정보")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceData {
        private Long nOfUsers;
        private Long nOfQuestions;
    }

    @Schema(description = "대시보드 상장 정보")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GradeData {
        private int nOfLv1;
        private int nOfLv2;
        private int nOfLv3;
        private int nOfLv4;
    }

    @Schema(description = "대시보드 조회 시 응답 클래스")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DashBoardResponse {
        private ServiceData service;
        private GradeData grade;
    }
}