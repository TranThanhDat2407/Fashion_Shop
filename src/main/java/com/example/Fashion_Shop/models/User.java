package com.example.Fashion_Shop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Integer role_id;
    private String password;
    private String one_time_password;
    private Date otp_requested_time;
}
