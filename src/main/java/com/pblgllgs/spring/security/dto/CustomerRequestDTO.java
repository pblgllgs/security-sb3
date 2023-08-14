package com.pblgllgs.spring.security.dto;

import com.pblgllgs.spring.security.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private Integer id;
    private String name;
    private String email;
    private Gender gender;
    private Integer age;
    private List<String> roles = new ArrayList<>();
    private String username;
    private String password;
}