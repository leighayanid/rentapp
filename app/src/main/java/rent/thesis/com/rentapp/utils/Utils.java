package rent.thesis.com.rentapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.InputFilter;
import android.text.Spanned;

import rent.thesis.com.rentapp.R;

public class Utils {

    //check if network connection is available
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            return true;
        } else {
            return false;
        }
    }

    //input filter to avoid emojies inside edittext
    public static InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

    public static void setEntryActivityAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }

    public static void setExitActivityAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
    }

}
