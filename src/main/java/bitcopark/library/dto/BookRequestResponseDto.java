package bitcopark.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequestResponseDto {

    private boolean success;
    private String message;

}
