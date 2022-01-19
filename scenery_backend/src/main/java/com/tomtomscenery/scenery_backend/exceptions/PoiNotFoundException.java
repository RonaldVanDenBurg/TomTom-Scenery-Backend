package com.tomtomscenery.scenery_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PoiNotFoundException extends RuntimeException {
     private static final long serialVersionUID = 1L;

     public PoiNotFoundException(String message)
     {
          super(message);
     }

}
