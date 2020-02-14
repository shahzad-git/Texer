package info.semicolen.shahzadusamahaseebproject;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GrayScaleEffect extends AppCompatActivity {

        Button b1, b2, b3;
        ImageView im;

        private Bitmap bmp;
        private Bitmap operation;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_image);

            //b1 = (Button) findViewById(R.id.gray);
            im = (ImageView) findViewById(R.id.display_image_image_view);

            BitmapDrawable abmp = (BitmapDrawable) im.getDrawable();
            bmp = abmp.getBitmap();
        }

        public void gray(View view) {
            operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());
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
            im.setImageBitmap(operation);
        }
}
