package com.module.service;

import com.module.dto.HabitCategoryDTO;
import com.module.dto.HabitCategoryResponse;

public interface HabitCategoryService {
    HabitCategoryDTO createHabitCategory(HabitCategoryDTO habitCategoryDTO);

    HabitCategoryResponse getAllHabitCategories(int pageNo, int pageSize, String sortBy, String sortDir);

    HabitCategoryDTO getHabitCategoryByID(long id);

    HabitCategoryDTO updateHabitCategory(HabitCategoryDTO habitCategoryDTO, long id);

    void deleteHabitCategoryByID(long id);
}
