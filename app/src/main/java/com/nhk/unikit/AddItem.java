package com.nhk.unikit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class AddItem extends AppCompatActivity {

    public static final int SELECT_PICTURE = 0;
    public ImageView addedImageBtn;

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        backBtn = (Button) findViewById(R.id.backBtn);
        addedImageBtn = (ImageView) findViewById(R.id.addedImage);
        addedImageBtn.setImageResource(R.drawable.folder);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddItem.this, ItemsPage.class));
            }
        });
        addedImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            Bitmap bitmap = getPath(data.getData());
//            addedImageBtn.setImageBitmap(bitmap);
            Uri selectedImageUri = data.getData();

            try { Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));


                addedImageBtn.setImageBitmap(bitmap);


            } catch(IOException ie) {

                Log.e("Image Error","Error in getting data");
            }
        }
    }

    public Bitmap getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
         cursor.close();
        // Convert file path into bitmap image using below line.
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    public void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }



}

