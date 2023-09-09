package com.example.javainstagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        auth = FirebaseAuth.getInstance();

        
    }



    //Menüyü bağlayan kodlar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Menuden item seçilince ne olacağını yazıyorduk
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_post) {

            //Upload Activiteye gidilecek
            Intent intentUpload = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intentUpload);

        }else if (item.getItemId() == R.id.signout) {

            //SignOut işlemi

            auth.signOut();

            Intent intentToMain = new Intent (FeedActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}