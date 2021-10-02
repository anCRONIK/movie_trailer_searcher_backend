package site.ancronik.movie.trailer.searcher.core.exception;

public class DataServiceException extends RuntimeException {

    private static final String REST_SERVICE_EXCEPTION_MESSAGE = "REST service [method %s, resource %s] returned error.";

    public DataServiceException(String method, String resource) {
        super(String.format(REST_SERVICE_EXCEPTION_MESSAGE, method, resource));
    }

}
