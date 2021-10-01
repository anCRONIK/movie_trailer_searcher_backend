package site.ancronik.movie.trailer.searcher.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {

    private String path;

    private LocalDateTime timestamp;

    private String errorMsg;

}
