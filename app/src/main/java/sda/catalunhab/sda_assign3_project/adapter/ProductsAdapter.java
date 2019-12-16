package sda.catalunhab.sda_assign3_project.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import sda.catalunhab.sda_assign3_project.R;
import sda.catalunhab.sda_assign3_project.type.Product;

/**
 * DCU - SDA - Assignment 3
 *
 * Adapter to link each product item to the recycler view list
 *
 * @author Bianca Catalunha <bianca.catalunha2@mail.dcu.ie>
 * @since December 2019
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private ArrayList<Product> dataSet;

    private static final String TAG = "ProductsAdapter";

    /**
     * OnCreateViewHolder this method is called creating a view for each object
     */
    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
        RelativeLayout itemParentLayout;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageItem);
            name = itemView.findViewById(R.id.productText);
            price = itemView.findViewById(R.id.productPrice);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);
        }
    }

    /**
     * Initializes the ArrayList of products
     *
     * @param dataSet list of products
     */
    public ProductsAdapter(ArrayList<Product> dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Attaches the view group to the fragment item
     * Sets an onClick listener to each product image
     * When the user clicks on it, a toast message is displayed showing the name of the product
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ProductsAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        ImageView productImage = v.findViewById(R.id.imageItem);
        final TextView name = v.findViewById(R.id.productText);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), name.getText().toString(), Toast.LENGTH_SHORT);
                toast.show();
                Log.d(TAG, "Product clicked: " + name.getText().toString());
            }
        });
        Log.d(TAG, "Product View holder created");

        return new ProductViewHolder(v);
    }

    /**
     * Sets the holder object with the data entered in ProductsFragment
     *
     * @param holder the object that stores a product
     * @param position gets the array position
     */
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.image.setImageResource(dataSet.get(position).getImage());
        holder.name.setText(dataSet.get(position).getName());
        holder.price.setText(dataSet.get(position).getPrice());
    }

    /**
     * @return the amount of products in the array
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
