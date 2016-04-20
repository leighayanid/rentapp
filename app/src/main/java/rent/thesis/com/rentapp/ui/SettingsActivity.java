package rent.thesis.com.rentapp.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import rent.thesis.com.rentapp.R;
import rent.thesis.com.rentapp.utils.Utils;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Utils.setEntryActivityAnimation(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void finish() {
        super.finish();
        Utils.setExitActivityAnimation(this);
    }
}
