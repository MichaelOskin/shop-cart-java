package org.penzgtu.Application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class LoginPassword {
    private int user_id;
    private String login;
    private String password;
}
