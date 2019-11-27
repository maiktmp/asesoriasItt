package com.pitt.asesoriasitt.ui.user;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.ListUserActivityBinding;
import com.pitt.asesoriasitt.db.models.Rol;
import com.pitt.asesoriasitt.ui.Dialogs;

public class ActivityListUser extends AppCompatActivity {

    private ListUserActivityBinding vBind;
    private TabAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.list_user_activity);
        setUpTabLayout();
        setUpBackButton();
    }

    private void setUpBackButton() {
        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                Dialogs.alert(this, "¿Desea cerrar salir?", this::finish);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
