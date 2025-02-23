package com.myApp.TazkartiApp.DTO;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateDTO extends UserDTO {
    private String password;
}

