package com.example.multinotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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



        adapter = new NotesViewHolderAdapter(this,noteList);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
//        try {
//            //SaveDataToFile();
//            //noteList.clear();
//            LoadDataFromFile();
//            Log.d(TAG, "onRestore: " + noteList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(noteList.isEmpty()){
            for(int i = 0 ; i<20 ; i++){
                noteList.add(new Note());
                setTitle("Multi Notes [" + noteList.size() + "]");
            }
        }


    }

    @Override
    protected void onPause() {

//        try {
//            SaveDataToFile();
//            //noteList.clear();
//            //LoadDataFromFile();
//            Log.d(TAG, "onPause: " + noteList);
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        try {
//            //SaveDataToFile();
//            //noteList.clear();
//            LoadDataFromFile();
//            Log.d(TAG, "onRestore: " + noteList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        super.onRestoreInstanceState(savedInstanceState);
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
        promtDeleteDailog(view);
        //DeleteView(view);
        return false;
    }

    private void promtDeleteDailog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setPositiveButton("YES",(dailog, id) -> DeleteView(view));
        builder.setNegativeButton("CANCEL",(dailog, id)
                -> Toast.makeText(this,"Note Discarded",Toast.LENGTH_SHORT).show());
        builder.setTitle("Your note is not saved!");

        int pos = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(pos);
        builder.setMessage("Save note? '" + note.noteTitle + "'" );

        AlertDialog dailog =  builder.create();
        dailog.show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addMenuButton){
            Toast.makeText(this,"You selected: "
                    + item.getTitle().toString(),Toast.LENGTH_SHORT).show();

            return true;
        } else if(item.getItemId() == R.id.infoMenuButton) {
            Toast.makeText(this,"You selected: "
                    + item.getTitle().toString(),Toast.LENGTH_SHORT).show();
            onInfoClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void onInfoClicked(){
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
    }
}