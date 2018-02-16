package com.alejandrocorrero.room.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alejandrocorrero.room.R;
import com.alejandrocorrero.room.data.model.Company;
import com.alejandrocorrero.room.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {


    private DetailActivityViewModel viewModel;
    private ActivityDetailBinding mbinding;
    private Company company=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        viewModel = ViewModelProviders.of(this).get(DetailActivityViewModel.class);
        Intent intennt = getIntent();
        if (intennt != null) {
            Bundle extras = intennt.getExtras();
            if (extras != null) {
                if (intennt.hasExtra("PrimaryKey")) {
                    String primayKey = extras.getString("PrimaryKey");
                    viewModel.getCompany(primayKey).observe(this, this::startValues);
                    mbinding.edtCIF.setEnabled(false);
                }
            }
        }
        mbinding.setPresenter(this);
        if(company==null){
            this.company=new Company();
            mbinding.setModel(company);
            mbinding.executePendingBindings();
        }
        //TODO VINCULAR DATOS CREAR UPDATE Y GUARDAR DATOS AÑADIR EMPTY VIEW Y ELIMINAR CON LONG CLICK

    }

    private void startValues(Company company) {
        this.company=company;
        mbinding.setModel(company);
        mbinding.executePendingBindings();
    }

    public void fabOnClick(View v){
        if(!mbinding.edtCIF.isEnabled()){
            new Thread(() -> viewModel.updateCompany(company)).start();
            finish();
        }


    }
}
