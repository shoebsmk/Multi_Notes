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

    public String getNoteText() {
        if(noteText.length() >= 80){
            return noteText.substring(0,80) + "...";
        }
        else return noteText;
    }

    public Date getLastSaveTime() {
        return lastSaveTime;
    }

    public final String noteTitle;
    public final String noteText;
    public Date lastSaveTime;
    private static int ctr;

    public Note(String noteTitle, String noteText, Date lastSaveTime) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.lastSaveTime = lastSaveTime;
    }


    Note(){
        noteTitle = "Note " + ctr;
        noteText = "This is sample note text: As a person who wants to use social media " +
                "as a way to advertise or promote a business or yourself," +
                " you should be aware of the tools you have online to make" +
                " your social media campaigns go smoothly.";
        lastSaveTime = Calendar.getInstance().getTime();
        ctr++;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                ", noteTitle='" + noteTitle + '\'' +
                ", note=" + noteText +
                //", time=" + time +
                '}';
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject noteJSON = new JSONObject();
        noteJSON.put("noteTitle",noteTitle);
        noteJSON.put("note", noteText);
        noteJSON.put("time", lastSaveTime.toString());
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
