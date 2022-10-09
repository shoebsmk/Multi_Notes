package com.example.multinotes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public TextView noteTitle;
    public TextView note;
    public TextView time;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        note = itemView.findViewById(R.id.noteTV);
        noteTitle = itemView.findViewById((R.id.noteTitleTV));
        time = itemView.findViewById(R.id.timeTV);
    }
}
