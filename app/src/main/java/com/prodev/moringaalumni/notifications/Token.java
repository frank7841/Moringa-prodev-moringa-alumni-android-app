package com.prodev.moringaalumni.notifications;

public class Token {

    /* An FCM Token, or much commonly known as a registrationToken.
    An id issued by the GCM connection servers to client app allows it to recieve messages

     */
    String token;
    public Token(String token){
        this.token = token;
    }
    public Token(){

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
