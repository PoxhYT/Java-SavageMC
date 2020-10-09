package com.rosemite.services.main;

//import com.google.api.core.ApiFuture;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.cloud.FirestoreClient;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainService extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        System.out.println("Subb");
        System.out.println("Subb");
        System.out.println("Subb");
        System.out.println("Subb");
        System.out.println("Subb");

        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("C:\\Users\\Jesse\\Documents\\Dev\\Firebase\\Java-SavageMC\\serviceAccountKey.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        FirebaseOptions options = null;
//        try {
//            options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://java-savagemc.firebaseio.com")
//                    .build();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        FirebaseApp app = FirebaseApp.initializeApp(options);
//
//        Firestore db = FirestoreClient.getFirestore(app);
//
//        DocumentReference docRef = db.collection("cities").document("SF");
//// asynchronously retrieve the document
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//// ...
//// future.get() blocks on response
//        DocumentSnapshot document = null;
//        try {
//            document = future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        if (document.exists()) {
//            System.out.println("Document data: " + document.getData());
//        } else {
//            System.out.println("No such document!");
//        }
    }
}
