package com.explore.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.explore.utils.ScrimUtil;
import com.explore.utils.Utils;
import com.vgrec.explore.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    public static final int ABOUT_BUTTON_POSITION = 2;

    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private int mCurrentSelectedPosition = 0;
    private NavigationDrawerAdapter adapter;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ViewGroup scrimBackground = (ViewGroup) view.findViewById(R.id.scrim_background);
        scrimBackground.setBackgroundDrawable(ScrimUtil.makeCubicGradientScrimDrawable(0xaa000000, 8, Gravity.BOTTOM));

        mDrawerListView = (ListView) view.findViewById(R.id.navigation_drawer_list);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        List<NavigationDrawerItem> items = new ArrayList<NavigationDrawerItem>() {{
            add(new NavigationDrawerItem(getString(R.string.explore), R.drawable.ic_explore));
            add(new NavigationDrawerItem(getString(R.string.favorite_places), R.drawable.ic_favorite));
            add(new NavigationDrawerItem(getString(R.string.about), R.drawable.ic_info));
        }};

        adapter = new NavigationDrawerAdapter(getActivity(), items);
        adapter.setSelectedItemPosition(mCurrentSelectedPosition);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return view;
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        );
        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        if (position == ABOUT_BUTTON_POSITION) {
            showAboutDialog();
            return;
        }

        setSelectedItemPosition(position);
        mCurrentSelectedPosition = position;

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    private void showAboutDialog() {
        new MaterialDialog.Builder(getActivity())
                .icon(getResources().getDrawable(R.drawable.ic_launcher))
                .title(R.string.about)
                .content(Html.fromHtml(getString(R.string.about_text)))
                .contentColorRes(R.color.dark_grey)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.teal)
                .show();
    }

    public void setSelectedItemPosition(int position) {
        if (adapter != null) {
            adapter.setSelectedItemPosition(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public void setItemChecked(int position) {
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    /**
     * The entity that represents a navigation drawer item: Icon + Title
     */
    private static class NavigationDrawerItem {
        String title;
        int icon;

        private NavigationDrawerItem(String title, int icon) {
            this.title = title;
            this.icon = icon;
        }
    }

    private static class NavigationDrawerAdapter extends ArrayAdapter<NavigationDrawerItem> {

        private LayoutInflater inflater;
        private List<NavigationDrawerItem> items;
        private int selectedItemPosition;

        public NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> items) {
            super(context, 0, items);
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = inflater.inflate(R.layout.item_drawer_list, parent, false);
            }
            TextView textView = (TextView) view.findViewById(R.id.drawer_item);
            textView.setText(items.get(position).title);
            textView.setCompoundDrawablesWithIntrinsicBounds(items.get(position).icon, 0, 0, 0);

            if (position == selectedItemPosition) {
                int selectedColor = view.getContext().getResources().getColor(R.color.teal);
                textView.setTextColor(selectedColor);
                Utils.setColorForTextViewDrawable(selectedColor, textView);
            } else {
                textView.setTextColor(view.getContext().getResources().getColor(R.color.drawer_item));
                Utils.setColorForTextViewDrawable(view.getContext().getResources().getColor(R.color.default_icon_color), textView);
            }

            return view;
        }

        public void setSelectedItemPosition(int selectedItemPosition) {
            this.selectedItemPosition = selectedItemPosition;
        }
    }
}
