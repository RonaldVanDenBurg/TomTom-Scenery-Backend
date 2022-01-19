package com.tomtomscenery.scenery_backend.exceptions;

// This is the class where all the exceptions are handled.

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice // Annotation enables this class to handle exceptions globally.
public class GlobalExceptionHandler {


     @ExceptionHandler(PoiNotFoundException.class)
     public ResponseEntity<?> handleResourceNotFoundException(PoiNotFoundException exception, WebRequest webRequest)
     {
          ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
          return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
     }

     @ExceptionHandler(Exception.class)
     public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest webRequest)
     {
          ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
          return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
     }
}
