package study.fisco.demo.handleException;

public class ServiceException extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }
}
