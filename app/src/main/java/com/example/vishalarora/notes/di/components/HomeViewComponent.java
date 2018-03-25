package com.example.vishalarora.notes.di.components;

import com.example.vishalarora.notes.activity.HomeActivity;
import com.example.vishalarora.notes.contracts.HomeView;
import com.example.vishalarora.notes.di.modules.HomeModule;
import com.example.vishalarora.notes.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by vishalarora on 25/03/18.
 */

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeViewComponent {
    void inject(HomeActivity homeView);
}
