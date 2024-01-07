package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class MessageDto implements DtoBase{
    private String message;

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message.trim();
    }

    @Override
    public List<ValidationError> validate() {
        System.out.println("Message: "+message);
        List<ValidationError> errors = new ArrayList<>();
        if(message == null || message.length() == 0){
            errors.add(new ValidationError("message","Message must not be empty!",message));
        }
        else if (message.length() >1000){
            errors.add(new ValidationError("message","Message must not pass 1000 letters!",message));
        }
        return errors;
    }
}
