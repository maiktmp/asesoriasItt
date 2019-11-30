package com.tec.ine.ui.consult;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.common.hash.HashingOutputStream;
import com.tec.ine.R;
import com.tec.ine.models.INE;

import java.util.List;

public class INEAdapter extends RecyclerView.Adapter<INEAdapter.ViewHolder> {

    private List<INE> ines;
    private OnIneClickListener onIneClickListener;

    public INEAdapter(List<INE> ines, OnIneClickListener onIneClickListener) {
        this.ines = ines;
        this.onIneClickListener = onIneClickListener;
    }

    public void setInes(List<INE> ines) {
        this.ines = ines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_ine,
                parent,
                false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        INE ine = ines.get(position);
        holder.name.setText(ine.getFirstLastName() + " " + ine.getSecondLastName() + " " + ine.getName());
        holder.curp.setText(ine.getCurp());
        holder.itemRoot.setOnClickListener(v -> onIneClickListener.onIneClickListener(ine));
    }

    @Override
    public int getItemCount() {
        return ines.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView curp;
        MaterialCardView itemRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            curp = itemView.findViewById(R.id.tv_curp);
            itemRoot = itemView.findViewById(R.id.item_root);
        }
    }

    public interface OnIneClickListener {
        void onIneClickListener(INE ine);
    }
}
