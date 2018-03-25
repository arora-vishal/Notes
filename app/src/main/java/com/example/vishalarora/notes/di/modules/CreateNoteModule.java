package com.example.vishalarora.notes.di.modules;

import com.example.vishalarora.notes.contracts.CreateNotePresenter;
import com.example.vishalarora.notes.db.DbHelper;
import com.example.vishalarora.notes.db.NotesRepoImpl;
import com.example.vishalarora.notes.db.contracts.NotesRepo;
import com.example.vishalarora.notes.di.scope.ActivityScope;
import com.example.vishalarora.notes.interactor.CreateNoteInteractor;
import com.example.vishalarora.notes.interactor.CreateNoteInteractorImpl;
import com.example.vishalarora.notes.presenter.CreateNotePresenterImpl;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vishalarora on 25/03/18.
 */

@Module
public class CreateNoteModule {

    @Provides
    @ActivityScope
    public CreateNotePresenter createNotePresenter(CreateNoteInteractor createNoteInteractor, CompositeDisposable compositeDisposable) {
        return new CreateNotePresenterImpl(createNoteInteractor, compositeDisposable);
    }

    @Provides
    @ActivityScope
    public CreateNoteInteractor createNoteInteractor(NotesRepo notesRepo) {
        return new CreateNoteInteractorImpl(notesRepo);
    }

    @Provides
    @ActivityScope
    NotesRepo getNotesRepo(DbHelper dbHelper) {
        return new NotesRepoImpl(dbHelper);
    }

    @Provides
    @ActivityScope
    CompositeDisposable compositeDisposable() {
        return new CompositeDisposable();
    }

}
