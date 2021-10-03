package site.ancronik.movie.trailer.searcher.security.filter;

import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class IdFilter implements Filter { //TODO USE ME in Sprign security

    private static final String KEY_UUID = "uuid";
    private static final String KEY_USER = "user";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put(KEY_UUID, UUID.randomUUID().toString());
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            if (Objects.nonNull(httpRequest.getUserPrincipal())) {
                MDC.put(KEY_USER, httpRequest.getUserPrincipal().getName());
            }
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(KEY_UUID);
            MDC.remove(KEY_USER);
        }
    }

}