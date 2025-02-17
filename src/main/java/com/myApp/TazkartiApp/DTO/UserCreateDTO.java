package com.myApp.TazkartiApp.DTO;

import com.myApp.TazkartiApp.Enums.UserRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
  //  private UserRole role;
}

