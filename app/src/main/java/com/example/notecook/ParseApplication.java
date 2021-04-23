package com.example.notecook;

import android.app.Application;

import com.example.notecook.Models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("e3Q0spXEdds4mCS1MrimmN3rbGcJ8wjgM8vyyLFH")
                .clientKey("arwJOGeJ23Zzf0S6UbTUPl1IysFZ5G1CYcdY2Xcb")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

