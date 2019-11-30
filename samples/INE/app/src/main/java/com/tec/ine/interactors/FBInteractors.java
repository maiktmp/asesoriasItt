package com.tec.ine.interactors;

import android.app.Activity;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tec.ine.models.INE;
import com.tec.ine.utils.CBGeneric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FBInteractors {

    private final String TAG = "FB_INTERACTOR";
    private static FBInteractors instance;
    private FirebaseStorage storage;

    private String collection = "ine";


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public static String ADMIN = "desmovil@admin.com";
    public static String CONSULTOR = "desmovil@consultor.com";


    public static FBInteractors getInstance() {
        instance = instance == null ? new FBInteractors() : instance;
        return instance;
    }

    public FBInteractors() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void getAuthUser(CBGeneric<FirebaseUser> cb) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        cb.onResult(currentUser);
    }

    public void signUp(Activity activity, String email, String password, CBGeneric<FirebaseUser> cb) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        getAuthUser(cb);
                    } else {
                        cb.onResult(null);
                    }
                });
    }

    public void getAll(Boolean active, CBGeneric<List<INE>> cb) {
        CollectionReference collectionReference = db.collection(this.collection);
        Query query = collectionReference;

        if (active != null) {
            query = collectionReference
                    .whereEqualTo("active", active);
        }

        exec(query.get(),
                result -> {
                    List<INE> ines = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : result.entrySet()) {
                        INE ine = INE.toINE((Map<String, Object>) entry.getValue());
                        ines.add(ine);
                    }
                    cb.onResult(ines);
                }
        );

    }

    public void getINE(String id, CBGeneric<INE> cb) {
        DocumentReference docRef = db
                .collection(this.collection)
                .document(id);

        exec(docRef, map -> cb.onResult(INE.toINE(map)));
    }

    public void uploadImage(String name, byte[] bytes, CBGeneric<Boolean> cb) {
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


    public void searchINE(String field, String search, CBGeneric<List<INE>> cb) {
        CollectionReference collectionReference = db.collection(this.collection);

        exec(collectionReference
                        .whereEqualTo(field, search)
                        .get(),
                result -> {
                    List<INE> ines = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : result.entrySet()) {
                        INE ine = INE.toINE((Map<String, Object>) entry.getValue());
                        ines.add(ine);
                    }
                    cb.onResult(ines);
                }
        );

    }

    public void createINE(INE ine, CBGeneric<Boolean> cb) {
        db
                .collection(this.collection)
                .add(ine.toMap())
                .addOnSuccessListener(documentReference -> {
                    cb.onResult(true);
                })
                .addOnFailureListener(e -> {
                    cb.onResult(false);
                });
    }

    public void updateINE(INE ine, CBGeneric<Boolean> cb) {
        db
                .collection(this.collection)
                .document(ine.getFBID())
                .update(ine.toMap())
                .addOnSuccessListener(documentReference -> {
                    cb.onResult(true);
                })
                .addOnFailureListener(e -> {
                    cb.onResult(false);
                });
    }

    private void exec(DocumentReference docRef, CBGeneric<Map<String, Object>> cb) {
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    document.getData().put("FB_ID", document.getId());
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


    private void exec(CollectionReference collectionReference, CBGeneric<Map<String, Object>> cb) {
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> map = new ArrayMap<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    map.put(document.getId(), document.getData());
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }
                cb.onResult(map);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    private void exec(Task<QuerySnapshot> querySnapshotTask, CBGeneric<Map<String, Object>> cb) {
        querySnapshotTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> map = new ArrayMap<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> data = document.getData();
                    data.put("FB_ID", document.getId());
                    map.put(document.getId(), data);
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }
                cb.onResult(map);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }


}
