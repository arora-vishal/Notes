package com.example.vishalarora.notes.di.modules;

import android.app.Application;

import com.example.vishalarora.notes.db.DbHelper;
import com.example.vishalarora.notes.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vishalarora on 25/03/18.
 */

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public DbHelper getDbhelper() {
        return new DbHelper(application.getApplicationContext(), Constants.DB_NAME, null, Constants.DB_VERSION);
    }

}
