package com.dirtybits.privatechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import auth.User;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ChatsFragment frag1 = new ChatsFragment();
    ContactsFragment frag2 = new ContactsFragment();
    private User loggedUser = null;
    private static final int REQ_SIGNIN = 3;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_chat_tab,
            R.drawable.ic_contacts_tab
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //start login activity
        if(loggedUser == null) {
            Log.v("MainActivity", "No user is logged so start Login activity.");
            startActivityForResult(new Intent(getApplicationContext(), Login.class), REQ_SIGNIN);
        }
        */

        fab = (FloatingActionButton) findViewById(R.id.fab_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Custom adapter class provides fragments required for the view pager.
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Defines the number of tabs by setting appropriate fragment and tab name.
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        //Assigns the ViewPager to TabLayout.
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        /*Manage fab on all tabs*/
        frag1.shareFab(fab); // init the FAB

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        fab.hide(); // Hide animation
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        switch (viewPager.getCurrentItem()) {
                            case 0:
                                frag2.shareFab(null); // Remove FAB from fragment
                                frag1.shareFab(fab); // Share FAB to new displayed fragment
                                break;
                            case 1:
                            default:
                                frag1.shareFab(null); // Remove FAB from fragment
                                frag2.shareFab(fab); // Share FAB to new displayed fragment
                                break;
                        }
                        fab.show(); // Show animation
                        break;
                }
            }
        });
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(frag1, "chats");
        adapter.addFragment(frag2, "contacts");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_SIGNIN) {
            if (resultCode == RESULT_OK) {
                // get data from intent
                String user = data.getStringExtra("user");
                String pass = data.getStringExtra("pass");
                Log.v("MainActivity", "A user logged in: " + user + " " + pass);
                loggedUser = new User(user, pass);
            } else if (resultCode == RESULT_CANCELED) {
                //user data was not retrieved
                Log.v("MainActivity", "NO user logged in.");
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        }
    }
}
