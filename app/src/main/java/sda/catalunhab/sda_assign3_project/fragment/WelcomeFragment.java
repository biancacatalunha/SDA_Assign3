package sda.catalunhab.sda_assign3_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import sda.catalunhab.sda_assign3_project.R;

/**
 * DCU - SDA - Assignment 3
 *
 * Fragment representing the Products tab
 * Asks the user for a name, photo with the t-shirt design, delivery address or
 * store for collection.
 * Then opens an email intent with the data entered by the user
 *
 * @author Bianca Catalunha <bianca.catalunha2@mail.dcu.ie>
 * @since December 2019
 */
public class WelcomeFragment extends Fragment {

    /**
     * Required empty public constructor
     */
    public WelcomeFragment() {}

    /**
     * This method is called when it's the first time the fragment's UI is drawn.
     * It inflates the container into the fragment layout.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
}
