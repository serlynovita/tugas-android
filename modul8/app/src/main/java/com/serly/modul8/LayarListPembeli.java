package com.serly.modul8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.serly.modul8.Adapter.PembeliAdapter;
import com.serly.modul8.Adapter.PembeliAdapter;
import com.serly.modul8.Model.GetPembeli;
import com.serly.modul8.Model.Pembeli;
import com.serly.modul8.Rest.ApiClient;
import com.serly.modul8.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarListPembeli extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
//    Button btGet;
    Button btAddData, btGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_list_pembeli);

        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btAddData = (Button) findViewById(R.id.btAddData);
        btGet = (Button) findViewById(R.id.btGet);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetPembeli> mPembeliCall = mApiInterface.getPembeli();
                mPembeliCall.enqueue(new Callback<GetPembeli>() {
                    @Override
                    public void onResponse(Call<GetPembeli> call, Response<GetPembeli> response) {
                        Log.d("Get Pembeli",response.body().getStatus());
                        List<Pembeli> listPembeli = response.body().getResult();
                        mAdapter = new PembeliAdapter(listPembeli);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetPembeli> call, Throwable t) {
                        Log.d("Get Pembeli",t.getMessage());
                    }
                });
            }
        });
        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LayarInsertPembeli.class);
                startActivity(intent);
            }
        });

    }
}

