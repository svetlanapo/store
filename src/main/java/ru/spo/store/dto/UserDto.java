package ru.spo.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String matchingPassword;
    private String email;

}
