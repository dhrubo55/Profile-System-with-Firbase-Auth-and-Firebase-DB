package fireBase;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dhrubo Android on 6/24/2018.
 */
class MyFirebaseApp extends android.app.Application{

        @Override
        public void onCreate() {
            super.onCreate();
    /* Enable disk persistence  */
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
}