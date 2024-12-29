package gabrieldev.com.supermercado_estoque.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public BusinessException() {
    	super("It is not allowed to persist a null object!");
    }
    public BusinessException(String message) {
        super(message);
    }
}