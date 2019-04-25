package com.example.user.training;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<JSONResponse> jsonResponses;
    private Api api;
    Adapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recId);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeId);

        jsonResponses = new ArrayList<>();
        api = ApiUtils.getApi();
        adapter = new Adapter(jsonResponses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadInfo();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               loadInfo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }
    public void loadInfo(){
        if (networkState(MainActivity.this)==true) {
            load();

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    remove(viewHolder.getAdapterPosition());
                }
            });
            itemTouchHelper.attachToRecyclerView(recyclerView);
        } else {
            Toast.makeText(this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();
        }
    }
    private void remove(int i){
        jsonResponses.remove(i);
        adapter.notifyDataSetChanged();
    }
    public void load(){
        Call<List<JSONResponse>> call = api.getData();
        call.enqueue(new Callback<List<JSONResponse>>() {
            @Override
            public void onResponse(Call<List<JSONResponse>> call, Response<List<JSONResponse>> response) {
                if (response.isSuccessful()){
                    jsonResponses.addAll(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                else {
                    Toast.makeText(MainActivity.this, "ERROR "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JSONResponse>> call, Throwable t) {

            }
        });
    }

    public boolean networkState(Context context){
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting()) {
                return true;
            } else
                return false;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
