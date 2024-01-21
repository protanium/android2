package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AddPhotoFragment extends Fragment {

    LinearLayout labelContainer;

    Button cameraButton;
    Button cameraApproveButton;

    ImageView cameraPhoto;

    private Uri imageUrl;

    private ActivityResultLauncher<Uri> contract = registerForActivityResult(new ActivityResultContracts.TakePicture(), result->{
        cameraPhoto.setImageURI(null);
        cameraPhoto.setImageURI(imageUrl);
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_photo, container, false);

        labelContainer = view.findViewById(R.id.label_container_addphotofrg);
        Manager.db.addLabelsToContainer(null, this, null);

        cameraButton = view.findViewById(R.id.camera_button);

        imageUrl = createImageUri();
        cameraPhoto = view.findViewById(R.id.camera_photo);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contract.launch(imageUrl);
            }
        });

        cameraApproveButton = view.findViewById(R.id.camera_approve_button);
        cameraApproveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Manager.db.addLabelsForPhotoToDb(AddPhotoFragment.this);

                if(cameraPhoto.getDrawable() != null){
                    BitmapDrawable drawable = (BitmapDrawable) cameraPhoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    Manager.db.addPhotoToDb(bitmap, AddPhotoFragment.this);
                }


            }
        });




        return view;
    }

    public void Read(String text){
        Log.d("sanane", text);
    }
    private Uri createImageUri(){
        File image = new File(getContext().getFilesDir(), "camera-photos.png");
        return FileProvider.getUriForFile(getContext(), "com.example.myapplication.FileProvider", image);
    }

    public void addLabelsToContainer(String text){
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.setLayoutParams(layoutParams);

        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageResource(R.drawable.baseline_label_24);

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                6
        );
        textView.setLayoutParams(layoutParams3);
        textView.setText(text);

        RadioButton radioButton = new RadioButton(getContext());
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(
                0,
                50,
                1
        );
        radioButton.setLayoutParams(layoutParams4);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.addView(radioButton);

        labelContainer.addView(linearLayout);
    }

    public ArrayList<String> getLabelsSelected(){
        ArrayList<String> selectedLabels = new ArrayList<>();

        if(cameraPhoto.getDrawable() != null){
            int i = 0;
            while(i < labelContainer.getChildCount()){

                LinearLayout linearLayout = (LinearLayout) labelContainer.getChildAt(i);

                TextView textView = (TextView) linearLayout.getChildAt(1);
                RadioButton radioButton = (RadioButton) linearLayout.getChildAt(2);

                if(radioButton.isChecked()){
                    selectedLabels.add(textView.getText().toString());
                    radioButton.setChecked(false);
                }
                i++;
            }
        }else {
            Toast.makeText(getContext(), "Fotoğraf Ekleyiniz", Toast.LENGTH_SHORT).show();
        }

        return selectedLabels;
    }


}