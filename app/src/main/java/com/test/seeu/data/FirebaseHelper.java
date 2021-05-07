package com.test.seeu.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper implements IDataHelper {
    private static FirebaseHelper instance;
    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private FirebaseHelper() {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
    }

    public static FirebaseHelper getInstance(){
        if (instance == null){
            instance = new FirebaseHelper();
        }
        return instance;
    }

    @Override
    public CollectionReference getData(String a) {
        return db.collection(a);
    }

    @Override
    public DocumentReference getDataById(String parent, String key) {
        return db.collection(parent).document(key);

    }

    @Override
    public StorageReference getReference(String url) {
        return firebaseStorage.getReferenceFromUrl(url);
    }
}
