package com.tec.ine.ui.fragment_container;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tec.ine.R;
import com.tec.ine.databinding.ActivityFragmentContainerBinding;
import com.tec.ine.models.INE;
import com.tec.ine.ui.form.FragmentForm;
import com.tec.ine.utils.Codes;

public class FragmentContainerActivity extends AppCompatActivity implements FragmentForm.FragmentFormInterface {
    public static final String INE = "INE";

    private ActivityFragmentContainerBinding vBind;
    private INE ine;
    private Bitmap selectedImage;
    private FragmentForm fragmentForm;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Codes.RESULT_TAKE_PHOTO) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImage = imageBitmap;
            fragmentForm.setSelectedImage(selectedImage);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_fragment_container);
        ine = (INE) getIntent().getSerializableExtra(INE);
        setUpFragment();

        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setUpFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragmentForm = new FragmentForm();

        Bundle args = new Bundle();
        args.putSerializable(FragmentContainerActivity.INE, ine);
        fragmentForm.setArguments(args);

        transaction.add(vBind.mainFragment.getId(),
                fragmentForm,
                Codes.FRAGMENT_FORM);
        transaction.commit();
    }

    @Override
    public void onOpenCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Codes.RESULT_TAKE_PHOTO);
        }
    }
}
