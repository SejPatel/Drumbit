package com.example.devyani.drumbeatapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.devyani.drumbeatapplication.Activity.CreateOwnPost;
import com.example.devyani.drumbeatapplication.Activity.MyPostEventClickRecycler;
import com.example.devyani.drumbeatapplication.Activity.boardclickeventrecycler;
import com.example.devyani.drumbeatapplication.Activity.login;
import com.example.devyani.drumbeatapplication.Adapter.MyPostFragmentAdapter;
import com.example.devyani.drumbeatapplication.Interfaces.Api;
import com.example.devyani.drumbeatapplication.R;
import com.example.devyani.drumbeatapplication.listner.OnItemClickListner;
import com.example.devyani.drumbeatapplication.model.UserGroup.User;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserData;
import com.example.devyani.drumbeatapplication.model.UserGroup.UserGroupResponse;
import com.example.devyani.drumbeatapplication.utils.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPostFragment extends Fragment {

    FloatingActionButton newpost;
    ArrayList<UserData> recyclerdata;
    ProgressDialog progressDialog;
    Context context;
    MyPostFragmentAdapter myPostFragmentAdapter;

    public MyPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootview = inflater.inflate(R.layout.mypostrecyclerview, container, false);
        RecyclerView r1;

       /* progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Loading Post"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
*/

        newpost = (FloatingActionButton) rootview.findViewById(R.id.fab_post);
        r1 = (RecyclerView) rootview.findViewById(R.id.mypost_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        r1.setLayoutManager(linearLayoutManager);

        recyclerdata = new ArrayList<>();

        myPostFragmentAdapter = new MyPostFragmentAdapter(this, recyclerdata);

        r1.setAdapter(myPostFragmentAdapter);
        myPostFragmentAdapter.setListner(new OnItemClickListner() {
            @Override
            public void onItemClick(int pos) {

            }

            @Override
            public void onItemClick(int pos, int type) {
                if (type == 1) {
                    // user = getArguments().getParcelable(String.valueOf(User.class));
                    Intent intent = new Intent(getActivity(), MyPostEventClickRecycler.class);
                    intent.putExtra("userDataMyPost", recyclerdata.get(pos));
                    startActivity(intent);
                }
            }
        });
        creategroupapi();

        newpost.setImageResource(R.drawable.add);


        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateOwnPost.class);
                startActivity(intent);
            }
        });
        return rootview;
    }

    private void creategroupapi() {

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

        Call<UserGroupResponse> call = api.creategroupapi(SharedPrefManager.getInstance(context).getIntValue(SharedPrefManager.USER_ID),
                SharedPrefManager.getInstance(context).getStringValue(SharedPrefManager.ACCESS_TOKEN),
                SharedPrefManager.getInstance(context).getIntValue(SharedPrefManager.USER_ID), "1");

        call.enqueue(new Callback<UserGroupResponse>() {
            @Override
            public void onResponse(Call<UserGroupResponse> call, Response<UserGroupResponse> response) {
                UserGroupResponse userGroupResponse = response.body();
                if (userGroupResponse.getStatus() == 1) {

                    recyclerdata.addAll(response.body().data.groups);
                    myPostFragmentAdapter.notifyDataSetChanged();
                   progressDialog.dismiss();

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserGroupResponse> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
