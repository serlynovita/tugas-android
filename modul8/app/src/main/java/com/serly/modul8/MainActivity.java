package com.serly.modul8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.serly.modul8.Adapter.MyAdapter;
import com.serly.modul8.Model.GetPembelian;
import com.serly.modul8.Model.Pembelian;
//import com.serly.modul8.Model.PostPutDelPembelian;
import com.serly.modul8.Model.PostPutDelPembelian;
import com.serly.modul8.Rest.ApiClient;
import com.serly.modul8.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.serly.modul8.R.id.menuTambahTransPembelian;

public class MainActivity extends AppCompatActivity {

//    Button btGet, btUpdate, btInsert, btDelete;
    Button btGet;
    ApiInterface mApiInterface;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btGet = (Button) findViewById(R.id.btGet);
//        btUpdate = (Button) findViewById(R.id.btUpdate);
//        btInsert = (Button) findViewById(R.id.btInsert);
//        btDelete = (Button) findViewById(R.id.btDelete);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mApiInterface  = ApiClient.getClient().create(ApiInterface.class);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<GetPembelian> pembelianCall = mApiInterface.getPembelian();
                pembelianCall.enqueue(new Callback<GetPembelian>() {
                    @Override
                    public void onResponse(Call<GetPembelian> call, Response<GetPembelian> response) {
                        List<Pembelian> pembelianList = response.body().getListDataPembelian();
                        Log.d("Retrofit Get", "Jumlah data pembelian: " + String.valueOf(pembelianList.size()));

                        mAdapter = new MyAdapter(pembelianList);
                        mRecyclerView.setAdapter(mAdapter);

                    }

                    @Override
                    public void onFailure(Call<GetPembelian> call, Throwable t) {
                        // Log error
                        Log.e("Retrofit Get", t.toString());
                    }

                });
            }
        });



//        btUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Call<PostPutDelPembelian> updatePembelianCall = mApiInterface.putPembelian("beli16","pemb11","2016-10-23","500000","tiket11");
//                updatePembelianCall.enqueue(new Callback<PostPutDelPembelian>() {
//                    @Override
//                    public void onResponse(Call<PostPutDelPembelian> call, Response<PostPutDelPembelian> response) {
//                        Log.d("Retrofit Update", "Status Update: " + String.valueOf(response.body().getStatus()));
//                    }
//
//                    @Override
//                    public void onFailure(Call<PostPutDelPembelian> call, Throwable t) {
//                        Log.d("Retrofit Update", t.getMessage());
//                    }
//                });
//            }
//        });
//
//        btInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Call<PostPutDelPembelian> postPembelianCall = mApiInterface.postPembelian("16",
//                        "pemb11","2016-10-23","500000","tiket11");
//                postPembelianCall.enqueue(new Callback<PostPutDelPembelian>() {
//                    @Override
//                    public void onResponse(Call<PostPutDelPembelian> call, Response<PostPutDelPembelian> response) {
//                        Log.d("Retrofit Insert", "Status Insert: " + String.valueOf(response.body().getStatus()));
//                    }
//
//                    @Override
//                    public void onFailure(Call<PostPutDelPembelian> call, Throwable t) {
//                        Log.d("Retrofit Insert", t.getMessage());
//                    }
//                });
//            }
//        });
//
//        btDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Call<PostPutDelPembelian> deletePembelian = mApiInterface.deletePembelian("16");
//                deletePembelian.enqueue(new Callback<PostPutDelPembelian>() {
//                    @Override
//                    public void onResponse(Call<PostPutDelPembelian> call, Response<PostPutDelPembelian> response) {
//                        Log.i("Retrofit Delete", "Status Delete: " + String.valueOf(response.body().getStatus()));
//                    }
//
//                    @Override
//                    public void onFailure(Call<PostPutDelPembelian> call, Throwable t) {
//                        Log.i("Retrofit Delete", t.getMessage());
//                    }
//                });
//            }
//        });
//
//
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIntent;
        switch (item.getItemId()) {

            case menuTambahTransPembelian:
                mIntent = new Intent(this, LayarDetail.class);
                startActivity(mIntent);
                return true;

            case R.id.menuListPembeli:
                mIntent = new Intent(this, LayarListPembeli.class);
                startActivity(mIntent);
                return true;

            case R.id.menuInsertDataPembeli:
                Intent intent = new Intent(this, LayarInsertPembeli.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}