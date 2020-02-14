package info.semicolen.shahzadusamahaseebproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.cardview.widget.CardView;

public class Utils {

    public void iphoneStyleMessageBox(final Activity context, final String text) {


        try {
            final Dialog messageDialog = new Dialog(context);
            messageDialog.setContentView(R.layout.dialog_iphone_style_message_box);
            messageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final EditText tv_message = messageDialog.findViewById(R.id.message);

            CardView ok_button = messageDialog.findViewById(R.id.dismiss_button);
            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    writeToFile(tv_message.getText().toString(), context, text);
                    messageDialog.dismiss();
                    MainActivity.RemovingFragmentsFromAdaptor();
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    MainActivity.CURRENT_FRAGMENT = 1;
                    MainActivity.FILE_ADDED_NAME = tv_message.getText().toString()+".txt";

                    context.startActivity(intent);


                }
            });

            messageDialog.setCancelable(false);
            messageDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, Context context, String text) {
        try {
            String path = Environment.getExternalStorageDirectory() + "/OCR Data";
            File folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "OCR Data");
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                // Do something on success
                Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
            } else {
                // Do something else on failure
                Toast.makeText(context, "Not Created", Toast.LENGTH_SHORT).show();
            }
            File file = new File(path, fileName + ".txt");

            FileOutputStream stream = new FileOutputStream(file);
            stream.write(text.getBytes());
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
