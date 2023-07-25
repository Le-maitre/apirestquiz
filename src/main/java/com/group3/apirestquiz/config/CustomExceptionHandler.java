package com.group3.apirestquiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = getErrorMessages(ex.getBindingResult());

        // Créer le corps de la réponse avec le format souhaité
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseBody.put("message", errorMessages);

        // Renvoyer la réponse personnalisée avec le statut 400 (Bad Request)
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorMessages(BindingResult bindingResult) {
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String errorMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            errorMessages.add(errorMessage);
        }

        return errorMessages;
    }
}
