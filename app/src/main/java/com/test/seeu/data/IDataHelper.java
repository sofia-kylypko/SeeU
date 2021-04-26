package com.test.seeu.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.StorageReference;

public interface IDataHelper {
    CollectionReference getData(String a);
    StorageReference getReference(String url);
}
