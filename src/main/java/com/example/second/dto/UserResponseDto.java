package com.example.second.dto;


import com.example.second.models.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDto{

    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String mobile;
    private String email;
    private String cAddress;
    private String pAddress;
    private Date createTime;
    private Date updateTime;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.mobile = user.getMobileNumber();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.cAddress = user.getCAddress();
        this.pAddress = user.getPAddress();
        this.createTime = user.getCreateTime();
        this.updateTime = user.getUpdateTime();
    }

}