package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.MatchUtils;

import java.util.ArrayList;
import java.util.List;

public class RegisterDto implements DtoBase {
    private String username;
    private String password;
    private String confirmPassword;
    private String name;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth.trim();
    }
    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
//        USERNAME ERRORS
        if (username.length()==0){
            errors.add(new ValidationError("username","Username must not empty!",username));
        }
        else if(username.length()>255){
            errors.add(new ValidationError("username","Username must not pass 256 words!",username));
        }
        else if (!MatchUtils.matchUsername(username)){
            errors.add(new ValidationError("username","Username must not have space!",username));
        }
        else if (AccountDao.isUsernameExisted(username,0)){
            errors.add(new ValidationError("username","Username has existed!",username));
        }

//        PASSWORD ERRORS
        if (password.length()==0){
            errors.add(new ValidationError("password","Password must not empty!",password));
        }
        else if(password.length()>255){
            errors.add(new ValidationError("password","Password must not pass 256 words!",password));
        }
//        CONFIRM PASSWORD ERRORS
        if (confirmPassword.length()==0){
            errors.add(new ValidationError("confirmPassword","Confirm password must not empty!",confirmPassword));
        }else if(!confirmPassword.equals(password)){
            errors.add(new ValidationError("confirmPassword","Confirm password must equal to password!",confirmPassword));
        }
//        NAME ERRORS
        if (name.length()==0){
            errors.add(new ValidationError("name","Name must not empty!",name));
        }
        else if(name.length()>255){
            errors.add(new ValidationError("name","Name must not pass 256 words!",name));
        }
//        EMAIL ERRORS
        if (email.length()==0){
            errors.add(new ValidationError("email","Email must not empty!",email));
        }
        else if(email.length()>255){
            errors.add(new ValidationError("email","Email must not pass 256 words!",email));
        }
        else if(!MatchUtils.matchEmail(email)){
            errors.add(new ValidationError("email","Wrong email format!",email));
        }
        else if (AccountDao.isEmailExisted(email,0)){
            errors.add(new ValidationError("email","Email has existed!",email));
        }
//        PHONE NUMBER ERRORS
        if (phoneNumber.length()==0){
            errors.add(new ValidationError("phoneNumber","Phone number must not empty!",phoneNumber));
        }
        else if (!MatchUtils.matchPhoneNumber(phoneNumber)){
            errors.add(new ValidationError("phoneNumber","Phone number must have only 10 numbers!",phoneNumber));
        }
//        DATE OF BIRTH ERRORS
        if (dateOfBirth.length()==0){
            errors.add(new ValidationError("dateOfBirth","Date of birth number must not empty!",dateOfBirth));
        }
        else if (!MatchUtils.matchBirthDay(dateOfBirth)){
            errors.add(new ValidationError("dateOfBirth","Date of birth must not pass current day!",dateOfBirth));
        }
        return errors;
    }
}
