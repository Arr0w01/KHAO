package com.example.khao;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khao2.R;
import com.google.firebase.database.FirebaseDatabase;

import adaptor.FilterCatAdapter;

public class CategoryView extends AppCompatActivity {
    RecyclerView category_RV;
    FilterCatAdapter adapter;
    LinearLayout sort;
    public static String cat;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));


        category_RV = findViewById(R.id.catRV);
        category_RV.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), Food.class)
                        .build();




        adapter = new FilterCatAdapter(options);
        category_RV.setAdapter(adapter);

        Intent intent = getIntent();
        cat = intent.getStringExtra("Category");

        sort = findViewById(R.id.category_sort);
              sort.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Toast.makeText(CategoryView.this, "filter", Toast.LENGTH_SHORT).show();
                  }
              });


        back = findViewById(R.id.catBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}