package com.songnguyen.example05.exceptions;

public class UserNotFoundException extends Exception  {
    public static final long serialVerionUID=1L;
    public UserNotFoundException(){}
    public UserNotFoundException(String message){
        super(message);
    }
}
