package com.pitt.asesoriasitt.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.UserListFragmentBinding;
import com.pitt.asesoriasitt.db.models.Rol;
import com.pitt.asesoriasitt.db.models.User;
import com.pitt.asesoriasitt.interactors.UserInteractor;

import java.util.ArrayList;

public class FragmentUserList extends Fragment {
    public static String TEACHER_TAG = "TEACHER_TAG";
    public static String STUDENT_TAG = "STUDENT_TAG";


    private UserListFragmentBinding vBind;
    private UserInteractor userInteractor;
    private long rol;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        Bundle args = getArguments();
        rol = args.getLong(TEACHER_TAG, 0);
        if (rol == 0) {
            rol = args.getLong(STUDENT_TAG, 0);
        }


        vBind = DataBindingUtil.inflate(
                inflater, R.layout.user_list_fragment,
                container,
                false);
        View view = vBind.getRoot();

        userInteractor = UserInteractor.getInstance();

        userInteractor.getAllUserByRol(this::setUpRecyclerView, rol);

        return view;
    }

    private void setUpRecyclerView(ArrayList<User> userArrayList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        vBind.rvUsers.setLayoutManager(layoutManager);
        vBind.rvUsers.setItemAnimator(new DefaultItemAnimator());
        UserAdapter adapter = new UserAdapter(userArrayList, user -> {
            if (user.getFkIdRol() == Rol.TEACHER) {
                Intent i = new Intent(getActivity(), ActivityUserDetail.class);
                i.putExtra(ActivityUserDetail.USER_ID_TAG, user.getId());
                getActivity().startActivity(i);
            }
        });
        vBind.rvUsers.setVisibility(View.VISIBLE);
        vBind.pbLoading.setVisibility(View.GONE);
        vBind.rvUsers.setAdapter(adapter);
    }
}
