package site.ancronik.movie.trailer.searcher.core;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T, U> {

    U map(T from);

    default List<U> mapToList(Collection<T> from) {
        return from.stream().map(this::map).collect(Collectors.toUnmodifiableList());
    }

}
