package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddLabelFragment extends Fragment {
    Button addLabelButton;
    TextView labelNameText;

    LinearLayout labelContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_label, container, false);

        addLabelButton = view.findViewById(R.id.add_label_button);
        labelNameText = view.findViewById(R.id.label_name);
        labelContainer = view.findViewById(R.id.label_container_addlabelfrg);
        Manager.db.addLabelsToContainer(this, null, null);

        addLabelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLabelsToContainer(labelNameText.getText().toString(), true);
            }
        });
        return view;
    }

    public void addLabelsToContainer(String text, boolean shouldWriteToDb){
        if(text.equals(""))
            return;

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        linearLayout.setLayoutParams(layoutParams);

        ImageView imageView = new ImageView(getContext());
        ViewGroup.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageResource(R.drawable.baseline_label_24);

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams3.setMargins(10,0,0,0);
        textView.setLayoutParams(layoutParams3);
        textView.setText(text);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        labelContainer.addView(linearLayout);

        if(shouldWriteToDb){
            Manager.db.addLabelToDb(text);
        }
    }

}