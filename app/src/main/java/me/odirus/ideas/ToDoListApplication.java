package me.odirus.ideas;

import android.app.Application;

public class ToDoListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVService.AVInit(this);
    }
}
