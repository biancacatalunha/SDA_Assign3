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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sda.catalunhab.sda_assign3_project.R;
import sda.catalunhab.sda_assign3_project.adapter.ProductsAdapter;
import sda.catalunhab.sda_assign3_project.type.Product;

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
public class ProductsFragment extends Fragment {

    /**
     * This method is called when it's the first time the fragment's UI is drawn.
     * It inflates the container into the fragment layout, sets the layout manager
     * to grid with two columns for the recycler view layout and creates the
     * products adapter which will attach each product to the view.
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
        final View root = inflater.inflate(R.layout.fragment_item_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(root.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new ProductsAdapter(getProductList());
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    /**
     * Creates and populates a list of products which contain name, price and image
     *
     * @return a list of products
     */
    private ArrayList<Product> getProductList() {
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Belly Jacket", "€37.00", R.drawable.belly_jacket));
        products.add(new Product("Off Shoulder", "€23.00", R.drawable.off_shoulder));
        products.add(new Product("Belly Top", "€26.00", R.drawable.belly_top));
        products.add(new Product("Jacket", "€41.00", R.drawable.jacket));
        products.add(new Product("Half Sleeve Top", "€19.00", R.drawable.half_sleeve_top));
        products.add(new Product("Cardigan", "€29.00", R.drawable.cardigan));
        products.add(new Product("Cleavage Shirt ", "€31.00", R.drawable.cleavege_shirt));
        products.add(new Product("Formal Shirt ", "€32.00", R.drawable.formal_shirt));
        products.add(new Product("Casual Dress", "€42.00", R.drawable.casual_dress));
        products.add(new Product("Party Dress", "€75.00", R.drawable.party_dress));

        return products;
    }
}
