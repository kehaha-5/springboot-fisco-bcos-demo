package study.fisco.demo.handleException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import study.fisco.demo.result.Result;

@RestController
@ControllerAdvice
public class HandleException {

    @ExceptionHandler(value = ServiceException.class)
    public Result exceptionHandle(ServiceException e){
        return Result.fail(e.getMessage());
    }
}
