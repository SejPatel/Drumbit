package com.example.devyani.drumbeatapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.devyani.drumbeatapplication.Activity.editprofile;
import com.example.devyani.drumbeatapplication.Adapter.CustomAdapter;
import com.example.devyani.drumbeatapplication.Interfaces.Api;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.Activity.boardclickeventrecycler;
import com.example.devyani.drumbeatapplication.listner.OnItemClickListner;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserData;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserGroupResponse;
import com.example.devyani.drumbeatapplication.utils.MyDividerItemDecoration;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devyani on 30/3/18.
 */

public class User_Profile extends Fragment {

    ProgressDialog progressDialog;
    CustomAdapter customAdapter;
    RecyclerView r1;

    Context mContext;
    ArrayList<UserData> alluserpostdata;


    public User_Profile() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.profilerecycler, container, false);

        progressDialog = new ProgressDialog(getActivity());

        r1 = (RecyclerView) rootview.findViewById(R.id.recycler_Profile);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        r1.setLayoutManager(linearLayoutManager);

        alluserpostdata = new ArrayList<>();

        customAdapter = new CustomAdapter(getContext(), alluserpostdata);
        r1.addItemDecoration(new MyDividerItemDecoration(1, getResources().getDimensionPixelOffset(R.dimen.dp_8), true));
        r1.setAdapter(customAdapter);

        customAdapter.setListner(new OnItemClickListner() {
            @Override
            public void onItemClick(int pos) {

            }

            @Override
            public void onItemClick(int pos, int type) {
                if (type == 1) {
                    Intent intent = new Intent(getActivity(), boardclickeventrecycler.class);
                    intent.putExtra("userData", alluserpostdata.get(pos));
                    startActivity(intent);
                }
            }
        });

        if (SharedPrefManager.getInstance(mContext).getStringValue(SharedPrefManager.lat)
                != "" && SharedPrefManager.getInstance(mContext).getStringValue(SharedPrefManager.lng)
                != "") {
            createboardapi();
        }

        return rootview;
    }

    public void createboardapi() {
        if (progressDialog == null && mContext != null)
            progressDialog = new ProgressDialog(mContext);

//        progressDialog.setMessage("Loading..."); // Setting Message
//        progressDialog.setTitle("Loading Post"); // Setting Title
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//        progressDialog.show(); // Display Progress Dialog
//        progressDialog.setCancelable(false);


        if ((SharedPrefManager.getInstance(mContext).getStringValue(SharedPrefManager.lat)
                != "") && (SharedPrefManager.getInstance(mContext).getStringValue(SharedPrefManager.lng)
                != "")
                && r1 != null && alluserpostdata != null && customAdapter != null) {


            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setTitle("Loading Post please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                    .build();
            Api api = retrofit.create(Api.class);
            Call<UserGroupResponse> call = api.createboardapi(SharedPrefManager.getInstance(mContext).getIntValue(SharedPrefManager.USER_ID),
                    SharedPrefManager.getInstance(mContext).getStringValue(SharedPrefManager.ACCESS_TOKEN), 1,
                    SharedPrefManager.getInstance(mContext).getStringValue(SharedPrefManager.lat)
                    , SharedPrefManager.getInstance(mContext).getStringValue(SharedPrefManager.lng));
            call.enqueue(new Callback<UserGroupResponse>() {
                @Override
                public void onResponse(Call<UserGroupResponse> call, Response<UserGroupResponse> response) {
                    UserGroupResponse userGroupResponse = response.body();
                    if (userGroupResponse.getStatus() == 1) {
                        alluserpostdata.clear();
                        alluserpostdata.addAll(response.body().data.groups);
                        customAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UserGroupResponse> call, Throwable t) {
//                progressDialog.dismiss();
                    Log.e(getActivity().getLocalClassName(), t.getMessage());
                }
            });
        }

    }
}
