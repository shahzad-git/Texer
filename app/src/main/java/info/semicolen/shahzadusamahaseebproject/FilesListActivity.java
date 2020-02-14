package info.semicolen.shahzadusamahaseebproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.semicolen.shahzadusamahaseebproject.Adapter.FilesListAdapter;

import android.os.Bundle;
import android.os.Environment;

import java.io.File;

public class FilesListActivity extends AppCompatActivity {

    RecyclerView filesListRecyclerView;
    FilesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_list);

        String path = Environment.getExternalStorageDirectory() + File.separator + "OCR Data";
        File directory = new File(path);
        File[] files = directory.listFiles();

        filesListRecyclerView = (RecyclerView) findViewById(R.id.filesListRecyclerView);
        filesListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //adapter = new FilesListAdapter(files,getApplicationContext());
        filesListRecyclerView.setAdapter(adapter);
    }
}
