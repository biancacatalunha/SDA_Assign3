package sda.catalunhab.sda_assign3_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import sda.catalunhab.sda_assign3_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private Spinner daysDropDown;
    private TextView deliveryAddress;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        deliveryAddress = root.findViewById(R.id.deliveryAddress);

        RadioGroup radioGroup = root.findViewById(R.id.radioGroup);
        setOnCheckedChangeListener(radioGroup);

        setDaysDropdownValues(root);

        return root;
    }

    private void setDaysDropdownValues(View root) {
        daysDropDown = root.findViewById(R.id.daysDropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.collection_stores, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysDropDown.setAdapter(adapter);
    }

    private void setOnCheckedChangeListener(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                            deliveryAddress.setVisibility(View.VISIBLE);
                            daysDropDown.setVisibility(View.INVISIBLE);//set to invisible so they won't be on top of each other
                        break;
                    case R.id.radioButton2:
                            daysDropDown.setVisibility(View.VISIBLE);
                            deliveryAddress.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }
}
