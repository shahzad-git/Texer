package info.semicolen.shahzadusamahaseebproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.esafirm.imagepicker.features.ImagePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import info.semicolen.shahzadusamahaseebproject.MainActivity;
import info.semicolen.shahzadusamahaseebproject.R;

import static info.semicolen.shahzadusamahaseebproject.MainActivity.REQUEST_C0DE_IMAGE_PICKER_GALLERY;

public class HomeFragment extends Fragment {

    ConstraintLayout chooseImageLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_home_fragment, container, false);
        init(view);
        setOnClickListener();

        return view;
    }

    private void init(View view) {
        chooseImageLayout = (ConstraintLayout) view.findViewById(R.id.chooseImageLayout);
    }

    private void setOnClickListener() {
        chooseImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.imageFrom = "Gallery";
                ImagePicker.create(MainActivity.context)
                        .returnAfterFirst(true) // set whether pick or camera action should return immediate result or not. For pick image only work on single mode
                        .folderMode(true) // folder mode (false by default)
                        .folderTitle("Folder") // folder selection title
                        .imageTitle("Tap to select") // image selection title
                        .single() // single mode
                        .theme(R.style.AppTheme)
                        .limit(1) // max images can be selected (99 by default)
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image ("Camera" folder by default)
                        .origin(MainActivity.images) // original selected images, used in multi mode
                        .start(REQUEST_C0DE_IMAGE_PICKER_GALLERY); // start image picker activity with request code
            }
        });
    }


}
