package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordDto implements DtoBase{
    private String oldPass;
    private String inputOldPass;
    private String newPass;
    private String confirmPass;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getInputOldPass() {
        return inputOldPass;
    }

    public void setInputOldPass(String inputOldPass) {
        this.inputOldPass = inputOldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if(!oldPass.equals(inputOldPass)){
            errors.add(new ValidationError("inputOldPass","Enter wrong old password!",inputOldPass));
        }
        if (newPass.length() == 0){
            errors.add(new ValidationError("newPass","New password must not be empty!",newPass));
        }
        else if(newPass.equals(oldPass)){
            errors.add(new ValidationError("newPass","New password matches old password!",newPass));
        }
        else if(newPass.length()>256){
            errors.add(new ValidationError("newPass","New password must not pass 256 letters!",newPass));
        }
        if(!confirmPass.equals(newPass)){
            errors.add(new ValidationError("confirmPass","Confirm password does not match!",confirmPass));
        }

        return errors;
    }
}
