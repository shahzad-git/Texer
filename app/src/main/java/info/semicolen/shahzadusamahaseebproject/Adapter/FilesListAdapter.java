package info.semicolen.shahzadusamahaseebproject.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import info.semicolen.shahzadusamahaseebproject.MainActivity;
import info.semicolen.shahzadusamahaseebproject.R;
import info.semicolen.shahzadusamahaseebproject.TextDisplayActivity;

import static android.content.Context.CLIPBOARD_SERVICE;

public class FilesListAdapter extends RecyclerView.Adapter<FilesListAdapter.viewHolder> {

    ArrayList<File> files;
    ArrayList<Boolean> expandOrNot;
    Context context;
    private int shortAnimationDuration;
    ArrayList<String> content;
    private int timeBlinking = 5;
    private int lastItemOpened = -1;


    public FilesListAdapter(ArrayList<File> files, ArrayList<Boolean> expandOrNot, Context context) {
        this.files = files;
        this.expandOrNot = expandOrNot;
        this.context = context;
        shortAnimationDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime)+3;
        content = new ArrayList<>();
        for (int i =0;i<files.size();i++) {
            content.add("");
        }
    }


    public String getMonth(int month) {
        String Month = "";
        switch (month) {
            case 0:
                Month = "Jan";
                break;
            case 1:
                Month = "Feb";
                break;
            case 2:
                Month = "Mar";
                break;
            case 3:
                Month = "Apr";
                break;
            case 4:
                Month = "May";
                break;
            case 5:
                Month = "June";
                break;
            case 6:
                Month = "July";
                break;
            case 7:
                Month = "Aug";
                break;
            case 8:
                Month = "Sep";
                break;
            case 9:
                Month = "Oct";
                break;
            case 10:
                Month = "Nov";
                break;
            case 11:
                Month = "Dec";
                break;
            default:
        }
        return Month;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_files_list, parent, false);
        return new viewHolder(view);
    }

    private void visibilityGoneAnimation(final ConstraintLayout recyclerView) {
        recyclerView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recyclerView.setVisibility(View.GONE);
                    }
                });
    }
    private void visibilityBackAnimation(final ConstraintLayout recyclerView) {
        recyclerView.setAlpha(0f);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
    }



    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(400);//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim);
    }

    private void setFileAddedAnimation(View viewToAnimate, int position) {

        ScaleAnimation anim = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(100);//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim);


        // If the bound view wasn't previously displayed on screen, it's animated
        /*ScaleAnimation anim1 = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(200);//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim1);*/

        /*ScaleAnimation anim2 = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim2.setDuration(300);//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim2);*/



    }



    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {


        if (MainActivity.FILE_ADDED_NAME.equals(files.get(position).getName())) {

            new AsyncTask<Void,Void,Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    for (int i = timeBlinking;i>0;i--) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        setFileAddedAnimation(holder.itemView, position);
                    }
                    MainActivity.FILE_ADDED_NAME = "";
                     return null;
                }
            }.execute();


        }
        if (MainActivity.FILE_ADDED_NAME.equals("") && lastItemOpened != position){
            setAnimation(holder.itemView, position);
        }


        if(!expandOrNot.get(position)) {
            holder.expandableLayout.setVisibility(View.GONE);
        }else {
            visibilityBackAnimation(holder.expandableLayout);
        }
        holder.fileText.setText(content.get(position));

        holder.filename.setText(files.get(position).getName());
        //holder.expandableLayout.setVisibility(View.GONE);
        final StringBuilder[] text = new StringBuilder[1];

        Date lastModDate = new Date(files.get(position).lastModified());
        //Log.i("File last modified @ : "+ lastModDate.toString());
        holder.fileCreationDate.setText(lastModDate.getDate() + " " + getMonth(lastModDate.getMonth()));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uri path = Uri.fromFile(files[position]);

                String path = files.get(position).getPath();

                text[0] = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(files.get(position)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text[0].append(line);

                        text[0].append('\n');
                    }
                    Log.e("FileRead", "onClick: " + text[0].toString());
                    br.close();

                    Intent intent = new Intent(context, TextDisplayActivity.class);
                    intent.putExtra("purpose", "fileOpen");
                    intent.putExtra("text", text[0].toString());
                    context.startActivity(intent);


                }
                catch (IOException e) {
                    Log.e("FileRead", "Error: " + e.getMessage());
                }

                /*Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setDataAndType(Uri.parse(path), "text/*");
                try {
                    context.startActivity(pdfOpenintent);

                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);text[0] = new StringBuilder();

                text[0] = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(files.get(position)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text[0].append(line);

                        text[0].append('\n');
                    }
                    Log.e("FileRead", "onClick: " + text[0].toString());
                    br.close();
                }
                catch (IOException e) {
                }

                ClipData clip = ClipData.newPlainText("OCR Copy Text", text[0].toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = -1;
                for (int i = 0;i<files.size();i++) {
                    if(files.get(i).equals(files.get(position))) {
                        item = i;
                    }
                }
                files.get(position).delete();
                files.remove(item);
                notifyItemRemoved(item);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandOrNot.get(position)){

                    if(lastItemOpened != -1) {
                        notifyItemChanged(lastItemOpened);
                    }
                    lastItemOpened = position;

                    text[0] = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(files.get(position)));
                        String line;
                        while ((line = br.readLine()) != null) {
                            text[0].append(line);

                            text[0].append('\n');
                        }
                        Log.e("FileRead", "onClick: " + text[0].toString());
                        br.close();
                    }
                    catch (IOException e) {
                        Log.e("FileRead", "Error: " + e.getMessage());
                    }

                    content.set(position,text[0].toString());
                    notifyItemChanged(position);

                    //holder.expandableLayout.setVisibility(View.VISIBLE);
                    for(int i = 0;i<files.size();i++) {
                        expandOrNot.set(i,false);
                    }
                    expandOrNot.set(position, true);

                }else {
                    lastItemOpened = -1;
                    visibilityGoneAnimation(holder.expandableLayout);
                    expandOrNot.set(position,false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView filename;
        ConstraintLayout expandableLayout;
        ImageView expnadButton, copy, edit, delete;
        TextView fileText, fileCreationDate;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            filename = itemView.findViewById(R.id.filename);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            expnadButton = itemView.findViewById(R.id.expand_button);
            fileText = itemView.findViewById(R.id.recognizedText);
            fileCreationDate = itemView.findViewById(R.id.textView3);

            copy = itemView.findViewById(R.id.button_copy);
            edit = itemView.findViewById(R.id.button_edit);
            delete = itemView.findViewById(R.id.button_delete);

        }
    }
}
