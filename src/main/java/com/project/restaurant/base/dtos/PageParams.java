package com.project.restaurant.base.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sort = "name";
    private String sortDirection = "ASC";

}
