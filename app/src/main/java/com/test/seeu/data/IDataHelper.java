package com.test.seeu.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;

public interface IDataHelper {
    CollectionReference getData(String a);
    DocumentReference getDataById(String parent, String key);
    StorageReference getReference(String url);
}
