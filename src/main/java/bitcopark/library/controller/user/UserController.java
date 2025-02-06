package bitcopark.library.controller.user;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.controller.util.ControllerUtils;
import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.dto.LoginResponseDTO;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardImg;
import bitcopark.library.entity.board.Category;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.service.Board.BoardService;
import bitcopark.library.service.Board.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.setCategoryAndRoute;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    private final String IMG_UPLOAD_PATH = "static/images/board";

    @GetMapping(value="{catLevel1:user}/{catLevel2:faq}")
    public String user(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            ,@PathVariable(name = "catLevel1") String catLevel1
            ,@PathVariable(name = "catLevel2") String catLevel2){

        ControllerUtils.CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        return categoryRouterResult.router().routing(categoryRouterResult.categoryLevel3());
    }

    @GetMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}")
    public String board(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , Pageable pageable
            , @RequestAttribute(value="loginMember", required = false) LoginMemberDTO loginMember){

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        // logic
        Category category = categoryService.getCategoryEngName(catLevel2);
        // pagination
        Page<Board> boardPage = boardService.selectBoardList(category.getId(), pageable);

        System.out.println("loginMember = " + loginMember);

        // model addAttribute
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("cateCode", category.getId());
        model.addAttribute("cateName", category.getCategoryName());
        model.addAttribute("cateEngName", category.getCategoryEngName());

        return "user/boardList";
    }

    @GetMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/insert")
    public String selectPlatform(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        Category category = categoryService.getCategoryEngName(catLevel2);

        model.addAttribute("cateEngName", category.getCategoryEngName());

        if( category.getId() == 16 ){
            return "user/qnaWrite";
        }

        return "user/boardWrite";
    }

    @PostMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/insert")
    public String insertBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @RequestParam(name = "files", required = false) MultipartFile[] files
            , BoardRequestDTO boardRequestDTO
            , @RequestAttribute(value="loginMember", required = false) LoginMemberDTO loginMember) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        System.out.println("inin");
        // logic
        Category category = categoryService.getCategoryEngName(catLevel2);
        model.addAttribute("cateCode", category.getId());

        Board board = boardService.writePost(loginMember, boardRequestDTO, category);

        model.addAttribute("board", board);

        if (files != null && files.length > 0) {
            try{
                String path = new ClassPathResource(IMG_UPLOAD_PATH).getFile().getAbsolutePath();
                List<BoardImg> boardImgList = new ArrayList<>();

                for( int i = 0; i < files.length; i++ ) {
                    if (!files[i].isEmpty()) {
                        BoardImg boardImg = boardService.insertBoardImg(board, files[i].getOriginalFilename(), i);
                        files[i].transferTo(new File(path + boardImg.getRenameImg()));
                        boardImgList.add(boardImg);
                    }
                }

                model.addAttribute("boardImgList", boardImgList);
            }catch(IOException e){

            }
        }

        return "user/boardDetail";
    }
}
