package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class LoginDto implements DtoBase{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
//        USERNAME ERRORS
        if (username.length()==0 || password.length()==0){
            errors.add(new ValidationError("username&password","All fields must be filled!",username));
        }
        else{
            errors.add(new ValidationError("username&password","Account does not exist!",username));
        }
        return errors;
    }
}
