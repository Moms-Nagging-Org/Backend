package com.jasik.momsnaggingapi.domain.user.service;

import com.jasik.momsnaggingapi.domain.question.Question;
import com.jasik.momsnaggingapi.domain.question.service.QuestionService;
import com.jasik.momsnaggingapi.domain.schedule.Schedule;
import com.jasik.momsnaggingapi.domain.user.Interface.UserFollowInterface;
import com.jasik.momsnaggingapi.domain.user.User;
import com.jasik.momsnaggingapi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final QuestionService questionService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Long countUser() {
        return userRepository.count();
    }

    public User.UserResponse findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));
        return modelMapper.map(user, User.UserResponse.class);
    }

    public Page<User> findAllUsers(Pageable pageable, String search) {
        return userRepository.findByPersonalIdContainingIgnoreCase(search, pageable);
    }

    @Transactional
    public User.Response editUser(Long id, User.UpdateRequest user) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        if(StringUtils.isNotBlank(user.getNickName())) {
            existUser.setNickName(user.getNickName());
        }
        if(user.getStatusMsg() != null) {
            if(StringUtils.isBlank(user.getStatusMsg())) {
                existUser.setStatusMsg("오늘 하루도 파이팅 🔥");
            } else {
                existUser.setStatusMsg(user.getStatusMsg());
            }
        }
        if(user.getNaggingLevel() != null) {
            existUser.setNaggingLevel(user.getNaggingLevel());
        }
        if (user.getAllowRoutineNotice() != null) {
            existUser.setAllowRoutineNotice(user.getAllowRoutineNotice());
        }
        if (user.getAllowTodoNotice() != null) {
            existUser.setAllowTodoNotice(user.getAllowTodoNotice());
        }
        if (user.getAllowWeeklyNotice() != null) {
            existUser.setAllowWeeklyNotice(user.getAllowWeeklyNotice());
        }
        if (user.getAllowOtherNotice() != null) {
            existUser.setAllowOtherNotice(user.getAllowOtherNotice());
        }

        return modelMapper.map(userRepository.save(existUser), User.Response.class);
    }

    @Transactional
    public User.Response removeUser(Long id, Question.SignOutReasonRequest request) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

        questionService.createSignOutReason(id, request);
        userRepository.deleteById(id);

        User.Response res = new User.Response();
        res.setId(id);

        return res;
    }

    public List<User.PublicUserResponse> findUserByPersonalId(Long id, String personalId) {
        List<UserFollowInterface> userFollowInterfaces = userRepository.findAllByPersonalIdContainingIgnoreCaseAndIdNot(id, personalId);
        return userFollowInterfaces.stream()
                .map(u -> User.PublicUserResponse.builder()
                        .personalId(u.getUser().getPersonalId())
                        .id(u.getUser().getId())
                        .nickName(u.getUser().getNickName())
                        .createdAt(u.getUser().getCreatedAt())
                        .isFollowing(u.getFollow() != null && !u.getFollow().isBlocked())
                        .build())
                .collect(Collectors.toList());
    }
}
