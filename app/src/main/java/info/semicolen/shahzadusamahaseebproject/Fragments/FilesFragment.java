package info.semicolen.shahzadusamahaseebproject.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicIntegerArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.semicolen.shahzadusamahaseebproject.Adapter.FilesListAdapter;
import info.semicolen.shahzadusamahaseebproject.R;

public class FilesFragment extends Fragment {

    RecyclerView filesListRecyclerView;
    public static FilesListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_files_fragment, container, false);

        filesListRecyclerView = (RecyclerView) view.findViewById(R.id.filesListRecyclerView);
        filesListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                filesListRecyclerView.setAdapter(adapter);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String path = Environment.getExternalStorageDirectory() + File.separator + "OCR Data";
                File directory = new File(path);
                File[] files = directory.listFiles();
                ArrayList<Boolean> expandOrNot = new ArrayList<>();
                final ArrayList<File> files1  = new ArrayList<>();
                for (int i = 0;i<files.length; i++) {
                    expandOrNot.add(false);
                    files1.add(files[i]);
                }


                Collections.sort(files1, new Comparator<File>() {

                    @Override
                    public int compare(File file1, File file2) {
                        long k = file1.lastModified() - file2.lastModified();
                        if(k < 0){
                            return 1;
                        }else if(k == 0){
                            return 0;
                        }else{
                            return -1;
                        }
                    }
                });

                adapter = new FilesListAdapter(files1, expandOrNot,getContext());

                return null;
            }
        }.execute();

        return view;
    }


}
