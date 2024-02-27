package org.penzgtu.Application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserWallet {
    private int user_id;
    private double balance;
}
