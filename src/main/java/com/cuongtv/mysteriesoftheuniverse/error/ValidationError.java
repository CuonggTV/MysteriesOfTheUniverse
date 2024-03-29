package com.cuongtv.mysteriesoftheuniverse.error;

public class ValidationError {
    private String name;
    private String message;
    private String value;

    public ValidationError(String name, String message, String value) {
        this.name = name;
        this.message = message;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
