package com.example.vishalarora.notes.contracts;

/**
 * Created by vishalarora on 25/03/18.
 */

public interface BasePresenter<T> {
    void bind(T view);
    void unbind();
}
