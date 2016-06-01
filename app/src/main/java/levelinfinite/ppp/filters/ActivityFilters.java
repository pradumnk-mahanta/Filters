package levelinfinite.ppp.filters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class ActivityFilters extends AppCompatActivity {

    ImageView imageFilter;
    String selectedImage;
    Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        imageFilter = (ImageView) findViewById(R.id.imageFilter);

        selectedImage = getIntent().getStringExtra("imagePath");

        Log.d("Image URI", selectedImage);

        Uri selectedImageURI = Uri.parse(selectedImage);

        try{
            selectedImageBitmap = getBitmapFromURI(selectedImageURI);
            selectedImageBitmap = Bitmap.createScaledBitmap(selectedImageBitmap, imageFilter.getWidth(), imageFilter.getHeight(),true);
        }catch(Exception e){

        }

        
        File image = new File(selectedImage);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        //Drawable d = new BitmapDrawable(getResources(), myBitmap);
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
