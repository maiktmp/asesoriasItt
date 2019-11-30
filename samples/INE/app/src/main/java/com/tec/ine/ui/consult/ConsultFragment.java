package com.tec.ine.ui.consult;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.ine.R;
import com.tec.ine.databinding.ActivityIneDetailBinding;
import com.tec.ine.databinding.FragmentConsultBinding;
import com.tec.ine.interactors.FBInteractors;
import com.tec.ine.models.INE;
import com.tec.ine.ui.details.IneDetailActivity;

import java.util.List;

public class ConsultFragment extends Fragment {

    private FragmentConsultBinding vBind;
    private FBInteractors fbInteractors;
    private String field = "fullName";
    private INEAdapter adapter;
    private List<INE> ines;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fbInteractors = FBInteractors.getInstance();

        vBind = FragmentConsultBinding.inflate(inflater, container, false);
        setUpFilter();
        setUpOptions();

        setupRecyclerView();
        setUpAdapter();

        return vBind.getRoot();
    }


    private void setUpFilter() {
        vBind.etFilter.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        vBind.btnSearch.setOnClickListener(v -> {
            vBind.tilFilter.setError(null);
            String filter = vBind.etFilter.getText().toString();
            if (filter.equals("")) {
                vBind.tilFilter.setError("Ingrese un texto válido");
                return;
            }
            vBind.progressBar.setVisibility(View.VISIBLE);
            vBind.tvEmpty.setVisibility(View.GONE);
            vBind.rvIne.setVisibility(View.GONE);

            fbInteractors.searchINE(field, filter, ines -> {
                vBind.progressBar.setVisibility(View.GONE);

                adapter.setInes(ines);
                if (ines.isEmpty()) {
                    vBind.tvEmpty.setVisibility(View.VISIBLE);
                    vBind.rvIne.setVisibility(View.GONE);
                } else {
                    vBind.rvIne.setVisibility(View.VISIBLE);
                    vBind.tvEmpty.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void setUpOptions() {
        vBind.rbName.setOnClickListener(v -> field = "fullName");
        vBind.rbCurp.setOnClickListener(v -> field = "curp");
        vBind.rbMunicipality.setOnClickListener(v -> field = "municipality");
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        vBind.rvIne.setLayoutManager(layoutManager);
        vBind.rvIne.setItemAnimator(new DefaultItemAnimator());
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
        vBind.rvIne.setAdapter(adapter);
    }

}
