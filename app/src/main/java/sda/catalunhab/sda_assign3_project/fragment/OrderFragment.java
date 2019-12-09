package sda.catalunhab.sda_assign3_project.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import sda.catalunhab.sda_assign3_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private RadioGroup storesRadioGroup;
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
        storesRadioGroup = root.findViewById(R.id.storeRadioGroup);

        RadioGroup radioGroup = root.findViewById(R.id.radioGroup);
        setOnCheckedChangeListener(radioGroup);

        return root;
    }

    private void setOnCheckedChangeListener(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                            deliveryAddress.setVisibility(View.VISIBLE);
                            storesRadioGroup.setVisibility(View.INVISIBLE);//set to invisible so they won't be on top of each other
                        break;
                    case R.id.radioButton2:
                            storesRadioGroup.setVisibility(View.VISIBLE);
                            deliveryAddress.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }
}
