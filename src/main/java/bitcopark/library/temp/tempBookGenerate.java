package bitcopark.library.temp;

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

        if(bookRepository.selectAll() < 100) {



        }


    }
}
