package org.livevox.phonebook.shared;

import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class CustomException extends NestedRuntimeException {

    private static final long serialVersionUID = 1l;

    private final String reason;

    private final Date timeStamp;

    private final HttpStatus status;

    public CustomException(String msg, String reason, HttpStatus status) {
        super(msg);
        this.reason = reason;
        this.timeStamp = new Date();
        this.status = status;
    }
}
