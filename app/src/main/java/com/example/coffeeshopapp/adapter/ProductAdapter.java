package com.example.coffeeshopapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color; // For explicit color setting
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.model.Product;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> productList;
    private OnProductClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductCost.setText(String.format(Locale.US, "$%.2f", product.getCost()));
        Glide.with(context)
                .load(product.getImagePath())
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(holder.imgProduct);

        holder.itemView.setBackgroundColor(selectedPosition == position ? Color.LTGRAY : Color.WHITE); // Example highlight

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product);
                if (selectedPosition != holder.getAdapterPosition()) {
                    notifyItemChanged(selectedPosition);
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateProducts(List<Product> newProducts) {
        this.productList.clear();
        this.productList.addAll(newProducts);
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void clearSelection() {
        int oldSelectedPosition = selectedPosition;
        selectedPosition = RecyclerView.NO_POSITION;
        if (oldSelectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(oldSelectedPosition);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductCost;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductCost = itemView.findViewById(R.id.tvProductCost);
        }
    }
}