package site.ancronik.movie.trailer.searcher.info.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoResponse {

    private String infoMessage;

    private String applicationVersion;
}
