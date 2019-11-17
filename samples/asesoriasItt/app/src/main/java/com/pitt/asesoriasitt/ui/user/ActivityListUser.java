package com.pitt.asesoriasitt.ui.user;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.ListUserActivityBinding;
import com.pitt.asesoriasitt.db.models.Rol;

public class ActivityListUser extends AppCompatActivity {

    private ListUserActivityBinding vBind;
    private TabAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.list_user_activity);
        setUpTabLayout();
    }


    public void setUpTabLayout() {
        adapter = new TabAdapter(getSupportFragmentManager());
        Bundle teacherArgs = new Bundle();
        teacherArgs.putLong(FragmentUserList.TEACHER_TAG, Rol.TEACHER);

        Bundle studentArgs = new Bundle();
        studentArgs.putLong(FragmentUserList.STUDENT_TAG, Rol.STUDENT);


        FragmentUserList teacherFragment = new FragmentUserList();
        teacherFragment.setArguments(teacherArgs);
        FragmentUserList studentFragment = new FragmentUserList();
        studentFragment.setArguments(studentArgs);

        adapter.addFragment(teacherFragment, "Mestros");
        adapter.addFragment(studentFragment, "Estudiantes");

        vBind.vpFragment.setAdapter(adapter);
        vBind.tbLayout.setupWithViewPager(vBind.vpFragment);
    }

}
