package sda.catalunhab.sda_assign3_project.adapter;

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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private ArrayList<Product> dataSet;

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

    public ProductsAdapter(ArrayList<Product> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        ImageView productImage = v.findViewById(R.id.imageItem);
        final TextView name = v.findViewById(R.id.productText);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), name.getText().toString() , Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.image.setImageResource(dataSet.get(position).getImage());
        holder.name.setText(dataSet.get(position).getName());
        holder.price.setText(dataSet.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
