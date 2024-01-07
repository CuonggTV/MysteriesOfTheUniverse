package com.cuongtv.mysteriesoftheuniverse.dto;

import com.cuongtv.mysteriesoftheuniverse.error.ValidationError;

import java.util.List;

public interface DtoBase {
    List<ValidationError> validate();
}
