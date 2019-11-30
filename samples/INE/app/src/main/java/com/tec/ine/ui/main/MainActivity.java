package com.tec.ine.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.tec.ine.R;
import com.tec.ine.databinding.ActivityMainBinding;
import com.tec.ine.interactors.FBInteractors;
import com.tec.ine.ui.consult.ConsultFragment;
import com.tec.ine.ui.form.FragmentForm;
import com.tec.ine.ui.list.ListFragment;
import com.tec.ine.utils.Codes;
import com.tec.ine.utils.Dialogs;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FragmentForm.FragmentFormInterface {

    private ActivityMainBinding vBind;
    private TabAdapter adapter;
    private Bitmap selectedImage;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Codes.RESULT_TAKE_PHOTO) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImage = imageBitmap;
            for (Fragment fragment : adapter.getmFragmentList()) {
                if (fragment instanceof FragmentForm) {
                    ((FragmentForm) fragment).setSelectedImage(selectedImage);
                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpTabLayout();
        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setTitle(null);
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
                Dialogs.alert(this, "¿Desea cerrar salir?", () -> {
                    FBInteractors.getInstance().logOut();
                    finish();
                });
                return true;
            case R.id.action_about:
                String info = "";
                info += "<b>Alumna: </b>";
                info += " <br/>";
                info += "<b>Materia: </b>";
                info += " <br/>";
                info += "<b>Profesor: </b>";

                Dialogs.alert(this, String.valueOf(Html.fromHtml(info)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpTabLayout() {
        adapter = new TabAdapter(getSupportFragmentManager(), TabAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        FBInteractors.getInstance().getAuthUser(user -> {
            if (Objects.equals(user.getEmail(), FBInteractors.ADMIN)) {
                adapter.addFragment(new FragmentForm(), "Alta");
            }
        });


        adapter.addFragment(new ConsultFragment(), "Consultar");
        adapter.addFragment(new ListFragment(), "Listar");


        vBind.vpFragment.setAdapter(adapter);
        vBind.tbLayout.setupWithViewPager(vBind.vpFragment);

    }

    @Override
    public void onOpenCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Codes.RESULT_TAKE_PHOTO);
        }
    }
}
