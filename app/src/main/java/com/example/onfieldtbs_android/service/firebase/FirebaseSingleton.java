package com.example.onfieldtbs_android.service.firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseSingleton {
    public static FirebaseStorage storage = FirebaseStorage.getInstance();

    public FirebaseSingleton(){}

    public static FirebaseStorage getInstance(){
        return storage;
    }

    public static StorageReference getReference(String location){
        return getInstance().getReference(location);
    }
}
