package msa.study.product.infrastructure.exception.status;

import msa.study.product.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RegisterFailException extends GlobalException {

    public RegisterFailException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public RegisterFailException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public RegisterFailException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
