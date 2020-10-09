import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FirebaseServiceAA {
    public static void mainTest() throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\Jesse\\Documents\\Dev\\Firebase\\Java-SavageMC\\serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public void test() {
        System.out.println("Hii");
    }
}
