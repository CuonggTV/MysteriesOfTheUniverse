package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.dao.GroupDao;
import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class GroupDto implements DtoBase{
    private String groupName;
    private String currentGroupName;

    private String approve;
    private String details;
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName.trim();
    }

    public void setCurrentGroupName(String currentGroupName) {
        this.currentGroupName = currentGroupName;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details.trim();
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (groupName.length()==0){
            errors.add(new ValidationError("groupName","Group name must not empty!",groupName));
        }
        else if (groupName.length()>256){
            errors.add(new ValidationError("groupName","Group name must not pass 256 letters!",groupName));
        }
        else if (GroupDao.checkGroupName(groupName) && !currentGroupName.equals(groupName)){
            errors.add(new ValidationError("groupName","This group name has existed!",groupName));
        }
        if (approve == null){
            errors.add(new ValidationError("approve","Approve must be chose!",null));
        }

        if(details.length()>1000){
            errors.add(new ValidationError("details","Details must not pass 1000 letters!",details));
        }
        return errors;
    }
}
