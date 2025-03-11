package com.itrosys.student_management_system.exception;

public class EmailAlreadyExists extends RuntimeException{

    public EmailAlreadyExists(String massage){
        super(massage);
    }
}
