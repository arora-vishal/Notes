package com.example.vishalarora.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vishalarora.notes.NotesApplicationImpl;
import com.example.vishalarora.notes.R;
import com.example.vishalarora.notes.adapter.NotesListAdapter;
import com.example.vishalarora.notes.contracts.HomePresenter;
import com.example.vishalarora.notes.contracts.HomeView;
import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.di.components.DaggerHomeViewComponent;
import com.example.vishalarora.notes.di.components.HomeViewComponent;
import com.example.vishalarora.notes.di.modules.HomeModule;
import com.example.vishalarora.notes.util.AppUtil;
import com.example.vishalarora.notes.util.Logger;
import com.example.vishalarora.notes.util.RxUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class HomeActivity extends AppCompatActivity implements HomeView {

    // views
    private RecyclerView notesList;
    private FloatingActionButton createNote;
    private View defaultView;
    private View progressBar;


    private static final int RC_CREATE_NOTE = 1000;

    @Inject
    NotesListAdapter notesListAdapter;

    @Inject
    PublishSubject<Pair<Note, Integer>> noteClickSubject;

    @Inject
    HomePresenter homePresenter;

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    GridLayoutManager gridLayoutManager;

    private HomeViewComponent homeViewComponent;

    public HomeActivity() {
        homeViewComponent = DaggerHomeViewComponent.builder().appComponent(NotesApplicationImpl.getInstance().getAppComponent()).homeModule(new HomeModule(this)).build();
        homeViewComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListeners();
        bindPresenter();
        loadData();
    }

    private void loadData() {
        homePresenter.loadData();
    }

    private void bindPresenter() {
        homePresenter.bind(this);
    }

    private void unbindPresenter() {
        homePresenter.unbind();
        homePresenter = null;
    }


    private void initViews() {
        notesList = (RecyclerView) findViewById(R.id.notes_list);
        createNote = (FloatingActionButton) findViewById(R.id.create_note);
        defaultView = findViewById(R.id.default_view);
        progressBar = findViewById(R.id.home_progress);

        notesList.setLayoutManager(gridLayoutManager);
        notesList.setAdapter(notesListAdapter);
    }

    private void initListeners() {

        compositeDisposable.add(RxView.clicks(createNote)
                .subscribe(aVoid -> startActivityForResult(new Intent(HomeActivity.this, CreateNoteActivity.class), RC_CREATE_NOTE), Logger::logException));

        compositeDisposable.add(noteClickSubject.subscribe(noteIntegerPair -> homePresenter.removeNote(noteIntegerPair.first, noteIntegerPair.second, notesListAdapter.getItemCount()), Logger::logException));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if a note was successfully created then we need to reload data
        if (requestCode == RC_CREATE_NOTE && resultCode == RESULT_OK) {
            homePresenter.reload();
        }
    }

    @Override
    public void showDefault() {
        // hide the list
        notesList.setVisibility(View.GONE);
        defaultView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotes(List<Note> notes) {
        // make the list visible
        defaultView.setVisibility(View.GONE);
        notesList.setVisibility(View.VISIBLE);

        // add notes and notify for update
        notesListAdapter.setNotes(notes);
        notesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void setProgressView(boolean enabled) {
        progressBar.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @Override
    public void removeItem(Note note) {
        notesListAdapter.removeItem(note);
    }

    @Override
    public void showError() {
        AppUtil.showToast(getString(R.string.error));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // clear view reference in presenter
        unbindPresenter();

        RxUtil.dispose(compositeDisposable);

        // clear dependencies
        homeViewComponent = null;
    }
}
