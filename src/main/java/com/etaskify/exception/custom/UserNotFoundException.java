package com.etaskify.exception.custom;

public class UserNotFoundException extends RuntimeException{

   public UserNotFoundException(String message){
       super(message);
   }
}
