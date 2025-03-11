package com.itrosys.student_management_system.exception;

public class UsernameAndEmailAlreadyExists extends  RuntimeException{

    public UsernameAndEmailAlreadyExists(String massage){
        super(massage);
    }
}
