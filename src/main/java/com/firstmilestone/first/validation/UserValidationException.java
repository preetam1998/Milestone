package com.firstmilestone.first.validation;

public class UserValidationException extends RuntimeException {

    private String property;
    private String message;

    public UserValidationException(String property, String message){
        super("Error in field: " +property+ ", "+ message);
        this.property = property;
        this.message = message;
    }

    public String getProperty() {
        return property;
    }

    public String getMessage() {
        return message;
    }
}
