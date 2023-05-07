package com.greenThumb.service;

import com.greenThumb.domain.Post;
import com.greenThumb.domain.User;
import com.greenThumb.dto.request.UserRequestDto;
import com.greenThumb.dto.response.PostResponseDto;
import com.greenThumb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(UserRequestDto dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        return userRepository.save(dto.toEntity()).getId();
    }

    /*
    회원가입 시 유효성 체크
     */
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format(("valid_%s"), error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }


}