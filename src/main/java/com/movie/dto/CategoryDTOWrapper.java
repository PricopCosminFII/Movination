package com.movie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTOWrapper {
    private List<String> chosenCategories;
}
