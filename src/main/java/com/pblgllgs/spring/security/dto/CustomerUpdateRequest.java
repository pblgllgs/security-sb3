package com.pblgllgs.spring.security.dto;

import com.pblgllgs.spring.security.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {
    private String name;
    private Integer age;
    private Gender gender;
}
