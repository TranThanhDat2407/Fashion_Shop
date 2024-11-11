package com.example.Fashion_Shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    @NotBlank(message = "Phone number is required")
    private String phone;

    @JsonProperty("email")
    @NotBlank(message = "Phone number is required")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;
}
