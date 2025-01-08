package bitcopark.library.controller.util;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.exception.CategoryNotFoundException;

import java.util.List;

public class ControllerUtils {

    public static CategoryDTO getCategoryByCategoryEngName(List<CategoryDTO> categoryDTOList, String catLevel) {
        return categoryDTOList.stream()
                .filter(category -> category.getCategoryEngName().equals(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }

    public static CategoryDTO getCategoryByCategoryId(List<CategoryDTO> categoryDTOList, String catLevel){

        if (catLevel == null) {
            return null;
        }

        return categoryDTOList.stream()
                .filter(categoryDTO -> categoryDTO.getId().intValue() == Integer.parseInt(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }

}
