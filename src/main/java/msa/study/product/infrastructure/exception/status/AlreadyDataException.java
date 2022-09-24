package msa.study.product.infrastructure.exception.status;

import msa.study.product.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class AlreadyDataException extends GlobalException {

    public AlreadyDataException() {
        super(HttpStatus.CONFLICT);
    }

    public AlreadyDataException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

    public AlreadyDataException(String reason, Throwable cause) {
        super(HttpStatus.CONFLICT, reason, cause);
    }
}
