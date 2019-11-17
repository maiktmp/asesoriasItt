package com.pitt.asesoriasitt.ui.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.db.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<User> users;
    private OnUserClickListener OnUserClickListener;

    public UserAdapter(ArrayList<User> users, OnUserClickListener onUserClickListener) {
        this.users = users;
        this.OnUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_user,
                parent,
                false
        );
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvUserCarrier.setText(user.getCarrier().getName());
        holder.tvUserName.setText(user.getFullName());
        holder.itemRoot.setClickable(true);
        holder.itemRoot.setChecked(false);
        holder.itemRoot.setOnClickListener(v -> OnUserClickListener.onUserClickListener(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView itemRoot;
        private TextView tvUserName;
        private TextView tvUserCarrier;

        public ViewHolder(View itemView) {
            super(itemView);
            itemRoot = itemView.findViewById(R.id.item_root);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserCarrier = itemView.findViewById(R.id.tv_user_carrier);
        }
    }


    public interface OnUserClickListener {
        void onUserClickListener(User user);
    }
}
