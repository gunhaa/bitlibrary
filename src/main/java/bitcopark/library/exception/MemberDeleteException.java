package bitcopark.library.exception;

import org.springframework.security.core.AuthenticationException;

public class MemberDeleteException extends AuthenticationException {

    public MemberDeleteException(String msg) {
        super(msg);
    }
}
