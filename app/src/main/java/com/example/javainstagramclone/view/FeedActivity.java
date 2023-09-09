package com.example.javainstagramclone.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.javainstagramclone.R;
import com.example.javainstagramclone.adapter.PostAdapter;
import com.example.javainstagramclone.databinding.ActivityFeedBinding;
import com.example.javainstagramclone.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    //Postun değerlerini tutacak arrayList oluşturuyoruz
    ArrayList<Post> postArrayList;

    private ActivityFeedBinding binding;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getData();

        postArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);
    }

    //Verileri firestoredan çekecez
    private void getData() {

        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if (value != null) {

                    for (DocumentSnapshot snapshot : value.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();

                        String useremail = (String) data.get("useremail");
                        String comment = (String) data.get("comment");
                        String downloadurl = (String) data.get("downloadurl");

                        Post post = new Post(useremail, comment, downloadurl);
                        postArrayList.add(post);

                    }

                    //Yeni veriler geldi recycler view'e haber ver diyor aşağıdaki kod
                    postAdapter.notifyDataSetChanged();

                }
            }
        });

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