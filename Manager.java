package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Manager {

    public interface State{
        void onStateResult(boolean result);
    }

    FirebaseFirestore firestore;

    public static Manager db;

    private String userId;

    public Manager(){
        firestore = FirebaseFirestore.getInstance();
        db = this;
    }

    public void signup(String name, String lastName, String email, String password, final State state){
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("lastName", lastName);
        user.put("email", email);
        user.put("password", password);

        firestore.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                state.onStateResult(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                state.onStateResult(false);
            }
        });
    }

    public void signin(String email, String password, final State state){
        firestore.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().isEmpty()){
                        state.onStateResult(false);
                        return;
                    }

                    boolean hasFoundAny = false;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        if(Objects.equals(document.getData().get("email"), email) &&
                                Objects.equals(document.getData().get("password"), password)){
                            state.onStateResult(true);
                            userId = document.getId();
                            hasFoundAny = true;
                            break;
                        }
                    }
                    if(!hasFoundAny){
                        state.onStateResult(false);
                    }


                }else {
                    state.onStateResult(false);
                }
            }
        });
    }

    public void addLabelToDb(String text){
        DocumentReference userRef = firestore.collection("user").document(userId);

        userRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    ArrayList<String> currentLabels = (ArrayList<String>) document.get("labels");

                    if(currentLabels == null)
                        currentLabels = new ArrayList<>();

                    currentLabels.add(text);

                    HashMap<String, Object> updatedLabels = new HashMap<>();
                    updatedLabels.put("labels", currentLabels);

                    userRef.update(updatedLabels);
                }
            }
        });


    }

    public void addLabelsToContainer(AddLabelFragment obj, AddPhotoFragment obj2, GalleryFragment obj3){
        DocumentReference userRef = firestore.collection("user").document(userId);

        userRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    ArrayList<String> currentLabels = (ArrayList<String>) document.get("labels");
                    if(currentLabels != null && currentLabels.size() != 0){
                        if(obj3 != null){
                            obj3.addLabelsToSpinner(currentLabels);
                        }else {
                            for(String item : currentLabels){
                                if(obj != null)
                                    obj.addLabelsToContainer(item, false);
                                else if(obj2 != null)
                                    obj2.addLabelsToContainer(item);
                            }
                        }
                    }

                }
            }
        });
    }

    public void addLabelsForPhotoToDb(AddPhotoFragment obj){
        ArrayList<String> selectedLabels = obj.getLabelsSelected();

        if(!selectedLabels.isEmpty()){
            DocumentReference userRef = firestore.collection("user").document(userId);

            userRef.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        ArrayList<String> currentLabels = (ArrayList<String>) document.get("labelsForPhoto");

                        if(currentLabels == null)
                            currentLabels = new ArrayList<>();

                        selectedLabels.add("-");

                        currentLabels.addAll(selectedLabels);

                        HashMap<String, Object> updatedLabels = new HashMap<>();
                        updatedLabels.put("labelsForPhoto", currentLabels);

                        userRef.update(updatedLabels);
                    }
                }
            });
        }
    }

    public void addLabelsToGallery(GalleryFragment obj, String filter){
        DocumentReference userRef = firestore.collection("user").document(userId);

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ArrayList<String> currentLabels = (ArrayList<String>) document.get("labelsForPhoto");
                    String name = (String) document.get("name");

                    if (currentLabels != null && currentLabels.size() != 0) {

                        StringBuilder text = new StringBuilder();

                        for (String item : currentLabels) {
                            if(item.equals("-")){
                                if(filter != null){
                                    if(text.toString().contains(filter)){
                                        obj.addGalleryItemToContainer(text.toString(), name);
                                    }
                                }else {
                                    obj.addGalleryItemToContainer(text.toString(), name);
                                }
                                text.setLength(0);
                            }else {
                                text.append(item);
                                text.append(" ");
                            }
                        }
                    }

                }
            }
        });

    }

    public void addPhotoToDb(Bitmap bitmap, AddPhotoFragment obj){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        ArrayList<Long> byteList = new ArrayList<>();
        for (byte b : data) {
            byteList.add((long) b);
        }

        HashMap<String, Object> dataV = new HashMap<>();
        dataV.put("userId", userId);
        dataV.put("resim", byteList);


        firestore.collection("user2").add(dataV).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                obj.Read(e.toString());
            }
        });

    }






}
