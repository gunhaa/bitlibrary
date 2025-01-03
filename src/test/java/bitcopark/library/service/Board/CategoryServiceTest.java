package bitcopark.library.service.Board;

import bitcopark.library.entity.Board.Category;
import bitcopark.library.exception.CategoryNotFoundException;
import bitcopark.library.repository.Board.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;


    @Test
    public void 카테고리_인터셉터_SELECTALL(){
        //given
        //tempCategoryGenerate 에서 실행
        
        //when
        List<Category> categories = categoryRepository.selectAll();

//        for (Category category : categories) {
//            System.out.println("category.getId() = " + category.getId());
//            System.out.println("category.getCategoryName() = " + category.getCategoryName());
//            System.out.println("category.getParentCategory() = " + category.getParentCategory());
//        }

        //then                                                                  // given의 카테고리 총 갯수
        org.assertj.core.api.Assertions.assertThat(categories.size()).isEqualTo(49);
    }

    @Test
    public void 부모가_있는_카테고리_생성(){

        //given
        Category category1 = categoryService.createNewCategory("역사");

        //when
        Category category2 = categoryService.createNewCategoryWithParentCategory("국사", category1);

        //then
        Category category3 = categoryRepository.findByCategoryName("국사").orElseThrow(()-> new IllegalArgumentException("오류 발생"));
        assertThat(category2).isEqualTo(category3);

    }


    @Test
    public void 관리자_카테고리생성(){

        //given
        Category category = categoryService.createNewCategory("역사");

        //when
        Category findCategory = categoryRepository.findByCategoryName("역사").get();

        //then
        assertThat(category).isEqualTo(findCategory);
    }

    @Test
    public void 관리자_중복카테고리생성_오류발생(){

        //given
        Category category = categoryService.createNewCategory("역사");

        //when , then
        assertThrows(CategoryNotFoundException.class, ()->{
            categoryService.createNewCategory("역사");
        });
    }

    @Test
    public void 카테고리_오브젝트_생성(){
        //given
        Category category = categoryService.createNewCategory("역사");
        
        //when
        Category findCategory = categoryService.createCategoryObject("역사");

        //then
        assertThat(category).isEqualTo(findCategory);
    }

    @Test
    public void 카테고리_오브젝트_생성실패_존재하지않는_카테고리(){


        //given , when , then

        Assertions.assertThrows(CategoryNotFoundException.class, ()->{
            Category findCategory = categoryService.createCategoryObject("역사");
        });

    }


}