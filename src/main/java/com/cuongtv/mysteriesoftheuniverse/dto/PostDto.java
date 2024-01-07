package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class PostDto implements DtoBase {
    private String visibility;
    private int groupId;
    private String details;
    private String imageName;

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "visibility='" + visibility + '\'' +
                ", groupId=" + groupId +
                ", details='" + details + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (visibility==null){
            errors.add(new ValidationError("visibility","Visibility must be chosen!",visibility));
        }
        else {
            if (groupId == 0 && visibility.equals("group")){
                errors.add(new ValidationError("groupId","Group must be chosen!",String.valueOf(groupId)));
            }
        }

        if (details.length()==0){
            errors.add(new ValidationError("details","Details must not be empty!",details));
        }
        else if (details.length()>1000){
            errors.add(new ValidationError("details","Details must not pass 1000 letters!",details));
        }

        if ("File size error!".equals(imageName)){
            errors.add(new ValidationError("image","Image must not pass 5MB!",imageName));
        }
        else if ("File image error!".equals(imageName)){
            errors.add(new ValidationError("image","Image must be png or jpg!",imageName));
        }
        return  errors;
    }
}
