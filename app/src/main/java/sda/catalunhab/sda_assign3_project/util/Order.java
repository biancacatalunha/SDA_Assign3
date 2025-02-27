package sda.catalunhab.sda_assign3_project.util;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import sda.catalunhab.sda_assign3_project.R;

/**
 * DCU - SDA - Assignment 3
 *
 * This is a helper class for the Order Fragment, it gets and sets fields.
 * It was created in order to keep the fragment class cleaner,
 * more readable and only containing the business logic.
 *
 * @author Bianca Catalunha <bianca.catalunha2@mail.dcu.ie>
 * @since December 2019
 */
public class Order {

    private Uri photoURI;
    private String photoPath;

    public void setPhotoURI(Uri uri) {
        this.photoURI = uri;
    }

    public void setPhotoPath(String path) {
        this.photoPath = path;
    }

    public Uri getPhotoURI() {
        return this.photoURI;
    }

    public String getSubject(View view) {
        return view.getContext().getString(R.string.email_subject);
    }

    public String[] getMailTo(View view) {
        return new String[]{view.getContext().getString(R.string.email_to)};
    }

    private String getName(View view) {
        EditText nameEditText = view.findViewById(R.id.customerName);
        return nameEditText.getText().toString();
    }

    private RadioButton getDeliveryRadioButton(View view) {
        return view.findViewById(R.id.radioButton1);
    }

    private RadioButton getCollectionRadioButton(View view) {
        return view.findViewById(R.id.radioButton2);
    }

    private String getDeliveryAddress(View view) {
        EditText delivery = view.findViewById(R.id.deliveryAddress);
        return delivery.getText().toString();
    }

    public EditText getDeliveryAddressEditText(View view) {
        return view.findViewById(R.id.deliveryAddress);
    }

    public ImageView getCameraImage(View view) {
        return view.findViewById(R.id.cameraImage);
    }

    public RadioGroup getRadioGroup(View view) {
        return view.findViewById(R.id.radioGroup);
    }

    public Button getSendButton(View view) {
        return view.findViewById(R.id.sendOrderButton);
    }

    public Spinner getStoreLocation(View view) {
        return view.findViewById(R.id.storeLocation);
    }

    private String getCollectionStore(View view) {
        Spinner store = view.findViewById(R.id.storeLocation);
        return store.getSelectedItem().toString();
    }

    public String getEmailBody(View view) {
        return view.getContext().getString(R.string.email_body_part1) +
                getDeliveryOrCollectionInfo(view) +
                view.getContext().getString(R.string.email_body_part2) +
                getName(view);
    }

    private String getDeliveryOrCollectionInfo(View view) {
        if(isCollectionSelected(view)) {
            return view.getContext().getString(R.string.email_body_collection_store) + getCollectionStore(view);
        }
        return view.getContext().getString(R.string.email_body_delivery_address) + getDeliveryAddress(view);
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public String getNameErrorMsg(View view) {
        return view.getContext().getString(R.string.error_msg_name);
    }

    public String getDeliveryOrCollectionErrorMsg(View view) {
        return view.getContext().getString(R.string.error_msg_delivery_or_collection);
    }

    public String getDeliveryIsRequiredErrorMessage(View view) {
        return view.getContext().getString(R.string.error_msg_delivery);
    }

    public boolean isNameEmpty(View view) {
        return getName(view).equals("");
    }

    public boolean isDeliverySelected(View view) {
        return getDeliveryRadioButton(view).isChecked();
    }

    public boolean isDeliveryAddressEmpty(View view) {
        return getDeliveryAddress(view).equals("");
    }

    public boolean isCollectionSelected(View view) {
        return getCollectionRadioButton(view).isChecked();
    }
}
