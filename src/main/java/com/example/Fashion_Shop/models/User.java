package com.example.Fashion_Shop.models;

import java.util.Date;

public class User extends BaseEntity{
    private int id;
    private String name;
    private String email;
    private String phone;
    private int role_id;
    private String password;
    private String one_time_password;
    private Date otp_requested_time;
}
