package com.nhk.unikit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ItemsPage extends ListActivity {

    Button addBtn;
    Button userBtn;
    SearchView search;


    String URL = "http://192.168.86.198/Unikit-Final/getAllProducts.php";

    private ListView listView;

    private String [] itemNames = {"Locker Keys","Surface 3 for sale","Calculus 3"};
    private String [] itemsDesc = {"Locker Keys for trading for books!","Used in good condition","trading for CSC 498X book"};
    private Integer [] imageItems = {R.drawable.keys,R.drawable.laptop,R.drawable.calc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_page);
        addBtn = (Button) findViewById(R.id.btn_add_item);
        userBtn = (Button) findViewById(R.id.profileBtn);
        search = (SearchView) findViewById(R.id.searchProduct);

        CharSequence query = search.getQuery();
        CharSequence queryHint = search.getQueryHint();

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

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;
            }catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
                JSONObject json = new JSONObject(s);
                String title = json.getString("title");
                String description = json.getString("description");
                String image = json.getString("image");
                JSONArray arr = new JSONArray(title);

                for(int i=0; i<arr.length(); i++){
                    JSONObject part = arr.getJSONObject(i);
                    Log.i("main", part.getString("main"));
                    Log.i("description", part.getString("description"));
                }

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}