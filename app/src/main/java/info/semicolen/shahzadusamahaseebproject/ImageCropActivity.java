package info.semicolen.shahzadusamahaseebproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import info.semicolen.shahzadusamahaseebproject.Repository.ImageRepository;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class ImageCropActivity extends AppCompatActivity {

    CropImageView imageView;
    CardView cropButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        getWindow().setStatusBarColor(getResources().getColor(R.color.display_image_activity_background_color));

        imageView = (CropImageView) findViewById(R.id.cropImageView);
        cropButton = (CardView) findViewById(R.id.crop_button_crop_activity);

        File file = new File(MainActivity.images.get(0).getPath());
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        //Toast.makeText(this, "Images Size: " + MainActivity.images.size(), Toast.LENGTH_SHORT).show();

        ImageRepository repo = ImageRepository.getInstance();
        if(repo.getImageCropped()) {
            imageView.setImageBitmap(repo.getOriginalCropImage());
        }else {
            imageView.setImageBitmap(repo.getOriginalCropImage());
        }

        /*if(ImageEditingActivity.editedImage == null) {
            Toast.makeText(this, "Original", Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(ImageEditingActivity.originalImage);
        }else {
            Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(ImageEditingActivity.editedImage);
        }*/


        /*CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);*/

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cropped = imageView.getCroppedImage();

                ImageRepository repo = ImageRepository.getInstance();
                if(repo.getImageGreyScale()) {
                    repo.setEditedCropImage(cropped);
                }else {
                    repo.setEditedCropImage(cropped);
                }

                repo.setImageCropped(true);

                Intent intent = new Intent();
                ImageEditingActivity.editedImage = cropped;
                setResult(RESULT_OK,intent);
                onBackPressed();
            }
        });


    }
}
