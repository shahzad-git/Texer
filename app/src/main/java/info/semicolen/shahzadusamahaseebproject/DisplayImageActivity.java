package info.semicolen.shahzadusamahaseebproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import info.semicolen.shahzadusamahaseebproject.Repository.ImageRepository;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.transitionseverywhere.TransitionManager;

import java.io.File;

public class DisplayImageActivity extends AppCompatActivity {

    //Views
    ImageView displayImage;
    CardView textRecognizerButton;
    ProgressBar progressBar;
    public static Bitmap realImage;
    CardView editButton;
    ImageView backButton;

    boolean visible=false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        getWindow().setStatusBarColor(getResources().getColor(R.color.display_image_activity_background_color));

            final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitionContainerDisplayActivty);
        init();
        TransitionManager.beginDelayedTransition(transitionsContainer);
        visible = !visible;
        textRecognizerButton.setVisibility(visible ? View.VISIBLE : View.GONE);




        File file = new File(MainActivity.images.get(0).getPath());
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        realImage = bitmap;

        ImageRepository repo = ImageRepository.getInstance();
        repo.setOriginalGreyScaleImage(bitmap);
        repo.setOriginalCropImage(bitmap);


        if(MainActivity.imageFrom.equals("Gallery")) {
            Glide.with(this).load(new File(MainActivity.images.get(0).getPath())).into(displayImage);
        }else {
            Glide.with(this).load(new File(MainActivity.cameraImages.get(0).getPath())).into(displayImage);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageEditingActivity.class);

                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(editButton,"greyButton");
                pair[0] = new Pair<View, String>(textRecognizerButton,"yellowButton");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DisplayImageActivity.this, pair);
                
                startActivityForResult(intent,205,options.toBundle());
                //startActivityForResult(intent,205);
            }
        });


        textRecognizerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TextDisplayActivity.class);
                intent.putExtra("purpose", "recognize");
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(editButton,"greyButton");
                pair[0] = new Pair<View, String>(textRecognizerButton,"yellowButton");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DisplayImageActivity.this, pair);

                startActivity(intent,options.toBundle());

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        finish();
        ImageEditingActivity.editedImage = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 205) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.e("CropImage", "onActivityResult: data" );
            if (resultCode == RESULT_OK) {
                Log.e("CropImage", "onActivityResult: data2" );

                ImageRepository repo = ImageRepository.getInstance();
                if(repo.getImageCropped()) {
                    if(repo.getImageGreyScale()) {
                        displayImage.setImageBitmap(test(repo.getEditedCropImage()));
                        realImage = test(repo.getEditedCropImage());
                    }else {
                        displayImage.setImageBitmap(repo.getEditedCropImage());
                        realImage = repo.getEditedCropImage();
                    }
                }else {
                    if(repo.getImageGreyScale()) {
                        displayImage.setImageBitmap(test(repo.getOriginalCropImage()));
                        realImage = test(repo.getOriginalCropImage());
                    }else {
                        displayImage.setImageBitmap(repo.getOriginalCropImage());
                        realImage = repo.getOriginalCropImage();
                    }
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public static Bitmap test(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
                // use 128 as threshold, above -> white, below -> black
                if (gray > 128) {
                    gray = 255;
                }
                else{
                    gray = 0;
                }
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmOut;
    }


    private void init() {
        editButton = (CardView) findViewById(R.id.edit_layout);
        displayImage = (ImageView) findViewById(R.id.display_image_image_view);
        textRecognizerButton = (CardView) findViewById(R.id.text_recognizer_button);
        progressBar = (ProgressBar) findViewById(R.id.grey_scale_progress_bar);
        progressBar.setVisibility(View.GONE);
        backButton = (ImageView) findViewById(R.id.back_button);
    }
}
