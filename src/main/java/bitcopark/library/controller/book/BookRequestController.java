package bitcopark.library.controller.book;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.dto.BookApproveDto;
import bitcopark.library.dto.BookDeleteDto;
import bitcopark.library.dto.BookRequestCondition;
import bitcopark.library.dto.BookRequestResponseDto;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.dto.BookRequestDetailDto;
import bitcopark.library.dto.BookRequestPageDto;
import bitcopark.library.service.book.BookRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.setCategoryInModel;

@Controller
@RequiredArgsConstructor
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/list")
    public String bookRequestBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                                   @PathVariable(name = "catLevel1") String catLevel1,
                                   @PathVariable(name = "catLevel2") String catLevel2,
                                   @PageableDefault(page = 0, size = 10) Pageable pageable) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        Page<BookRequestPageDto> bookRequestPageDto = bookRequestService.getBookRequestPage(pageable);
        model.addAttribute("page", bookRequestPageDto);

        return "search/bookRequestBoard";
    }

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/apply")
    public String bookReqApply(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                               @PathVariable(name = "catLevel1") String catLevel1,
                               @PathVariable(name = "catLevel2") String catLevel2,
                               @RequestAttribute LoginMemberDTO loginMember) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        model.addAttribute("loginMember", loginMember);
        return "search/bookRequestForm";
    }



    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/list/{isbn}")
    public String bookRequestBoardDetail(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                                         @PathVariable(name = "catLevel1") String catLevel1,
                                         @PathVariable(name = "catLevel2") String catLevel2,
                                         @PathVariable String isbn,
                                         @RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMember) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        BookRequestDetailDto bookRequestDetailDto = bookRequestService.getBookRequestDetailsByIsbn(isbn);
        if(loginMember != null) {
            bookRequestDetailDto.setLoginMemberEmail(loginMember.getEmail());
        }
        model.addAttribute("bookRequestDetail" , bookRequestDetailDto);
        return "search/bookRequestDetail";
    }

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/list/{isbn}/update")
    public String bookRequestBoardUpdate(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                                         @PathVariable(name = "catLevel1") String catLevel1,
                                         @PathVariable(name = "catLevel2") String catLevel2,
                                         @PathVariable String isbn,
                                         @RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMember) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        BookRequestDetailDto bookRequestDetailDto = bookRequestService.getBookRequestDetailsByIsbn(isbn);

        if(loginMember != null) {
            bookRequestDetailDto.setLoginMemberEmail(loginMember.getEmail());
        }

        model.addAttribute("bookRequestDetail" , bookRequestDetailDto);
        return "search/bookRequestUpdate";
    }

    @PostMapping("/{catLevel1:search}/book-req/apply/v1")
    @ResponseBody
    public BookRequestResponseDto bookRequestApply(@RequestBody BookRequestCondition applyCondition, @RequestAttribute("loginMember")LoginMemberDTO loginMember){
        applyCondition.setEmail(loginMember.getEmail());
        return bookRequestService.registerBookRequest(applyCondition);
    }

    @DeleteMapping("/{catLevel1:search}/book-req/delete/v1")
    @ResponseBody
    public ResponseEntity<?> bookRequestDelete(@RequestBody BookDeleteDto bookDeleteDto, @RequestAttribute("loginMember")LoginMemberDTO loginMember){
        return bookRequestService.deleteBookRequest(bookDeleteDto, loginMember);
    }

    @PostMapping("/{catLevel1:search}/book-req/approve/toggle/v1")
    @ResponseBody
    public ResponseEntity<?> bookRequestApproveStatusChange(@RequestBody BookApproveDto bookApproveDto, @RequestAttribute("loginMember")LoginMemberDTO loginMember){
        return bookRequestService.approveStatusChangeBookRequest(bookApproveDto, loginMember);
    }

    @PostMapping("/{catLevel1:search}/book-req/update/v1")
    @ResponseBody
    public ResponseEntity<?> bookRequestUpdate(@RequestBody BookRequestCondition updateCondition, @RequestAttribute("loginMember")LoginMemberDTO loginMember){
        return bookRequestService.updateBookRequest(updateCondition, loginMember);
    }
}
