package com.itrosys.student_management_system.exception;

public class BadCredentials extends RuntimeException{

    public BadCredentials(String massage){
        super(massage);
    }
}
