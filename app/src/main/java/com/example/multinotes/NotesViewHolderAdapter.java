package com.example.multinotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class NotesViewHolderAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final String TAG = getClass().getSimpleName();
    private final MainActivity mainActivity;
    private final ArrayList<Note> noteArrayList;

    public NotesViewHolderAdapter(MainActivity mainActivity, ArrayList<Note> noteArrayList) {
        this.mainActivity = mainActivity;
        this.noteArrayList = noteArrayList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_entry,parent,false);
        itemView.setOnLongClickListener(mainActivity);
        itemView.setOnClickListener(mainActivity);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Note note = noteArrayList.get(position);

        holder.noteTitle.setText(note.getNoteTitle());
        holder.note.setText(note.getNote());
        holder.time.setText(String.valueOf(note.getTime()));
        holder.time.setText(new Date().toString());
    }


    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }
}
