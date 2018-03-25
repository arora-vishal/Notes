package com.example.vishalarora.notes.util;

import android.widget.Toast;

import com.example.vishalarora.notes.NotesApplicationImpl;

/**
 * Created by vishalarora on 25/03/18.
 */

public class AppUtil {
    public static void showToast(String message) {
        Toast.makeText(NotesApplicationImpl.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}

