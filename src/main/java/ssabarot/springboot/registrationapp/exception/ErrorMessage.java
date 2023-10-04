package ssabarot.springboot.registrationapp.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private Map<String, String> errors;
}
