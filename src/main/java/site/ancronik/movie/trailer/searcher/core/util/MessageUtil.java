package site.ancronik.movie.trailer.searcher.core.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Objects;

public final class MessageUtil {

    private MessageUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getResourceMessage(String key, MessageSource messageSource, String... args) {
        if (Objects.isNull(args)) {
            return getResourceMessage(key, messageSource);
        }
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    public static String getResourceMessage(String key, MessageSource messageSource) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

}