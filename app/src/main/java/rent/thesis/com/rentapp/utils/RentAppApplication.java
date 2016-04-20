package rent.thesis.com.rentapp.utils;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by user on 4/2/2016.
 */
public class RentAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

    }
}
