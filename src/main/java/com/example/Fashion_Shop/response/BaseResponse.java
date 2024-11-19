package com.example.Fashion_Shop.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    @JsonProperty("create_at")
    private LocalDateTime createAt;

    @JsonProperty("update_at")
    private LocalDateTime updateAt;
}
