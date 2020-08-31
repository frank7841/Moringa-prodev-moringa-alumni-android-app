package com.prodev.moringaalumni.models;

public class ModelPost {
    // use same name as uploading post
    String pId, pTitle, pDescr, pImage, uid, uEmail, uDp, uName;

    public ModelPost(){

    }

    public ModelPost(String pId, String pTitle, String pDescr, String pImage, String uid, String uEmail, String uDp, String uName) {
        this.pId = pId;
        this.pTitle = pTitle;
        this.pDescr = pDescr;
        this.pImage = pImage;
        this.uid = uid;
        this.uEmail = uEmail;
        this.uDp = uDp;
        this.uName = uName;
    }
}
