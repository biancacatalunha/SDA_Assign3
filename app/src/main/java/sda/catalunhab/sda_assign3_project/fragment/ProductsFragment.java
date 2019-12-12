package sda.catalunhab.sda_assign3_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sda.catalunhab.sda_assign3_project.R;
import sda.catalunhab.sda_assign3_project.adapter.ProductsAdapter;
import sda.catalunhab.sda_assign3_project.type.Product;

public class ProductsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_item_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new ProductsAdapter(getProductList());
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    private ArrayList<Product> getProductList() {
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Belly Jacket", "€37.00", R.drawable.belly_jacket));
        products.add(new Product("Formal Shirt", "€23.00", R.drawable.formal_shirt));


        return products;
    }
}
