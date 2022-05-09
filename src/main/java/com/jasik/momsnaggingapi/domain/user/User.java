package com.jasik.momsnaggingapi.domain.user;

import com.jasik.momsnaggingapi.domain.common.BaseTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int naggingLevel;
    private String nickName;
    private String email;
    private String provider;
    private String personalId;
    private String profileImage;

    @Column(columnDefinition = "varchar(30) default 'MEMBER'")
    private String role;
    @Column(columnDefinition = "TEXT")
    private String statusMsg;

    @Column(columnDefinition = "boolean default true")
    private boolean allowGeneralNotice;
    @Column(columnDefinition = "boolean default true")
    private boolean allowTodoNotice;
    @Column(columnDefinition = "boolean default true")
    private boolean allowRoutineNotice;
    @Column(columnDefinition = "boolean default true")
    private boolean allowWeeklyNotice;
    @Column(columnDefinition = "boolean default true")
    private boolean allowOtherNotice;

    @Builder
    public User(int naggingLevel, String nickName, String email,
                String provider, String personalId, String profileImage, String statusMsg) {
        this.naggingLevel = naggingLevel;
        this.nickName = nickName;
        this.email = email;
        this.provider = provider;
        this.personalId = personalId;
        this.profileImage = profileImage;
        this.statusMsg = statusMsg;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponse {
        private Long id;
        private String email;
        private String provider;
        private String nickName;
        private String personalId;
        private int naggingLevel;
        private String statusMsg;
        private String profileImage;
        private boolean allowGeneralNotice;
        private boolean allowTodoNotice;
        private boolean allowRoutineNotice;
        private boolean allowWeeklyNotice;
        private boolean allowOtherNotice;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRequest {
        private Long id;
        private String email;
        private String provider;
        private String nickName;
        private String personalId;
        private int naggingLevel;
        private String statusMsg;
        private String profileImage;
        private boolean allowGeneralNotice;
        private boolean allowTodoNotice;
        private boolean allowRoutineNotice;
        private boolean allowWeeklyNotice;
        private boolean allowOtherNotice;
    }

}
