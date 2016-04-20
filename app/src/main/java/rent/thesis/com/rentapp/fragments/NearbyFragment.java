package rent.thesis.com.rentapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rent.thesis.com.rentapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends BaseFragment {


    public NearbyFragment() {
        // Required empty public constructor
    }

    public static NearbyFragment newInstance(int instance) {

        Bundle args = new Bundle();

        NearbyFragment fragment = new NearbyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

}
