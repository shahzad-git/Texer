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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.TransitionManager;

import java.io.File;

public class ImageEditingActivity extends AppCompatActivity {

    CardView cropButton;
    Switch greyScaleSwitch;
    ImageView imageView, backButton;
    CardView doneButton;

    public static Bitmap originalImage;
    public static Bitmap editedImage;

    boolean isReturnAnimation = false;
    boolean visible=false;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
        editedImage = null;
        //MainActivity.images.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editing);
        getWindow().setStatusBarColor(getResources().getColor(R.color.display_image_activity_background_color));

        init();
        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitionContainerEditingActivty);

        TransitionManager.beginDelayedTransition(transitionsContainer);
        visible = !visible;
        doneButton.setVisibility(visible ? View.VISIBLE : View.GONE);



        File file = new File(MainActivity.images.get(0).getPath());

        ImageRepository repo = ImageRepository.getInstance();
        if(repo.getImageCropped()) {
            if(repo.getImageGreyScale()) {
                imageView.setImageBitmap(test(repo.getEditedCropImage()));
            }else {
                imageView.setImageBitmap(repo.getEditedCropImage());
            }
        }else {
            if(repo.getImageGreyScale()) {
                imageView.setImageBitmap(test(repo.getOriginalCropImage()));
            }else {
                imageView.setImageBitmap(repo.getOriginalCropImage());
            }
        }


        if (repo.getImageGreyScale()) {
            greyScaleSwitch.setChecked(true);
        }else {
            greyScaleSwitch.setChecked(false);
        }


        greyScaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    ImageRepository repo = ImageRepository.getInstance();
                    if(repo.getImageCropped()) {
                        repo.setEditedGreyScaleImage(test(repo.getEditedCropImage()));
                    }else {
                        repo.setEditedGreyScaleImage(test(repo.getOriginalCropImage()));
                    }
                    imageView.setImageBitmap(repo.getEditedGreyScaleImage());
                    repo.setImageGreyScale(true);


                    /*if(editedImage!= null) {
                        editedImage = test(editedImage);
                    }else {
                        editedImage = test(originalImage);
                    }
                    imageView.setImageBitmap(editedImage);
                    */

                }else {

                    ImageRepository repo = ImageRepository.getInstance();
                    if(repo.getImageCropped()) {
                        repo.setOriginalGreyScaleImage(repo.getEditedCropImage());
                    }else {
                        repo.setOriginalGreyScaleImage(repo.getOriginalCropImage());
                    }
                    imageView.setImageBitmap(repo.getOriginalGreyScaleImage());
                    repo.setImageGreyScale(false);


/*                    if(editedImage!= null) {
                        imageView.setImageBitmap(editedImage);
                    }else {
                        editedImage = originalImage;
                    }
                    editedImage = null;
                    imageView.setImageBitmap(originalImage);*/
                }
            }
        });

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ImageCropActivity.class);

                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(cropButton,"greyButton");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ImageEditingActivity.this, pair);

                startActivityForResult(intent,203,options.toBundle());
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                DisplayImageActivity.realImage = editedImage;
                setResult(RESULT_OK,intent);
                onBackPressed();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void init() {
        doneButton = (CardView) findViewById(R.id.done_button);
        imageView = (ImageView) findViewById(R.id.imageView);
        backButton = (ImageView) findViewById(R.id.back_button_editing_activity);
        cropButton = (CardView) findViewById(R.id.crop_button);
        greyScaleSwitch = (Switch) findViewById(R.id.grey_scale_switch);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 203) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.e("CropImage", "onActivityResult: data" );
            if (resultCode == RESULT_OK) {
                Log.e("CropImage", "onActivityResult: data2" );



                ImageRepository repo = ImageRepository.getInstance();
                if(repo.getImageCropped()) {
                    if(repo.getImageGreyScale()) {
                        imageView.setImageBitmap(test(repo.getEditedCropImage()));
                    }else {
                        imageView.setImageBitmap(repo.getEditedCropImage());
                    }
                }else {
                    if(repo.getImageGreyScale()) {
                        imageView.setImageBitmap(test(repo.getOriginalCropImage()));
                    }else {
                        imageView.setImageBitmap(repo.getOriginalCropImage());
                    }
                }

                visible = false;
                final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitionContainerEditingActivty);
                TransitionManager.beginDelayedTransition(transitionsContainer);
                visible = !visible;
                imageView.setVisibility(visible ? View.VISIBLE : View.GONE);




                //imageView.setImageBitmap(editedImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}
