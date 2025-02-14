package bitcopark.library.controller.user;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.controller.util.ControllerUtils;
import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.dto.BoardUpdateRequestDTO;
import bitcopark.library.dto.CommentRequestDTO;
import bitcopark.library.entity.board.*;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.service.board.BoardService;
import bitcopark.library.service.board.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.setCategoryAndRoute;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    public static final String IMG_UPLOAD_PATH = "/Users/baejihwan/uploads/";

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
            , Pageable pageable){

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        Category category = categoryService.getCategoryEngName(catLevel2);

        Page<Board> boardPage = boardService.selectBoardList(category.getId(), pageable);

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
            , BoardRequestDTO boardRequestDTO
            , @RequestParam(name = "files", required = false) MultipartFile[] files
            , @RequestAttribute(value="loginMember", required = false) LoginMemberDTO loginMember) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        // logic
        Category category = categoryService.getCategoryEngName(catLevel2);
        model.addAttribute("cateCode", category.getId());
        model.addAttribute("cateEngName", category.getCategoryEngName());

        Board board = boardService.writePost(loginMember, boardRequestDTO, category);

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

        model.addAttribute("board", board);

        return "redirect:/user/" + catLevel2 + "/" + board.getId();
    }

    @GetMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/{boardId}")
    public String boardDetail(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @PathVariable Long boardId) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        Category category = categoryService.getCategoryEngName(catLevel2);
        model.addAttribute("cateCode", category.getId());
        model.addAttribute("cateEngName", category.getCategoryEngName());

        Board board = boardService.selectBoard(boardId).get();
        model.addAttribute("board", board);

        if( !board.getReplyList().isEmpty() ){
            List<Reply> replyList = boardService.selectReplyList(board);
            model.addAttribute("replyList", replyList);
        }

        return "user/boardDetail";
    }


    @PostMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/delete")
    @ResponseBody
    public ResponseEntity<String> deleteBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @RequestBody Long boardId
            , @RequestAttribute(value="loginMember", required = false) LoginMemberDTO loginMember) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        Board board = boardService.selectBoard(boardId).get();

        boardService.deletePost(loginMember, board);

        return ResponseEntity.ok("/user/"+ catLevel2);
    }

    @PostMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/comment")
    @ResponseBody
    public ResponseEntity<CommentRequestDTO> insertComment(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @RequestBody CommentRequestDTO commentRequestDTO
            , @RequestAttribute(value="loginMember", required = false) LoginMemberDTO loginMember) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        boardService.writeComment(loginMember, commentRequestDTO);

        return ResponseEntity.ok(commentRequestDTO);
    }

    @DeleteMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/comment")
    @ResponseBody
    public ResponseEntity<String> deleteComment(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @RequestBody Long commentId
            , @RequestAttribute(value="loginMember", required = false) LoginMemberDTO loginMember) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        ReplyDelFlag delFlag = boardService.deleteComment(loginMember, commentId);
        if( delFlag == ReplyDelFlag.Y ) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.ok("failed");
        }
    }

    @GetMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/update/{boardId}")
    public String updateBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , @PathVariable Long boardId)  {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        Category category = categoryService.getCategoryEngName(catLevel2);
        model.addAttribute("cateEngName", category.getCategoryEngName());

        Board board = boardService.selectBoard(boardId).get();
        model.addAttribute("board", board);

        return "user/boardUpdate";
    }

    @PostMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}/update")
    public String updateBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , BoardUpdateRequestDTO boardUpdateRequestDTO
            , @RequestAttribute(value="loginMember", required = false) LoginMemberDTO loginMember) {

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);
        
        Board board = boardService.updatePost(loginMember, boardUpdateRequestDTO);

        model.addAttribute("board", board);

        return "redirect:/user/" + catLevel2 + "/" +board.getId();
    }
}
