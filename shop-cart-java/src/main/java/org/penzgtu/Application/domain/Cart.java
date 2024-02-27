package org.penzgtu.Application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private int session_id;
    private int product_id;
    private int quantity;
}

