package com.example.vishalarora.notes.presenter;

import com.example.vishalarora.notes.adapter.NotesListAdapter;
import com.example.vishalarora.notes.contracts.HomePresenter;
import com.example.vishalarora.notes.contracts.HomeView;
import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.contracts.Tables;
import com.example.vishalarora.notes.interactor.NotesInteractor;
import com.example.vishalarora.notes.rx.RxSchedulerAbs;
import com.example.vishalarora.notes.rx.RxSchedulerTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vishalarora on 25/03/18.
 */
public class HomePresenterImplTest {

    @Mock
    HomeView homeView;

    @Mock
    NotesInteractor notesInteractor;

    CompositeDisposable compositeDisposable;

    HomePresenter homePresenter;

    List<Note> noteList;

    RxSchedulerAbs rxSchedulerAbs;

    @Mock
    Note note;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteList = new ArrayList<>();
        noteList.add(note);
        compositeDisposable = new CompositeDisposable();
        rxSchedulerAbs = new RxSchedulerTest();
        homePresenter = new HomePresenterImpl(notesInteractor, compositeDisposable, rxSchedulerAbs);
        homePresenter.bind(homeView);
    }

    @Test
    public void loadData() throws Exception {
        Mockito.doReturn(Observable.just(noteList)).when(notesInteractor).fetchNotesOrderBy(Tables.Notes.MODIFIED_TIMESTAMP, true);

        homePresenter.loadData();

        Mockito.verify(homeView).showNotes(noteList);
    }

    @Test
    public void reload() throws Exception {
        loadData();
    }

    @Test
    public void removeNote() throws Exception {

        Mockito.when(notesInteractor.removeNote(note.getId())).thenReturn(Observable.just(true));

        homePresenter.removeNote(note, NotesListAdapter.Action.DELETE, 2);

        Mockito.verify(homeView).removeItem(note);

        homePresenter.removeNote(note, NotesListAdapter.Action.DELETE, 1);

        Mockito.verify(homeView).showDefault();

        homePresenter.removeNote(note, NotesListAdapter.Action.CLICK, 1);

    }

}