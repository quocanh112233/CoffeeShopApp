package com.example.coffeeshopapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.activity.CheckOutActivity;
import com.example.coffeeshopapp.controller.CartController;
import com.example.coffeeshopapp.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartItem> cartItems;
    private CartController cartController;

    public CartAdapter(Context context, List<CartItem> cartItems, CartController cartController) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartController = cartController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.tvCartItemName.setText(item.getName());
        holder.tvCartItemCost.setText(String.format("$%.2f", item.getCost()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        Glide.with(context)
                .load(item.getImagePath())
                .placeholder(R.drawable.img_placeholder)
                .into(holder.imgCartItem);

        holder.increase.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            if (cartController.updateQuantity(item.getProductId(), newQuantity)) {
                item.setQuantity(newQuantity);
                holder.tvQuantity.setText(String.valueOf(newQuantity));
            }
        });

        holder.decrease.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() - 1;
            if (cartController.updateQuantity(item.getProductId(), newQuantity)) {
                item.setQuantity(newQuantity);
                holder.tvQuantity.setText(String.valueOf(newQuantity));
            }
        });

        holder.delete.setOnClickListener(v -> {
            cartController.removeFromCart(item.getProductId());
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CheckOutActivity.class);
            intent.putExtra("PRODUCT_ID", item.getProductId());
            intent.putExtra("PRODUCT_NAME", item.getName());
            intent.putExtra("PRODUCT_COST", item.getCost());
            intent.putExtra("PRODUCT_QUANTITY", item.getQuantity());
            intent.putExtra("PRODUCT_IMAGE_PATH", item.getImagePath());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCartItems(List<CartItem> newItems) {
        cartItems.clear();
        cartItems.addAll(newItems);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCartItem;
        TextView tvCartItemName, tvCartItemCost, tvQuantity;
        ImageView increase, decrease, delete;

        public ViewHolder(View itemView) {
            super(itemView);
            imgCartItem = itemView.findViewById(R.id.imgCartItem);
            tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            tvCartItemCost = itemView.findViewById(R.id.tvCartItemCost);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            increase = itemView.findViewById(R.id.increase);
            decrease = itemView.findViewById(R.id.decrease);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}