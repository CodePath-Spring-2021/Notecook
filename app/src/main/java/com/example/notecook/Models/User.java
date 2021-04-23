package com.example.notecook.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject {

    private static final String KEY_PROFILE_IMAGE = "profileImage";

    public ParseFile getProfileImage() { return getParseFile(KEY_PROFILE_IMAGE); }
}
