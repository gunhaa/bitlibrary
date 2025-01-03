package bitcopark.library.interceptor;


import bitcopark.library.entity.Board.Category;
import bitcopark.library.repository.Board.CategoryRepository;
import bitcopark.library.service.Board.CategoryService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
public class CategoryInterceptor implements HandlerInterceptor {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ServletContext app = request.getServletContext();

        if(app.getAttribute("category")==null){
            List<Category> categoryList = categoryRepository.selectAll();
            app.setAttribute("category", categoryList);
        }
        System.out.println("================");
        System.out.println("interceptor USED");
        System.out.println("================");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
