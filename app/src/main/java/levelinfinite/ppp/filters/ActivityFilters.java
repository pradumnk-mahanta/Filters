package levelinfinite.ppp.filters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

public class ActivityFilters extends AppCompatActivity {

    ImageView imageFilter;
    String selectedImage;
    Bitmap selectedImageBitmap, processedImage;
    FloatingActionButton addFab, removeFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters_main);
        imageFilter = (ImageView) findViewById(R.id.imageFilter);

        addFab = (FloatingActionButton) findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityFilters = new Intent(getApplicationContext(), ActivityExport.class);
                processedImage = selectedImageBitmap;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                processedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] pictureArray = byteArrayOutputStream.toByteArray();
                activityFilters.putExtra("finalImage", pictureArray);
                startActivity(activityFilters);
            }
        });

        removeFab = (FloatingActionButton) findViewById(R.id.removeFab);
        removeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityFilters = new Intent(getApplicationContext(), ActivityLanding.class);
                startActivity(activityFilters);
                finish();
            }
        });

        selectedImage = getIntent().getStringExtra("imagePath");

        Log.d("Image URI", selectedImage);

        Uri selectedImageURI = Uri.parse(selectedImage);

        try{
            selectedImageBitmap = getBitmapFromURI(selectedImageURI);
            selectedImageBitmap = Bitmap.createScaledBitmap(selectedImageBitmap, imageFilter.getWidth(), imageFilter.getHeight(),true);
        }catch(Exception e){

        }
        imageFilter.setImageBitmap(selectedImageBitmap);
    }

    private Bitmap getBitmapFromURI(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
