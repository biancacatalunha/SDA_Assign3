package sda.catalunhab.sda_assign3_project.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sda.catalunhab.sda_assign3_project.R;
import sda.catalunhab.sda_assign3_project.util.Order;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment  {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "OrderFragment";

    private Spinner locationDropDown;
    private TextView deliveryAddress;
    private ImageView cameraImage;
    private Order order;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_order, container, false);

        order = new Order();

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

        Button sendButton = root.findViewById(R.id.sendOrderButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailIntent(root);
            }
        });

        return root;
    }

    private void emailIntent(View view) {
        if (isValid(view)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");

            intent.putExtra(Intent.EXTRA_EMAIL, order.getMailTo(view));
            intent.putExtra(Intent.EXTRA_SUBJECT, order.getSubject(view));
            intent.putExtra(Intent.EXTRA_TEXT, order.getEmailBody(view));
            intent.putExtra(Intent.EXTRA_STREAM, order.getPhotoURI());

            if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                startActivity(intent);
                Log.d(TAG, "Email opened");
            }
        }
    }

    private boolean isValid(View view) {
        if (order.isNameEmpty(view)) {
            Toast toast = Toast.makeText(view.getContext(), order.getNameErrorMsg(view), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        if(!order.isDeliverySelected(view) && !order.isCollectionSelected(view)) {
            Toast toast = Toast.makeText(view.getContext(), order.getDeliveryOrCollectionErrorMsg(view), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        if(order.isDeliverySelected(view) && order.isDeliveryAddressEmpty(view)) {
            Toast toast = Toast.makeText(view.getContext(), order.getDeliveryIsRequiredErrorMessage(view), Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        return true;
    }

    private void takePhotoIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(view.getContext().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile(view);
            } catch (IOException e) {
                Log.i(TAG, "Error occurred while creating file");
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(view.getContext(),
                        view.getContext().getPackageName() + ".provider", photoFile);
                order.setPhotoURI(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Glide.with(this).load(order.getPhotoPath()).into(cameraImage);
        }
    }

    private void setLocationDropdownValues(View root) {
        locationDropDown = root.findViewById(R.id.storeLocation);
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

    private File createImageFile(View view) throws IOException {

        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = view.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        order.setPhotoPath(image.getPath());
        Log.i(TAG, "Photo path: " + order.getPhotoPath());

        return image;
    }
}
