package com.nhk.unikit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class ItemsPage extends ListActivity {

    Button addBtn;
    Button userBtn;

    private ListView listView;

    private String [] itemNames = {"Item 1"};
    private String [] itemsDesc = {"Item 1 for trade"};
    private Integer [] imageItems = {R.drawable.folder};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_page);
        addBtn = (Button) findViewById(R.id.btn_add_item);
        userBtn = (Button) findViewById(R.id.profileBtn);

        ListView listView=(ListView)findViewById(android.R.id.list);
        listViewDetails customCountryList = new listViewDetails(this, itemNames, itemsDesc, imageItems);
        listView.setAdapter(customCountryList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),"You Selected "+itemNames[position-1]+ " as Country",Toast.LENGTH_SHORT).show();        }
        });


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