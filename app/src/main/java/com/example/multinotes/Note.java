package com.example.multinotes;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Note {
    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNote() {
        return note;
    }

    public Date getTime() {
        return time;
    }

    public final String noteTitle;
    public final String note;
    public Date time;
    private static int ctr;

    public Note(String noteTitle, String note, Date time) {
        this.noteTitle = noteTitle;
        this.note = note;
        //this.time = time;
    }


    Note(){
        noteTitle = "Note " + ctr;
        note = "This is sample note text: As a person who wants to use social media " +
                "as a way to advertise or promote a business or yourself," +
                " you should be aware of the tools you have online to make" +
                " your social media campaigns go smoothly.";
        time  = Calendar.getInstance().getTime();
        ctr++;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                ", noteTitle='" + noteTitle + '\'' +
                ", note=" + note +
                //", time=" + time +
                '}';
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject noteJSON = new JSONObject();
        noteJSON.put("noteTitle",noteTitle);
        noteJSON.put("note",note);
        noteJSON.put("time",time.toString());
        return noteJSON;
    }

    public static Note JSONTONOTE(JSONObject noteJSON) throws JSONException, ParseException {
        String noteTitle = noteJSON.getString("noteTitle");
        String note = noteJSON.getString("note");
        String timeS = noteJSON.getString("time");

        //noinspection deprecation
        //Date time = DateFormat.getDateInstance().parse(timeS);

        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        Date time = sdf.parse(timeS);
        SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
        Log.d("TAG", "JSONTONOTE: "+ print.format(time));
        return new Note(noteTitle,note,time);

    }

}
