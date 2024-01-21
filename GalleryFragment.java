package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    Spinner spinner;

    LinearLayout galleryContainer;

    Button filterApproveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        spinner = view.findViewById(R.id.spinner);
        Manager.db.addLabelsToContainer(null, null, this);

        galleryContainer = view.findViewById(R.id.gallery_container);
        Manager.db.addLabelsToGallery(this, null);

        filterApproveButton = view.findViewById(R.id.filter_approve_button);
        filterApproveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryContainer.removeAllViews();
                Manager.db.addLabelsToGallery(GalleryFragment.this, spinner.getSelectedItem().toString());
            }
        });

        return view;
    }

    public void addLabelsToSpinner(ArrayList<String> labels) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, labels);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void addGalleryItemToContainer(String text, String name){
        LinearLayout linearLayout1 = new LinearLayout(getContext());
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setBackgroundResource(R.drawable.border_shape);
        linearLayout1.setMinimumHeight(180);
        ViewGroup.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        linearLayout1.setLayoutParams(layoutParams1);

        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        linearLayout2.setPadding(10,10,10,10);
        ViewGroup.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        linearLayout2.setLayoutParams(layoutParams2);

        ImageView imageView = new ImageView(getContext());
        imageView.setAdjustViewBounds(true);
        imageView.setImageResource(R.drawable.resim);
        LinearLayout.LayoutParams imageViewsParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(imageViewsParams);

        LinearLayout linearLayout3 = new LinearLayout(getContext());
        linearLayout3.setOrientation(LinearLayout.VERTICAL);
        linearLayout3.setPadding(10,0,10,0);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        linearLayout3.setLayoutParams(layoutParams3);

        LinearLayout linearLayout4 = new LinearLayout(getContext());
        linearLayout4.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1
        );
        linearLayout4.setLayoutParams(layoutParams4);

        TextView textView1 = new TextView(getContext());
        textView1.setTextSize(20);
        textView1.setText(name);
        LinearLayout.LayoutParams textViewParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView1.setLayoutParams(textViewParams1);

        TextView textView2 = new TextView(getContext());
        textView2.setText(text);
        LinearLayout.LayoutParams textViewParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView2.setLayoutParams(textViewParams2);

        LinearLayout linearLayout5 = new LinearLayout(getContext());
        linearLayout5.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout5.setGravity(Gravity.BOTTOM);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1
        );
        linearLayout5.setLayoutParams(layoutParams5);

        ImageView imageView1 = new ImageView(getContext());
        imageView1.setImageResource(R.drawable.baseline_sentiment_satisfied_alt_24);
        LinearLayout.LayoutParams imageView1Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                120,
                1
        );
        imageView1.setLayoutParams(imageView1Params);

        ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageResource(R.drawable.baseline_sentiment_very_dissatisfied_24);
        LinearLayout.LayoutParams imageView2Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                120,
                1
        );
        imageView2.setLayoutParams(imageView2Params);

        linearLayout5.addView(imageView1);
        linearLayout5.addView(imageView2);

        linearLayout4.addView(textView1);
        linearLayout4.addView(textView2);

        linearLayout3.addView(linearLayout4);
        linearLayout3.addView(linearLayout5);

        linearLayout2.addView(imageView);

        linearLayout1.addView(linearLayout2);
        linearLayout1.addView(linearLayout3);

        galleryContainer.addView(linearLayout1);
    }


}