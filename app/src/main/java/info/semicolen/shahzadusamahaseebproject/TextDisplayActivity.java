package info.semicolen.shahzadusamahaseebproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import info.semicolen.shahzadusamahaseebproject.Repository.ImageRepository;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextDisplayActivity extends AppCompatActivity {

    private static final String TAG = "TextDisplayActivity";

    public static TextView textView;
    CardView saveButton, copyButton;
    public static String fileNamestatic;

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_display);
        getWindow().setStatusBarColor(getResources().getColor(R.color.ef_white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();


        Intent intent = getIntent();
        String purpose = intent.getStringExtra("purpose");

        if (purpose.equals("recognize")) {


            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

            //File file = new File(MainActivity.images.get(0).getPath());
            //String filePath = file.getPath();
            //Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            //convertImageToGreyScale(bitmap);

            Frame outputFrame = new Frame.Builder().setBitmap(DisplayImageActivity.realImage).build();

            SparseArray<TextBlock> blocks = textRecognizer.detect(outputFrame);
            String text = "";
            for (int i = 0;i<blocks.size();i++) {

           /* OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, blocks.get(i));
            mGraphicOverlay.add(graphic);*/
                text = text + blocks.get(i).getValue() + "\n\n";
                //TextBlock tblock = graphic.getTextBlock();

                Log.e(TAG, "onActivityResult: " + blocks.get(i).getValue());
            }

            textView.setText(text + "");


            copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("OCR Copy Text", textView.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(TextDisplayActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                }
            });


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Utils().iphoneStyleMessageBox(TextDisplayActivity.this,textView.getText().toString());
                }
            });


        }else {

            String text = intent.getStringExtra("text");
            textView.setText(text);

            copyButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);

        }




    }

    public Bitmap convertImageToGreyScale(Bitmap bmp) {
        Bitmap operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());
        double red = 0.33;
        double green = 0.59;
        double blue = 0.11;

        for (int i = 0; i < bmp.getWidth(); i++) {
            for (int j = 0; j < bmp.getHeight(); j++) {
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);

                r = (int) red * r;
                g = (int) green * g;
                b = (int) blue * b;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        //im.setImageBitmap(operation);
        return operation;
    }

    private void init() {
        textView = (TextView) findViewById(R.id.textView);
        copyButton = (CardView) findViewById(R.id.text_copy_button);
        saveButton = (CardView) findViewById(R.id.text_save_button);
    }
}
