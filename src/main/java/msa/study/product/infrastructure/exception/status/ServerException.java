package msa.study.product.infrastructure.exception.status;

import msa.study.product.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class ServerException extends GlobalException {

    public ServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ServerException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public ServerException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
