package site.ancronik.movie.trailer.searcher.core.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class MethodExecutionTimer {

    private final MeterRegistry meterRegistry;

    @Autowired
    public MethodExecutionTimer(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Pointcut("execution(* site.ancronik.movie.trailer.searcher.api.domain.services.MovieTrailerSearchServiceImpl.*(..))")
    public void searchMethods() {
    }

    @Around("searchMethods()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.debug("Class: {}, method: {}, executionTime(ms): {}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), elapsedTime);

        meterRegistry.timer(String.format("execution_time_%s_%s", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName())).record(elapsedTime, TimeUnit.MILLISECONDS);
        return output;
    }

}