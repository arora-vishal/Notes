package com.example.vishalarora.notes.presenter;

import com.example.vishalarora.notes.contracts.CreateNotePresenter;
import com.example.vishalarora.notes.contracts.CreateNoteView;
import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.contracts.Tables;
import com.example.vishalarora.notes.interactor.CreateNoteInteractor;
import com.example.vishalarora.notes.rx.RxSchedulerAbs;
import com.example.vishalarora.notes.rx.RxSchedulerTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static org.junit.Assert.*;

/**
 * Created by vishalarora on 26/03/18.
 */
public class CreateNotePresenterImplTest {

    @Mock
    CreateNoteView createNoteView;

    @Mock
    CreateNoteInteractor createNoteInteractor;

    CreateNotePresenter createNotePresenter;

    CompositeDisposable compositeDisposable;

    RxSchedulerAbs rxSchedulerAbs;

    @Mock
    Note note;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        compositeDisposable = new CompositeDisposable();
        rxSchedulerAbs = new RxSchedulerTest();

        createNotePresenter = new CreateNotePresenterImpl(createNoteInteractor, compositeDisposable, rxSchedulerAbs);
        createNotePresenter.bind(createNoteView);
    }


    @Test
    public void saveNote() throws Exception {

        Mockito.doReturn(Observable.just(true)).when(createNoteInteractor).createNote(Mockito.any());

        createNotePresenter.saveNote("", "");

        Mockito.verify(createNoteView).showTitleEmptyError();

        createNotePresenter.saveNote("title", "");

        Mockito.verify(createNoteView).showBodyEmptyError();

        createNotePresenter.saveNote("title", "body");

        Mockito.verify(createNoteView).onNoteSaved();
    }

}