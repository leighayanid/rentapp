package rent.thesis.com.rentapp.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rent.thesis.com.rentapp.MainActivity;
import rent.thesis.com.rentapp.R;
import rent.thesis.com.rentapp.model.User;
import rent.thesis.com.rentapp.utils.Constant;

public class RegisterActivity extends BaseActivity {

    private Firebase ref;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.username)
    EditText mUsername;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.rePassword)
    EditText mPassword2;
    @Bind(R.id.hasBusiness)
    CheckBox mHasBusiness;
    @Bind(R.id.normalUser)
    CheckBox mNormalUser;

    private Dialog dialog;
    private String username, email, password, password2;
    private boolean hasBusiness;

    @OnClick(R.id.registerButton)
    void submit() {

        dialog = ProgressDialog.show(RegisterActivity.this, "", "Processing registration..");
        username = mUsername.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        password2 = mPassword2.getText().toString().trim();

        //TODO ADD VALIDATIONS HERE
        if (mHasBusiness.isChecked()) {
            hasBusiness = true;
        } else {
            hasBusiness = false;
        }

        if (!password.equals(password2)) {
            Snackbar.make(null, "Password did not match.", Snackbar.LENGTH_LONG).show();
        } else {
            ref = new Firebase(Constant.FIREBASE_URL);
            ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    Log.i("Success", "Success creating user.");
                    dialog.dismiss();
                }

                @Override
                public void onError(FirebaseError firebaseError) {

                }
            });

            ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {

                    User user = new User();
                    user.setEmailAddress(email);
                    user.setUsername(username);
                    user.setProvider(authData.getProvider());
                    user.setHasBusiness(hasBusiness);
                    ref.child(Constant.USERS).child(authData.getUid()).setValue(user);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    //todo add validations here for inputs
                    switch (firebaseError.getCode()) {
                        case FirebaseError.INVALID_EMAIL:
                            Snackbar.make(null, "Invalid email address.", Snackbar.LENGTH_SHORT).show();
                            break;
                        case FirebaseError.DISCONNECTED:
                            Snackbar.make(null, "No internet connection.", Snackbar.LENGTH_SHORT).show();
                            break;
                        case FirebaseError.EMAIL_TAKEN:
                            Snackbar.make(null, "Email address already taken.", Snackbar.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
