package bitcopark.library.temp;

import bitcopark.library.entity.Book.BookState;
import bitcopark.library.entity.Book.BookSupple;
import bitcopark.library.repository.Book.BookRepository;
import bitcopark.library.service.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class tempBookGenerate implements ApplicationRunner {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(bookRepository.selectAll().isEmpty()) {

            bookService.registerNewBook("박은몽 글", "명품 인생을 살아라", "대교베텔스만", "2008", "9788957592472", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762800%3Ftimestamp%3D20220904155651" , BookState.P, BookSupple.N );
            bookService.registerNewBook("김구 저", "백범일지", "돌베개", "2008", "9788971992258", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);
            bookService.registerNewBook("댄 브라운 지음", "디셉션 포인트", "대교베텔스만", "2006", "9788957591574", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762541%3Ftimestamp%3D20220825213715", BookState.I, BookSupple.N);
            bookService.registerNewBook("김상현 지음", "정약용 살인사건", "중앙M&B(랜덤하우스중앙)", "2006", "9788959864706", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F814870%3Ftimestamp%3D20211204201827", BookState.P, BookSupple.N);
            bookService.registerNewBook("황농문 지음", "몰입", "랜덤하우스코리아", "2007", "9788925514826", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F411705%3Ftimestamp%3D20240430112710", BookState.I, BookSupple.N);
            bookService.registerNewBook("홍사중 지음", "나의 논어", "이다미디어", "2006", "9791158741426", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F6015264%3Ftimestamp%3D20240723154038", BookState.I, BookSupple.N);
            bookService.registerNewBook("베르나르 베르베르 지음", "파피용", "열린책들", "2007", "9788932907741", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F506439%3Ftimestamp%3D20231201015513", BookState.I, BookSupple.N);
            bookService.registerNewBook("박범신", "킬리만자로의눈꽃", "해냄", "1997", "9788998493585", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F4402846%3Ftimestamp%3D20190228161200", BookState.P, BookSupple.N);
            bookService.registerNewBook("김진명 저", "한반도", "해냄", "1999", "9788950952518", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F625662%3Ftimestamp%3D20220420212720", BookState.P, BookSupple.N);
            bookService.registerNewBook("고찬유 지음", "(거룩한 땅에,) 슈끄리아", "유스북", "2006", "9788991430143", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1349582%3Ftimestamp%3D20220105174508", BookState.P, BookSupple.N);
            bookService.registerNewBook("기욤 뮈소 지음", "구해줘", "밝은세상", "2007", "9788984370753", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1187452%3Ftimestamp%3D20240915112246", BookState.P, BookSupple.N);
            bookService.registerNewBook("로스 엘리자베스 퀴블러   ; 케슬러 데이비드 공저", "인생수업", "이레", "2007", "9788957090817", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F751351%3Ftimestamp%3D20240706112942", BookState.I, BookSupple.N);
            bookService.registerNewBook("법정 말씀", "산에는 꽃이 피네", "동쪽나라", "1998", "9788995904992", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1453467%3Ftimestamp%3D20221025122431", BookState.P, BookSupple.N);
            bookService.registerNewBook("코맥 매카시 지음", "로드", "문학동네", "2008", "9788954605908", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F690185%3Ftimestamp%3D20240906112452", BookState.P, BookSupple.N);
            bookService.registerNewBook("최일도", "행복하소서", "위즈덤하우스", "2008", "9788960861312", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F840862%3Ftimestamp%3D20220917071152", BookState.I, BookSupple.N);
            bookService.registerNewBook("로버트 제임스 윌러 저", "메디슨 카운티의 다리", "시공사", "1994", "9791193783405", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F6626858%3Ftimestamp%3D20240621155506", BookState.P, BookSupple.N);
            bookService.registerNewBook("와다 히데키 지음", "남자는 왜? 여자는 왜?", "예문", "2002", "9791165042615", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5910968%3Ftimestamp%3D20240522150401", BookState.P, BookSupple.N);

        }


    }
}
