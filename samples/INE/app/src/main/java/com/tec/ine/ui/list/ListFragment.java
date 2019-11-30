package com.tec.ine.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.ine.databinding.FragmentListBinding;
import com.tec.ine.interactors.FBInteractors;
import com.tec.ine.models.INE;
import com.tec.ine.ui.consult.INEAdapter;
import com.tec.ine.ui.details.IneDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding vBind;
    private FBInteractors fbInteractors;
    private INEAdapter adapter;
    private List<INE> ines = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fbInteractors = FBInteractors.getInstance();
        vBind = FragmentListBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setUpAdapter();
        setUpFilters();
        return vBind.getRoot();
    }

    private void setUpFilters() {
        vBind.rbActive.setOnClickListener(v -> fetchData(true));
        vBind.rbDelete.setOnClickListener(v -> fetchData(false));
        vBind.rbAll.setOnClickListener(v -> fetchData(null));
        fetchData(null);
    }

    private void fetchData(Boolean status) {
        vBind.progressBar.setVisibility(View.VISIBLE);
        vBind.tvEmpty.setVisibility(View.GONE);
        vBind.rvInes.setVisibility(View.GONE);

        fbInteractors.getAll(status, ines -> {
            vBind.progressBar.setVisibility(View.GONE);

            if (ines.isEmpty()) {
                vBind.rvInes.setVisibility(View.GONE);
                vBind.tvEmpty.setVisibility(View.VISIBLE);
            }
            vBind.rvInes.setVisibility(View.VISIBLE);
            vBind.tvEmpty.setVisibility(View.GONE);

            adapter.setInes(ines);
            adapter.notifyDataSetChanged();
        });
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        vBind.rvInes.setLayoutManager(layoutManager);
        vBind.rvInes.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpAdapter() {
        adapter = new INEAdapter(
                ines,
                ine -> {
                    Intent i = new Intent(getContext(), IneDetailActivity.class);
                    i.putExtra(IneDetailActivity.INE_ID, ine.getFBID());
                    startActivity(i);
                }
        );
        vBind.rvInes.setAdapter(adapter);
    }

}
