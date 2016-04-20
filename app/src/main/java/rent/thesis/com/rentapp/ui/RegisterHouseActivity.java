package rent.thesis.com.rentapp.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rent.thesis.com.rentapp.R;
import rent.thesis.com.rentapp.model.HouseProperty;
import rent.thesis.com.rentapp.utils.Constant;
import rent.thesis.com.rentapp.utils.Utils;

public class RegisterHouseActivity extends BaseActivity {

    private Firebase businessRef;
    private Dialog dialog;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.companyName)
    EditText mCompanyName;
    @Bind(R.id.completeAddress)
    EditText mCompleteAddress;
    @Bind(R.id.contactNo)
    EditText mContactNo;

    @OnClick(R.id.registerButton)
    void register() {
        dialog = ProgressDialog.show(RegisterHouseActivity.this, "", "Saving..");
        String companyName = mCompanyName.getText().toString().trim();
        String completeAddress = mCompleteAddress.getText().toString().trim();
        String contactNo = mContactNo.getText().toString().trim();

        businessRef = new Firebase(Constant.FIREBASE_URL).child(Constant.USERS);
        HouseProperty houseProperty = new HouseProperty();
        houseProperty.setCompanyName(companyName);
        houseProperty.setCompanyAddress(completeAddress);
        houseProperty.setContact(contactNo);
        businessRef.push().setValue(houseProperty, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    dialog.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Saved!", Snackbar.LENGTH_LONG).show();
                } else {
                    dialog.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Something went wrong!" + firebaseError.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_house);
        Utils.setEntryActivityAnimation(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void finish() {
        super.finish();
        Utils.setExitActivityAnimation(this);
    }
}
