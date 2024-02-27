package org.penzgtu.Application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor

public class Session {
    private int user_id;
    private int is_active;
    private LocalDate date;
}
