package com.etaskify.service;


public interface EmailService {

    void sendNotification(String to, String subject, String message);

}
