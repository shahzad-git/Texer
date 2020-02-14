package info.semicolen.shahzadusamahaseebproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    LinearLayout background;

    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(501);//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setStatusBarColor(getResources().getColor(R.color.ApplicationMainYellow));

        imageView = (ImageView) findViewById(R.id.appplicationLogo);
        background = (LinearLayout) findViewById(R.id.backgroundSplash);
        setAnimation(imageView);


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);

                Pair[] pair = new Pair[2];
                pair[0] = new Pair<View, String>(imageView,"logoTransition");
                pair[1] = new Pair<View, String>(background,"backgroundTransition");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pair);

                startActivity(intent,options.toBundle());

                finish();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }
}
