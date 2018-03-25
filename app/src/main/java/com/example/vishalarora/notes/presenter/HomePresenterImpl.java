package com.example.vishalarora.notes.presenter;

import com.example.vishalarora.notes.adapter.NotesListAdapter;
import com.example.vishalarora.notes.contracts.HomePresenter;
import com.example.vishalarora.notes.contracts.HomeView;
import com.example.vishalarora.notes.data.Note;
import com.example.vishalarora.notes.db.contracts.Tables;
import com.example.vishalarora.notes.interactor.NotesInteractor;
import com.example.vishalarora.notes.rx.RxSchedulerAbs;
import com.example.vishalarora.notes.util.Logger;
import com.example.vishalarora.notes.util.RxUtil;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vishalarora on 25/03/18.
 */

public class HomePresenterImpl implements HomePresenter {

    private WeakReference<HomeView> homeViewWeakReference;
    private NotesInteractor notesInteractor;
    private CompositeDisposable compositeDisposable;
    private RxSchedulerAbs rxSchedulerAbs;

    public HomePresenterImpl(NotesInteractor notesInteractor, CompositeDisposable compositeDisposable, RxSchedulerAbs rxSchedulerAbs) {
        this.notesInteractor = notesInteractor;
        this.compositeDisposable = compositeDisposable;
        this.rxSchedulerAbs = rxSchedulerAbs;
    }

    @Override
    public void bind(HomeView homeView) {
        homeViewWeakReference = new WeakReference<>(homeView);
    }


    @Override
    public void unbind() {
        RxUtil.dispose(compositeDisposable);
        if (homeViewWeakReference.get() != null) {
            homeViewWeakReference.clear();
        }
    }

    @Override
    public void loadData() {
        compositeDisposable.add(notesInteractor.fetchNotesOrderBy(Tables.Notes.MODIFIED_TIMESTAMP, true)
                .subscribeOn(rxSchedulerAbs.getIOScheduler())
                .observeOn(rxSchedulerAbs.getMainThreadScheduler())
                .doOnNext(notes -> getView().setProgressView(false))
                .doOnError(notes -> getView().setProgressView(false))
                .subscribe(notes -> {
                    if (getView() != null) {
                        if (notes.size() == 0) {
                            getView().showDefault();
                        } else {
                            getView().showNotes(notes);
                        }
                    }
                }, Logger::logException));

    }

    @Override
    public void reload() {
        getView().setProgressView(true);
        loadData();

    }

    @Override
    public void removeNote(Note note, int action, int itemCount) {
        if (action == NotesListAdapter.Action.DELETE) {
            getView().setProgressView(true);
            compositeDisposable.add(notesInteractor.removeNote(note.getId())
                    .subscribeOn(rxSchedulerAbs.getIOScheduler())
                    .observeOn(rxSchedulerAbs.getMainThreadScheduler())
                    .doOnNext(aBoolean -> getView().setProgressView(false))
                    .doOnError(aBoolean -> getView().setProgressView(false))
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            getView().removeItem(note);
                        } else {
                            getView().showError();
                        }

                        if (itemCount == 1) {
                            getView().showDefault();
                        }
                    }, Logger::logException));
        }
    }

    private HomeView getView() {
        return homeViewWeakReference.get();
    }

}
