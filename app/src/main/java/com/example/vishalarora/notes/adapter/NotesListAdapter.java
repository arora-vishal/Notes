package com.example.vishalarora.notes.adapter;

import android.support.annotation.IntDef;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vishalarora.notes.R;
import com.example.vishalarora.notes.data.Note;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by vishalarora on 25/03/18.
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {

    private List<Note> notes;

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    private PublishSubject<Pair<Note, Integer>> clickSubject;

    public NotesListAdapter(PublishSubject<Pair<Note, Integer>> clickSubject) {
        this.clickSubject = clickSubject;
    }


    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_view, parent, false), clickSubject);
    }

    @IntDef({Action.CLICK, Action.DELETE})
    public @interface Action {
        int CLICK = 0;
        int DELETE = 1;
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        holder.bind(notes.get(position));

    }

    public void removeItem(Note note) {
        int index = notes.indexOf(note);
        notes.remove(index);
        notifyItemRemoved(index);
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView bodyView;
        private ImageButton deleteView;
        private Note note;


        public NotesViewHolder(View itemView, PublishSubject<Pair<Note, Integer>> clickSubject) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.note_title);
            bodyView = (TextView) itemView.findViewById(R.id.note_body);
            deleteView = (ImageButton) itemView.findViewById(R.id.note_delete);

            itemView.setOnClickListener(v -> {
                clickSubject.onNext(new Pair(note, Action.CLICK));
            });

            deleteView.setOnClickListener(v -> {
                clickSubject.onNext(new Pair(note, Action.DELETE));
            });
        }

        public void bind(Note note) {
            this.note = note;
            titleView.setText(note.getTitle());
            bodyView.setText(note.getBody());
        }
    }
}
