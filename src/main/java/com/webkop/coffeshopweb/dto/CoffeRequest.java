package com.webkop.coffeshopweb.dto;

import com.webkop.coffeshopweb.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoffeRequest {

    private String name;
    private Double price;
    private Integer stock;
    Category category;

}
