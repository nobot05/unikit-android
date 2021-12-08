package com.nhk.unikit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

public class ItemsPage extends AppCompatActivity {

    Button addBtn;
    Button userBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_page);
        addBtn = (Button) findViewById(R.id.btn_add_item);
        userBtn = (Button) findViewById(R.id.profileBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemsPage.this, AddItem.class));
            }
        });

        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemsPage.this, UserProfile.class));
            }
        });

    }
}