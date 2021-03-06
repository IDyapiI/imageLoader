package com.example.applicationloader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private String imagepath;
    private ImageView image;
    private Button button;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.image);
        image.setVisibility(View.INVISIBLE);
        button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            imagepath = getPath(filePath);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image = findViewById(R.id.image);
                image.setImageBitmap(bitmap);
                button = findViewById(R.id.button2);
                button.setVisibility(View.INVISIBLE);
                image.setVisibility(View.VISIBLE);
                returnButton = findViewById(R.id.return_button);
                returnButton.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void returnButton(View v){
        image = findViewById(R.id.image);
        button = findViewById(R.id.button2);
        returnButton = findViewById(R.id.return_button);
        returnButton.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
    }
}
