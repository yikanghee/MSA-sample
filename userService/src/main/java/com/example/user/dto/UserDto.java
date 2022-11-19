package com.example.user.dto;

import com.example.user.vo.ResponseOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Date createdAt;
    private String encryptedPwd;
    private List<ResponseOrder> orders;

}
