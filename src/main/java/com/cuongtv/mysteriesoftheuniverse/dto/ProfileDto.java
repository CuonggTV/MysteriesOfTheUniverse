package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.dao.AccountDao;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;
import com.cuongtv.mysteriesoftheuniverse.utils.MatchUtils;

import java.util.ArrayList;
import java.util.List;

public class ProfileDto implements DtoBase{
    private int id;
    private String username;
    private String currentUsername;
    private String name;
    private String email;
    private String currentEmail;
    private String phoneNumber;
    private String dateOfBirth;
    private String dateCreated;
    private String introduction;
    private String interest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
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

        else{
            try{
                if (AccountDao.isUsernameExisted(username,id) && !username.equals(currentUsername)){
                    errors.add(new ValidationError("username","Username has existed!",username));
                }
            }catch (Exception e){
                System.out.println("Cannot check username!");
                System.out.println(" -- "+e);
            }
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
        else {
            try{
                if (AccountDao.isEmailExisted(email,id) && !email.equals(currentEmail)){
                    errors.add(new ValidationError("email","Email has existed!",email));
                }
            }catch (Exception e){
                System.out.println("Cannot check email!");
                System.out.println(" -- "+e);
            }
        }
//        PHONE NUMBER ERRORS
        if (phoneNumber.length()==0){
            errors.add(new ValidationError("phoneNumber","Phone number must not be empty!",phoneNumber));
        }
        else if (!MatchUtils.matchPhoneNumber(phoneNumber)){
            errors.add(new ValidationError("phoneNumber","Phone number must have only 10 numbers!",phoneNumber));
        }
//        DATE OF BIRTH ERRORS
        if (dateOfBirth.length()==0){
            errors.add(new ValidationError("dateOfBirth","Date of birth must not empty!",dateOfBirth));
        }
        else if (!MatchUtils.matchBirthDay(dateOfBirth)){
            errors.add(new ValidationError("dateOfBirth","Date of birth must not pass current day!",dateOfBirth));
        }

//        INTRODUCTION ERRORS
        if (introduction.length()>1000) {
            errors.add(new ValidationError("introduction","Introduction must not pass 1000 words!",introduction));
        }
//        INTEREST ERRORS

        if (interest.length()>1000) {
            errors.add(new ValidationError("interest","Interest must not pass 1000 words!",interest));
        }
        return errors;
    }
}
