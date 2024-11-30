package com.example.Fashion_Shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @JsonProperty("name")
    @NotBlank(message = "Vui lòng nhập tên.")
    private String name;

    @JsonProperty("phone")
    @NotBlank(message = "Vui lòng nhập số điện thoại.")
    @Pattern(regexp =
            "^0(3[2-9]|5[2-9]|7[0|6-9]|8[1-9]|9[0-9])\\d{7}$"
            ,message = "Vui lòng nhập đúng định dạng số điện thoại.")
    private String phone;

    @JsonProperty("email")
    @NotBlank(message = "Vui lòng nhập email.")
    @Pattern(regexp =
            "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$"
            ,message = "Vui lòng nhập email đúng định dạng.")
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$",message = "Mật khẩu phải có 6 kí tự bao gồm kí tự hoa và số.")
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "Vui lòng xác nhận mật khẩu.")
    private String retypePassword;

    @JsonProperty("facebook_account_id")
    private String facebookAccountId;

    @JsonProperty("google_account_id")
    private String googleAccountId;

    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;
}
