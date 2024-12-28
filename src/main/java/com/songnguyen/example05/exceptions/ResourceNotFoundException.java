package com.songnguyen.example05.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVerionUID=1L;
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;
    public ResourceNotFoundException(){

    }
    public ResourceNotFoundException(String resourceName,String field,String fieldName){
        super("%s not found with %s: %s".formatted(resourceName,field,fieldName));
        this.resourceName=resourceName;
        this.field=field;
        this.fieldName=fieldName;
    }
    public ResourceNotFoundException(String resourceName,String field,Long fieldId){
        super("%d not found with %d: %d".formatted(resourceName,field,fieldId));
        this.resourceName=resourceName;
        this.field=field;
        this.fieldId=fieldId;
    }
}
