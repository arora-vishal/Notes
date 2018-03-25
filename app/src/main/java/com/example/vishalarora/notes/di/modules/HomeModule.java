package com.example.vishalarora.notes.di.modules;

import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;

import com.example.vishalarora.notes.activity.HomeActivity;
import com.example.vishalarora.notes.adapter.NotesListAdapter;
import com.example.vishalarora.notes.contracts.HomePresenter;
import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.DbHelper;
import com.example.vishalarora.notes.db.NotesRepoImpl;
import com.example.vishalarora.notes.db.contracts.NotesRepo;
import com.example.vishalarora.notes.di.scope.ActivityScope;
import com.example.vishalarora.notes.interactor.NotesInteractor;
import com.example.vishalarora.notes.interactor.NotesInteractorImpl;
import com.example.vishalarora.notes.presenter.HomePresenterImpl;
import com.example.vishalarora.notes.rx.RxSchedulerAbs;
import com.example.vishalarora.notes.rx.RxSchedulers;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by vishalarora on 25/03/18.
 */

@Module
public class HomeModule {

    private static final int SPAN_SIZE = 2;

    private HomeActivity homeActivity;

    public HomeModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @ActivityScope
    HomePresenter getPresenter(NotesInteractor notesInteractor, CompositeDisposable compositeDisposable, RxSchedulerAbs rxSchedulerAbs) {
        return new HomePresenterImpl(notesInteractor, compositeDisposable, rxSchedulerAbs);
    }

    @Provides
    @ActivityScope
    NotesListAdapter getNotesListAdapter(PublishSubject<Pair<Note, Integer>> clickSubject) {
        return new NotesListAdapter(clickSubject);
    }

    @Provides
    @ActivityScope
    NotesInteractor getNotesInteractor(NotesRepo notesRepo) {
        return new NotesInteractorImpl(notesRepo);
    }

    @Provides
    @ActivityScope
    NotesRepo getNotesRepo(DbHelper dbHelper) {
        return new NotesRepoImpl(dbHelper);
    }

    @Provides
    @ActivityScope
    PublishSubject<Pair<Note, Integer>> clickSubject() {
        return PublishSubject.create();
    }

    @Provides
    @ActivityScope
    CompositeDisposable compositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScope
    GridLayoutManager gridLayoutManager() {
        return new GridLayoutManager(homeActivity, SPAN_SIZE);
    }

    @Provides
    @ActivityScope
    RxSchedulerAbs rxSchedulerAbs(){
        return new RxSchedulers();
    }
}
