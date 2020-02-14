package info.semicolen.shahzadusamahaseebproject;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerActivity;
import com.esafirm.imagepicker.features.camera.CameraModule;
import com.esafirm.imagepicker.features.camera.DefaultCameraModule;
import com.esafirm.imagepicker.features.camera.OnImageReadyListener;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import info.semicolen.shahzadusamahaseebproject.Adapter.SectionsPagerAdapter;
import info.semicolen.shahzadusamahaseebproject.Fragments.FilesFragment;
import info.semicolen.shahzadusamahaseebproject.Fragments.HomeFragment;

import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static int REQUEST_C0DE_IMAGE_PICKER_GALLERY = 1;
    public static int REQUEST_C0DE_IMAGE_CAMERA = 1;

    public static ArrayList<Image> images;
    public static List<Image> cameraImages;
    public static String imageFrom;
    DefaultCameraModule cameraModule;

    //Views
    Button openFilesButton;
    Button pickFromGallery, captureImage;

    public static Activity context;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static SectionsPagerAdapter adapter;

    public static HomeFragment homeFragment;
    public static FilesFragment filesFragment;
    public static int CURRENT_FRAGMENT = 0;
    public static String FILE_ADDED_NAME = "";

    ImageView logo;

    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(501);//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));


        logo = (ImageView) findViewById(R.id.logoMainActivity);
        setAnimation(logo);

        TedPermission.with(this).setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                context = MainActivity.this;
                adapter = new SectionsPagerAdapter(getSupportFragmentManager());
                viewPager = (ViewPager) findViewById(R.id.container);
                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(tabLayout.getContext(),R.color.ApplicationTextColorBlack));
                tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
                //tabLayout.setPadding(15,0,0,0);

                setupViewPager();
                viewPager.setOffscreenPageLimit(-1);


            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Required to Perform Required Operation...", Toast.LENGTH_SHORT).show();
            }
        }).setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        ).check();



    }

    public static void RemovingFragmentsFromAdaptor() {
        adapter.removeFragment(homeFragment);
        adapter.removeFragment(filesFragment);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    public static void setupViewPager() {

        homeFragment = new HomeFragment();
        filesFragment = new FilesFragment();

        adapter.addFragment(homeFragment);
        adapter.addFragment(filesFragment);

        //container from centerViewPager Activity
        viewPager.setAdapter(adapter);

        tabLayout.setTabTextColors(R.color.ApplicationTextColorBlack , R.color.ApplicationTextColorBlack);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Home");
        tabLayout.getTabAt(1).setText("Files");

        viewPager.setCurrentItem(CURRENT_FRAGMENT);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_C0DE_IMAGE_PICKER_GALLERY && resultCode == RESULT_OK && data != null) {
            images = (ArrayList<Image>) ImagePicker.getImages(data);


            startActivity(new Intent(getApplicationContext(), DisplayImageActivity.class));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("LifeCycle", "onPause:" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LifeCycle", "onDestroy: ");
        RemovingFragmentsFromAdaptor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageEditingActivity.editedImage = null;
    }
}
