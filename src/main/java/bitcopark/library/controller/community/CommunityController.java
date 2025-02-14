package bitcopark.library.controller.community;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.controller.util.ControllerUtils;
import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.dto.ClassScheduleRequestDTO;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.BoardImg;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.clazz.ClassSchedule;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.service.board.BoardService;
import bitcopark.library.service.board.CategoryService;
import bitcopark.library.service.clazz.ClassScheduleService;
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
public class CommunityController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final ClassScheduleService classScheduleService;

    private final String IMG_UPLOAD_PATH = "/Users/baejihwan/uploads/";

    @GetMapping(value="{catLevel1:community}/{catLevel2:reading-room|seminar-room}")
    public String community(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            ,@PathVariable(name = "catLevel1") String catLevel1
            ,@PathVariable(name = "catLevel2") String catLevel2) {

        ControllerUtils.CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        return categoryRouterResult.router().routing(categoryRouterResult.categoryLevel3());
    }

    @GetMapping(value="{catLevel1:community}/{catLevel2:edu-culture-program}")
    public String eduCommunity(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , Pageable pageable) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        Category category = categoryService.getCategoryEngName(catLevel2);

        Page<Board> boardPage = boardService.selectBoardList(category.getId(), pageable);

        model.addAttribute("boardPage", boardPage);

        return "community/eduCultureProgram";
    }

    @GetMapping(value="{catLevel1:community}/{catLevel2:edu-culture-program}/insert")
    public String selectPlatform(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        return "community/eduCultureProgramWrite";
    }

    @PostMapping(value="{catLevel1:community}/{catLevel2:edu-culture-program}/insert")
    public String insertBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @RequestParam(name = "files", required = false) MultipartFile[] files
            , BoardRequestDTO boardRequestDTO
            , ClassScheduleRequestDTO classRequestDTO
            , @RequestParam(value="loginMember", required = false)LoginMemberDTO loginMemberDTO) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        Category category = categoryService.getCategoryEngName(catLevel2);

        Board board = boardService.writePost(loginMemberDTO, boardRequestDTO, category);
        model.addAttribute("board", board);

        if (files != null && files.length > 0)  {
            try{
                for( int i = 0; i < files.length; i++ ) {
                    if (!files[i].isEmpty()) {
                        BoardImg boardImg = boardService.insertBoardImg(board, files[i].getOriginalFilename(), IMG_UPLOAD_PATH, i);
                        files[i].transferTo(new File(IMG_UPLOAD_PATH + boardImg.getRenameImg()));

                        board.getBoardImgList().add(boardImg);
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        ClassSchedule classSchedule = classScheduleService.registClassSchedule(board, classRequestDTO);
        model.addAttribute("classSchedule", classSchedule);

        return "community/eduCultureProgramDetail";
    }

    @PostMapping(value="{catLevel1:community}/{catLevel2:edu-culture-program}/update")
    public String updateBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @RequestParam(name = "files", required = false) MultipartFile[] files
            , BoardRequestDTO boardRequestDTO
            , ClassScheduleRequestDTO classRequestDTO
            , @RequestParam(value="loginMember", required = false)LoginMemberDTO loginMemberDTO) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);



        return "community/eduCultureProgramDetail";
    }
}
