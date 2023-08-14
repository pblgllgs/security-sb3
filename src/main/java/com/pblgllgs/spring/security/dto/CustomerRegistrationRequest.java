package com.pblgllgs.spring.security.dto;

import com.pblgllgs.spring.security.entity.Gender;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String password,
        Integer age,
        Gender gender
) {

}
