package com.sid.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class defines the error response for rest exceptions
 */
@XmlRootElement(name = "error")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private String details;
}
