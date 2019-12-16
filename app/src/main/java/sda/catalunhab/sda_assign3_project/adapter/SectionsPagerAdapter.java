package sda.catalunhab.sda_assign3_project.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import sda.catalunhab.sda_assign3_project.R;
import sda.catalunhab.sda_assign3_project.fragment.OrderFragment;
import sda.catalunhab.sda_assign3_project.fragment.ProductsFragment;
import sda.catalunhab.sda_assign3_project.fragment.WelcomeFragment;

/**
 * DCU - SDA - Assignment 3
 *
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 *
 * Defined constants for the tabs so the switch is more readable
 *
 * @author Bianca Catalunha <bianca.catalunha2@mail.dcu.ie>
 * @since December 2019
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private static final String TAG = "SectionsPagerAdapter";

    private static final int WELCOME = 0;
    private static final int PRODUCTS = 1;
    private static final int ORDER = 2;

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * @param position integer to identify each tab, defined in the constants above
     * @return a fragment depending on the number identifier of the tab selected
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case WELCOME:
                Log.d(TAG, "Welcome tab clicked");
                return new WelcomeFragment();
            case PRODUCTS:
                Log.d(TAG, "Products tab clicked");
                return new ProductsFragment();
            case ORDER:
                Log.d(TAG, "Order t-shirt tab clicked");
                return new OrderFragment();
            default:
                Log.d(TAG, "No matching fragment found");
                return new Fragment();
        }
    }

    /**
     * Returns the name of the title of the tab that is stored in the TAB_TILES array
     *
     * @param position integer to identify each tab
     * @return CharSequence
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     * @return number of tabs
     */
    @Override
    public int getCount() {
        return 3;
    }
}