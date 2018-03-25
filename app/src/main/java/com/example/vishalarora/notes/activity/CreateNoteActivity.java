package com.example.vishalarora.notes.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.vishalarora.notes.NotesApplicationImpl;
import com.example.vishalarora.notes.R;
import com.example.vishalarora.notes.contracts.CreateNotePresenter;
import com.example.vishalarora.notes.contracts.CreateNoteView;
import com.example.vishalarora.notes.di.components.CreateNoteViewComponent;
import com.example.vishalarora.notes.di.components.DaggerCreateNoteViewComponent;
import com.example.vishalarora.notes.util.AppUtil;
import com.example.vishalarora.notes.util.Logger;
import com.example.vishalarora.notes.util.RxUtil;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vishalarora on 25/03/18.
 */

public class CreateNoteActivity extends AppCompatActivity implements CreateNoteView {

    // views
    private EditText noteTitleInput;
    private EditText noteBodyInput;
    private FloatingActionButton createNote;
    private ProgressBar progressBar;

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    CreateNotePresenter createNotePresenter;

    private CreateNoteViewComponent createNoteViewComponent;

    public CreateNoteActivity() {
        createNoteViewComponent = DaggerCreateNoteViewComponent.builder().appComponent(NotesApplicationImpl.getInstance().getAppComponent()).build();
        createNoteViewComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
        bindPresenter();
    }

    private void initListeners() {
        compositeDisposable.add(RxView.clicks(createNote)
                .subscribe(aVoid -> {
                    createNotePresenter.saveNote(noteTitleInput.getText(), noteBodyInput.getText());
                }, Logger::logException));
    }

    private void bindPresenter() {
        createNotePresenter.bind(this);
    }

    private void initViews() {
        noteTitleInput = (EditText) findViewById(R.id.input_title);
        noteBodyInput = (EditText) findViewById(R.id.input_body);
        createNote = (FloatingActionButton) findViewById(R.id.create_note);
        progressBar = (ProgressBar) findViewById(R.id.input_progress);
    }

    @Override
    public void onNoteSaved() {
        AppUtil.showToast(getString(R.string.note_save_success));
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showTitleEmptyError() {
        noteTitleInput.setError(getString(R.string.error_title_empty));
    }

    @Override
    public void showBodyEmptyError() {
        noteBodyInput.setError(getString(R.string.error_body_empty));
    }

    @Override
    public void setProgressView(boolean enabled) {
        progressBar.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindPresenter();

        RxUtil.dispose(compositeDisposable);

        createNoteViewComponent = null;

    }

    private void unbindPresenter() {
        createNotePresenter.unbind();
        createNotePresenter = null;
    }
}
