package psb.thesis.com.rentapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder> {

    private Activity activity;
    private LayoutInflater inflater;

    public SettingsAdapter(Activity activity, LayoutInflater inflater) {
        this.activity = activity;
        this.inflater = inflater;
    }

    @Override
    public SettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SettingsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SettingsHolder extends RecyclerView.ViewHolder {

        private ImageView mIcon;
        private TextView mTitle;

        public SettingsHolder(View itemView) {
            super(itemView);
        }
    }
}
