package com.example.customviewtestapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.customviewtestapp.fragments.BubbleViewFragment;
import com.example.customviewtestapp.fragments.ColorPickerViewFragment;
import com.example.customviewtestapp.fragments.GriddedImageViewFragment;
import com.example.customviewtestapp.fragments.ViewsListFragment;
import com.example.customviewtestapp.fragments.ViewsListFragment.onItemSelectedListener;

import java.util.List;

public class MainActivity extends FragmentActivity implements
        onItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Bitmap mFragmentBitmap;
    private ViewsListFragment main;
    private Boolean isDrawMode = false;
    private ActionBar mActionBar;


    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mActionBar = getActionBar();


        //fragment manager/transaction init
     /*   fm = getSupportFragmentManager();
        ft = fm.beginTransaction();*/


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (savedInstanceState == null) {

            main = new ViewsListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, main).commit();


            //mFragmentBitmap
        }

        CustomActionBarDrawerToggle mToggle = new CustomActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher, 1, 0);

        mDrawerLayout.setDrawerListener(mToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //Check if the gridded image fragment is visible, if so, show the draw icon
        if(isDrawMode) {
            menu.getItem(1).setVisible(true);
            //invalidateOptionsMenu();
        }



         else {
            menu.getItem(1).setVisible(false);
            //invalidateOptionsMenu();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {


            case R.id.action_settings:

                return true;


            case R.id.action_draw:

                setPaintBrushesVisible(true);

                break;
        }
   /*     if (id == R.id.action_settings) {
            return true;
        }

        if*/


        return super.onOptionsItemSelected(item);
    }


    private void setPaintBrushesVisible(Boolean set){

        if(set){
            //set brushes to be visible on screen
        }

        else {
            //set brushes to be invisible
        }
    }

    // A list item in the main fragment was selected
    // Swap out this fragment with the appropriate fragment
    @Override
    public void onItemSelected(int position) {

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (position) {
            case 0:


                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                ft.replace(R.id.container, new ColorPickerViewFragment(), "0");
                ft.addToBackStack("0");

                ft.commit();

                break;

            case 1:



                //fm = getSupportFragmentManager();
                //ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                ft.replace(R.id.container, new BubbleViewFragment(), "1");
                ft.addToBackStack(null);

                ft.commit();

                break;

            case 2:
                isDrawMode = true;
                invalidateOptionsMenu();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                ft.replace(R.id.container, new GriddedImageViewFragment(), "2");
                ft.addToBackStack(null);

                ft.commit();

                break;
        }
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }


    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {


        public CustomActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {

            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        }


        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);

            if (slideOffset > 0.0f) {

                // Toast.makeText(getApplicationContext(), "Drawer offset > 0.0f", Toast.LENGTH_SHORT).show();
                //do something cool the fragment background here

                Fragment frag = getVisibleFragment();
                View view = frag.getView();
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                mFragmentBitmap = view.getDrawingCache();
                //tiltFragmentBitmap(mFragmentBitmap, 45.f);
            } else {

                //return fragment background to it's original state here
            }


        }


        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);

            //return fragment to original state
        }

        private void tiltFragmentBitmap(Bitmap source, float angle) {

/*
            if (source == null) {

                Toast.makeText(getApplicationContext(), "Bitmap is null", Toast.LENGTH_SHORT).show();
            }


            Matrix matrix = new Matrix();

            matrix.postRotate(angle);
            Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);*/


        }

        /**
         * A placeholder fragment containing a simple view.
         */
    /*
     * public static class PlaceholderFragment extends Fragment {
	 * 
	 * public PlaceholderFragment() { }
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { View rootView =
	 * inflater.inflate(R.layout.fragment_main, container, false); return
	 * rootView; } }
	 */

    }
}
