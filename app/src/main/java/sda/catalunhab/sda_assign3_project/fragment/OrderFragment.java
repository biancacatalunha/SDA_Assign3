/*
 * Copyright 2019 Bianca Catalunha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import android.widget.RadioGroup;
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
 * DCU - SDA - Assignment 3
 *
 * Fragment representing the Order T-shirt tab
 * Asks the user for a name, photo with the t-shirt design, delivery address or
 * store for collection.
 * Then opens an email intent with the data entered by the user
 *
 * @author Bianca Catalunha <bianca.catalunha2@mail.dcu.ie>
 * @since December 2019
 */
public class OrderFragment extends Fragment  {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "OrderFragment";
    private Order order;
    private View root;

    /**
     * Required empty public constructor
     */
    public OrderFragment() {}

    /**
     * This method is called when it's the first time the fragment's UI is drawn.
     * It inflates the container into the fragment layout, instantiates the
     * Order helper class, sets onCheckedListener for the delivery/collection radio
     * group, attaches the location values for the collection dropdown, set's an
     * onClickListener that will open the camera and sets an onClickListener for
     * the Send Button that will open the email intent.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return View that is the root of the fragment layout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_order, container, false);
        order = new Order();

        setOnCheckedChangeListenerRadioGroup();
        setLocationDropdownValues();

        order.getCameraImage(root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoIntent();
            }
        });

        order.getSendButton(root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailIntent();
            }
        });

        return root;
    }

    /**
     * Checks if the user input is valid, if so, creates an send intent
     * attaches mail to, subject, body and attachments to the intent
     */
    private void emailIntent() {
        if (isValid()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");

            intent.putExtra(Intent.EXTRA_EMAIL, order.getMailTo(root));
            intent.putExtra(Intent.EXTRA_SUBJECT, order.getSubject(root));
            intent.putExtra(Intent.EXTRA_TEXT, order.getEmailBody(root));
            intent.putExtra(Intent.EXTRA_STREAM, order.getPhotoURI());

            if (intent.resolveActivity(root.getContext().getPackageManager()) != null) {
                startActivity(intent);
                Log.d(TAG, "Email opened");
            }
        }
    }

    /**
     * This method validates user input for the order t-shit fragment.
     * It first checks if the name is empty, if so, displays a toast with an error
     * message and returns false.
     * If the name is not empty, checks if one of the two radio buttons is selected, if not,
     * displays a toast with an error message and returns false.
     * If one of the radio buttons is selected, checks if it is the delivery one. If it is, checks
     * if the delivery address is empty, if so, displays an error message and returns false.
     * If none of the above, returns true
     *
     * @return boolean, true if the user input is valid, false if it isn't
     */
    private boolean isValid() {
        if (order.isNameEmpty(root)) {
            Toast toast = Toast.makeText(root.getContext(), order.getNameErrorMsg(root), Toast.LENGTH_SHORT);
            toast.show();
            Log.d(TAG, "Input validation failed - Name is empty");
            return false;
        }

        if(!order.isDeliverySelected(root) && !order.isCollectionSelected(root)) {
            Toast toast = Toast.makeText(root.getContext(), order.getDeliveryOrCollectionErrorMsg(root), Toast.LENGTH_SHORT);
            toast.show();
            Log.d(TAG, "Input validation failed - Delivery nor Collection selected");
            return false;
        }

        if(order.isDeliverySelected(root) && order.isDeliveryAddressEmpty(root)) {
            Toast toast = Toast.makeText(root.getContext(), order.getDeliveryIsRequiredErrorMessage(root), Toast.LENGTH_SHORT);
            toast.show();
            Log.d(TAG, "Input validation failed - Delivery address is empty");
            return false;
        }

        return true;
    }

    /**
     * Creates a caption image intent which will open the camera.
     * Creates a file to store the photo the user is going to take.
     * If the file was created successfully, gets the URI of the file
     * and attaches to the intent so the image will be saved in the path
     * generated to be accessed later.
     */
    private void takePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(root.getContext().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.i(TAG, "Error occurred while creating photo file");
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(root.getContext(),
                        root.getContext().getPackageName() + ".provider", photoFile);
                order.setPhotoURI(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * Attaches the image the user took into the camera image view.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Glide.with(this).load(order.getPhotoPath()).into(order.getCameraImage(root));
            Log.d(TAG, "Photo returned and displayed");
        }
    }

    /**
     * Sets the adapter for the collection store locations dropdown and attaches to the UI
     */
    private void setLocationDropdownValues() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.collection_stores, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order.getStoreLocation(root).setAdapter(adapter);
        Log.d(TAG, "Location dropdown attached to view");
    }

    /**
     * Sets an onChangeListener for the delivery/collection radio group
     * If collection is selected, displays the store locations dropdown and hide the delivery
     * address edit text.
     * If delivery is selected, displays the delivery address edit text and hides the store
     * locations dropdown so the two don't overlay.
     */
    private void setOnCheckedChangeListenerRadioGroup() {
        order.getRadioGroup(root).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        order.getDeliveryAddressEditText(root).setVisibility(View.VISIBLE);
                        order.getStoreLocation(root).setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Delivery selected");
                    break;
                    case R.id.radioButton2:
                        order.getStoreLocation(root).setVisibility(View.VISIBLE);
                        order.getDeliveryAddressEditText(root).setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Collection selected");
                     break;
                }
            }
        });
    }

    /**
     * Creates a file path where the
     * prefix is JPEG and the date and time, e.g JPEG_20191216_190016_1206104671
     * suffix is .jpg
     * directory is the external files dir
     *
     * @return File path
     * @throws IOException if it's unable to create the file
     */
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = root.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        order.setPhotoPath(image.getPath());
        Log.i(TAG, "Photo path: " + order.getPhotoPath());

        return image;
    }
}
