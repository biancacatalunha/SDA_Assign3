package sda.catalunhab.sda_assign3_project.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import sda.catalunhab.sda_assign3_project.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Spinner locationDropDown;
    private TextView deliveryAddress;
    private ImageView cameraImage;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        deliveryAddress = root.findViewById(R.id.deliveryAddress);

        cameraImage = root.findViewById(R.id.cameraImage);
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoIntent(v);
            }
        });

        RadioGroup radioGroup = root.findViewById(R.id.radioGroup);
        setOnCheckedChangeListener(radioGroup);

        setLocationDropdownValues(root);

        return root;
    }

    private void takePhotoIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cameraImage.setImageBitmap(imageBitmap);
        }
    }

    private void setLocationDropdownValues(View root) {
        locationDropDown = root.findViewById(R.id.daysDropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.collection_stores, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationDropDown.setAdapter(adapter);
    }

    private void setOnCheckedChangeListener(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                            deliveryAddress.setVisibility(View.VISIBLE);
                            locationDropDown.setVisibility(View.INVISIBLE);//set to invisible so they won't be on top of each other
                        break;
                    case R.id.radioButton2:
                            locationDropDown.setVisibility(View.VISIBLE);
                            deliveryAddress.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }
}
