package org.livevox.phonebook.application.entrypoints.web;

import lombok.extern.slf4j.Slf4j;
import org.livevox.phonebook.application.entrypoints.web.model.CustomErrorResponse;
import org.livevox.phonebook.shared.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

  @ResponseBody
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<CustomErrorResponse> customException(CustomException ex) {
    log.error("EXCEPTION - INTERNAL_SERVER_ERROR: ", ex);
    return ResponseEntity
            .status(ex.getStatus())
            .body(CustomErrorResponse.builder()
                    .code(ex.getReason())
                    .message(ex.getMessage())
                    .build());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ServerWebInputException.class, ValidationException.class})
  public CustomErrorResponse inputException(Exception ex) {
    log.error("ERROR - BAD REQUEST: ", ex);
    return CustomErrorResponse.builder()
            .code("LVOX-400")
            .message("Error in request fields: %s".formatted(ex.getMessage()))
            .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public CustomErrorResponse inputException(MethodArgumentNotValidException ex) {
    log.error("ERROR - BAD REQUEST: ", ex);
    BindingResult result = ex.getBindingResult();
    return CustomErrorResponse.builder()
            .code("LVOX-400")
            .message("Error in the request fields")
            .details(processFieldErrors(result.getFieldErrors()))
            .build();
  }

  private List<String> processFieldErrors(List<FieldError> fieldErrors) {
    List<String> error = new ArrayList<>();
    for (FieldError fieldError: fieldErrors) {
      error.add(fieldError.getField().concat(" - ").concat(fieldError.getDefaultMessage()));
    }
    return error;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public CustomErrorResponse exception(Exception ex) {
    log.error("EXCEPTION - INTERNAL_SERVER_ERROR: ", ex);
    return CustomErrorResponse.builder()
            .code("LVOX-501")
            .message("Error desconocido")
            .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  public CustomErrorResponse exception(Throwable ex) {
    log.error("THROWABLE - INTERNAL_SERVER_ERROR: ", ex);
    return CustomErrorResponse.builder()
            .code("LVOX-502")
            .message("Error desconocido")
            .build();
  }
}
