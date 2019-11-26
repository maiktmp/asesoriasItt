package com.maik.greenhouse.FB;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.maik.greenhouse.models.GreenHouse;
import com.maik.greenhouse.callbacks.CBGeneric;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FBInteractors {
    private final String TAG = "FB_INTERACTOR";
    private static FBInteractors instance;

    private String user = "maiktmp@gmail.com";
    private String collection = "1";

    private FirebaseFirestore db;


    public static FBInteractors getInstance() {
        instance = instance == null ? new FBInteractors() : instance;
        return instance;
    }

    public FBInteractors() {
        db = FirebaseFirestore.getInstance();

    }

    public void getGreenHouse(CBGeneric<GreenHouse> cb) {
        DocumentReference docRef = db
                .collection(this.user)
                .document(this.collection);


        exec(docRef, map -> {
            GreenHouse greenHouse = new GreenHouse();
            greenHouse.setName((String) map.get("name"));
            greenHouse.setImg((ArrayList<String>) map.get("img"));
            greenHouse.setDescription((String) map.get("description"));
            greenHouse.setLimit((Long) map.get("limit"));
            greenHouse.setAverage((Map) map.get("average"));
            cb.onResult(greenHouse);

        });
    }


    private void exec(DocumentReference docRef, CBGeneric<Map<String, Object>> cb) {
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    cb.onResult(document.getData());
                } else {
                    cb.onResult(null);
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }


    public void update(GreenHouse greenHouse, CBGeneric<Boolean> cb) {
        DocumentReference docRef = db
                .collection(this.user)
                .document(this.collection);
        docRef.update(
                "name", greenHouse.getName(),
                "description", greenHouse.getDescription(),
                "average", greenHouse.getAverage(),
                "limit", greenHouse.getLimit())
                .addOnSuccessListener(aVoid -> cb.onResult(true))
                .addOnFailureListener(e -> cb.onResult(false));
    }

    public void updateImage(String name, byte[] bytes, CBGeneric<Boolean> cb) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef
                .child(name)
                .putBytes(bytes)
                .addOnFailureListener(e -> {
                    cb.onResult(false);
                })
                .addOnSuccessListener(taskSnapshot -> {
                    cb.onResult(true);
                });

    }

    //    public void getUriFile(String file, CBGeneric<String> cb) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        storageRef.child(file).getDownloadUrl().addOnSuccessListener(
//                uri -> {
//                    cb.onResult(uri);
//                })
//                .addOnFailureListener(exception -> {
//
//                });
//
//    }

}
