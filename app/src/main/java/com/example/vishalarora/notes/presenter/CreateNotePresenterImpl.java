package com.example.vishalarora.notes.presenter;


import com.example.vishalarora.notes.contracts.CreateNotePresenter;
import com.example.vishalarora.notes.contracts.CreateNoteView;
import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.interactor.CreateNoteInteractor;
import com.example.vishalarora.notes.rx.RxSchedulerAbs;
import com.example.vishalarora.notes.util.AppUtil;
import com.example.vishalarora.notes.util.Logger;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vishalarora on 25/03/18.
 */

public class CreateNotePresenterImpl implements CreateNotePresenter {

    private CreateNoteInteractor createNoteInteractor;
    private WeakReference<CreateNoteView> createNoteViewWeakReference;
    private CompositeDisposable compositeDisposable;
    private RxSchedulerAbs rxSchedulerAbs;

    public CreateNotePresenterImpl(CreateNoteInteractor createNoteInteractor, CompositeDisposable compositeDisposable, RxSchedulerAbs rxSchedulerAbs) {
        this.createNoteInteractor = createNoteInteractor;
        this.compositeDisposable = compositeDisposable;
        this.rxSchedulerAbs = rxSchedulerAbs;
    }

    @Override
    public void saveNote(CharSequence title, CharSequence body) {
        getView().setProgressView(true);

        if (AppUtil.isEmpty(title)) {
            getView().showTitleEmptyError();
            getView().setProgressView(false);
            return;
        }

        if (AppUtil.isEmpty(body)) {
            getView().showBodyEmptyError();
            getView().setProgressView(false);
            return;
        }

        Note note = new Note.Builder().withTitle(title.toString())
                .withBody(body.toString())
                .withCreatedTimeStamp(System.currentTimeMillis())
                .build();

        compositeDisposable.add(createNoteInteractor.createNote(note)
                .subscribeOn(rxSchedulerAbs.getIOScheduler())
                .observeOn(rxSchedulerAbs.getMainThreadScheduler())
                .doOnNext(aBoolean -> getView().setProgressView(false))
                .doOnError(aBoolean -> getView().setProgressView(false))
                .subscribe(aBoolean -> {
                    if (getView() != null) {
                        getView().onNoteSaved();
                    }
                }, Logger::logException));

    }

    @Override
    public void bind(CreateNoteView view) {
        createNoteViewWeakReference = new WeakReference<>(view);
    }

    @Override
    public void unbind() {
        if (createNoteViewWeakReference.get() != null) {
            createNoteViewWeakReference.clear();
        }
    }

    private CreateNoteView getView() {
        return createNoteViewWeakReference.get();
    }
}
