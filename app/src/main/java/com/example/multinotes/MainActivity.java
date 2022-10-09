package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener {


    private static final String TAG = "MainActivity";
    private final ArrayList<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesViewHolderAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.myRV);
        Log.d(TAG, "onCreate: CheckConversion: " + CheckConversion());


        try {
            //SaveDataToFile();
            noteList.clear();
            LoadDataFromFile();
            Log.d(TAG, "onCreate: " + noteList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new NotesViewHolderAdapter(this,noteList);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(noteList.isEmpty()){
            for(int i = 0 ; i<20 ; i++){
                noteList.add(new Note());
                setTitle("Multi Notes [" + noteList.size() + "]");
            }
        }


    }

    private void LoadDataFromFile() throws FileNotFoundException {
        FileInputStream fis = null;
        try{
            fis = getApplicationContext().openFileInput("NOTE-DATA.json");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            StringBuilder fileContent = new StringBuilder();
            byte [] buffer = new byte[4000];
            int n;
            while((n=fis.read(buffer)) != -1){
                fileContent.append(new String(buffer,0,n));
            }
            JSONArray noteListJSONARR = new JSONArray(fileContent.toString());
            Log.d(TAG, "LoadDataFromFile: " );
            for(int i = 0 ; i< noteListJSONARR.length(); i++){
                noteList.add(Note.JSONTONOTE(noteListJSONARR.getJSONObject(i)));
            }
        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }

    }


    private void SaveDataToFile() throws JSONException, IOException {
        JSONArray noteListJSONARR = new JSONArray();
        for(Note note:noteList){
            noteListJSONARR.put(note.toJSON());
        }
        FileOutputStream fos = getApplicationContext().openFileOutput("NOTE-DATA.json",MODE_PRIVATE);
        PrintWriter writer = new PrintWriter(fos);
        writer.println(noteListJSONARR);
        writer.close();
        fos.close();
        Log.d(TAG, "SaveDataToFile: SAVED");
    }

    private boolean CheckConversion() {
        Note note1 = new Note();
        Note note2 = null;

        try {
            JSONObject nj = note1.toJSON();
            note2 = Note.JSONTONOTE(nj);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

        String s1 = note1.toString();
        String s2 = note2.toString();

        if(s1.equals(s2) && s1.compareTo(s2) == 0) return true;
        else return false;
    }

    @Override
    public boolean onLongClick(View view) {
        DeleteView(view);
        return false;
    }

    private void DeleteView(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(pos);
        Toast.makeText(this,"Deleted Note: "
                + note.noteTitle.toString() + " From Pos: " + pos, Toast.LENGTH_SHORT).show();
        noteList.remove(pos);
        adapter.notifyItemRemoved(pos);
        setTitle("Multi Notes [" + noteList.size() + "]");
    }

    @Override
    public void onClick(View view) {


    }
}