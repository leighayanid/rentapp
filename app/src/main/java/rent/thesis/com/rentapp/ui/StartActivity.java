package rent.thesis.com.rentapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rent.thesis.com.rentapp.MainActivity;
import rent.thesis.com.rentapp.R;
import rent.thesis.com.rentapp.utils.Constant;

public class StartActivity extends FirebaseLoginBaseActivity {

    private Firebase ref;
    @Bind(R.id.loginButton)
    Button loginButton;
    @Bind(R.id.registerButton)
    Button registerButton;

    @OnClick(R.id.loginButton)
    void login() {
        showFirebaseLoginPrompt();
    }

    @OnClick(R.id.registerButton)
    void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        setEnabledAuthProvider(AuthProviderType.TWITTER);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        loginButton.getBackground().setAlpha(200);
        registerButton.getBackground().setAlpha(200);

    }

    private void goToMainActivity() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected Firebase getFirebaseRef() {
        return new Firebase(Constant.FIREBASE_URL);
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseError) {

    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {

    }

    @Override
    protected void onFirebaseLoggedIn(AuthData authData) {
        super.onFirebaseLoggedIn(authData);
        goToMainActivity();
    }

    @Override
    protected void onFirebaseLoggedOut() {
        super.onFirebaseLoggedOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
