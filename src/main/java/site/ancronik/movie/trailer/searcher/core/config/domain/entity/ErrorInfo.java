package site.ancronik.movie.trailer.searcher.core.config.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorInfo {

    private String path;

    private LocalDateTime timestamp;

    private String errorMsg;

}
