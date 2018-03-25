package com.example.vishalarora.notes.util;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by vishalarora on 25/03/18.
 */

public class RxUtil {

    public static void dispose(CompositeDisposable disposable) {
        if (disposable == null || disposable.isDisposed()) {
            return;
        }

        disposable.clear();
    }
}
