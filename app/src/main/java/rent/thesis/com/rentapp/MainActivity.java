package rent.thesis.com.rentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import rent.thesis.com.rentapp.fragments.BaseFragment;
import rent.thesis.com.rentapp.fragments.FavoritesFragment;
import rent.thesis.com.rentapp.fragments.FriendsFragment;
import rent.thesis.com.rentapp.fragments.HomeFragment;
import rent.thesis.com.rentapp.fragments.NearbyFragment;
import rent.thesis.com.rentapp.fragments.SearchFragment;
import rent.thesis.com.rentapp.ui.BaseActivity;
import rent.thesis.com.rentapp.ui.NotificationsActivity;
import rent.thesis.com.rentapp.ui.ProfileActivity;
import rent.thesis.com.rentapp.ui.RegisterHouseActivity;
import rent.thesis.com.rentapp.ui.SettingsActivity;
import rent.thesis.com.rentapp.ui.StartActivity;
import rent.thesis.com.rentapp.utils.Constant;
import rent.thesis.com.rentapp.utils.Utils;

public class MainActivity extends BaseActivity implements BaseFragment.FragmentNavigation {

    public static final int PROFILE_SETTING = 1;
    private static final String TAG = "MainActivity";

    //reference to firebase
    private Firebase ref;
    private Query query;

    private BottomBar bottomBar;
    private FragNavController navController;

    private final int INDEX_RECENTS = FragNavController.TAB1;
    // private final int INDEX_SEARCH = FragNavController.TAB2;
    private final int INDEX_NEARBY = FragNavController.TAB2;
    private final int INDEX_FAVORITES = FragNavController.TAB3;
    private final int INDEX_FRIENDS = FragNavController.TAB4;

    private AccountHeader headerResult = null;
    private Drawer drawer = null;

    private IProfile profile;
    private Toolbar toolbar;

    private String userEmail;
    private String userCompleteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.setEntryActivityAnimation(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set reference to firebase
        ref = new Firebase(Constant.FIREBASE_URL);

        //check if user is still logged in
        AuthData authData = ref.getAuth();
        if (authData != null) {
            //user is still logged in
            userEmail = authData.getProviderData().get("email").toString();

        } else {
            Intent intent = new Intent(this, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        //setup views and fragments
        setUpFragments(savedInstanceState);
        setUpMaterialDrawer(savedInstanceState);

    }

    private void setUpMaterialDrawer(Bundle savedInstanceState) {
        String email = null;
        profile = new ProfileDrawerItem().withName("Leigh Dinaya").withEmail(userEmail);
        buildHeader(false, savedInstanceState);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(FontAwesome.Icon.faw_user).withIdentifier(1),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cart_plus),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_database).withEnabled(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_add_property).withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus)).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withEnabled(false)
                )
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        MainActivity.this.finish();
                        return true;
                    }
                })
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(2),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(3)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(MainActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            }
                            if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(intent);
                            }
                            if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(MainActivity.this, RegisterHouseActivity.class);
                                startActivity(intent);
                            }

                            if (drawerItem.getIdentifier() == 3) {
                                if (Utils.isConnectionAvailable(MainActivity.this)) {
                                    logout();
                                } else {
                                    Log.e(TAG, "No internet connection.");
                                }
                            }
                        }
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

    }

    private void logout() {
        ref = new Firebase(Constant.FIREBASE_URL);
        ref.unauth();
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile5));
                            if (headerResult.getProfiles() != null) {
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }


    private void setUpFragments(Bundle savedInstanceState) {
        //create a list of fragments here
        List<Fragment> fragments = new ArrayList<>(5);

        fragments.add(HomeFragment.newInstance(0));
        fragments.add(SearchFragment.newInstance(0));
        fragments.add(NearbyFragment.newInstance(0));
        fragments.add(FavoritesFragment.newInstance(0));
        fragments.add(FriendsFragment.newInstance(0));

        navController = new FragNavController(getSupportFragmentManager(), R.id.container, fragments);

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {
                switch (menuItemId) {
                    case R.id.id1:
                        navController.switchTab(INDEX_RECENTS);
                        break;
                   /* case R.id.id2:
                        navController.switchTab(INDEX_SEARCH);
                        break;*/
                    case R.id.id2:
                        navController.switchTab(INDEX_NEARBY);
                        break;
                    case R.id.id3:
                        navController.switchTab(INDEX_FAVORITES);
                        break;
                    case R.id.id4:
                        navController.switchTab(INDEX_FRIENDS);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(int menuItemId) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_notification) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (navController.getCurrentStack().size() > 1) {
            navController.pop();
        }

        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }

        super.onBackPressed();
    }

    @Override
    public void pushFragment(Fragment fragment) {
        navController.push(fragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        super.finish();
        Utils.setExitActivityAnimation(this);
    }
}
