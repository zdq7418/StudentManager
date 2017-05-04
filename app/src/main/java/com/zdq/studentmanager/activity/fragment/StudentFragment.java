package com.zdq.studentmanager.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.adapter.FruitAdapter;
import com.zdq.studentmanager.adapter.StudentAdapter;
import com.zdq.studentmanager.bean.Fruit;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.bean.TeacherForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cc.trity.floatingactionbutton.FloatingActionButton;
import okhttp3.Call;


/**
 * Created by ThundeRobot on 2017/4/22.
 */

public class StudentFragment extends Fragment {


    private List<StudentForm> fruitList=new ArrayList<>();
    private StudentAdapter fruitAdapter;
    String key="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.student_fragment, container, false);
        Bundle bundle = getArguments();
        key=bundle.getString("key");
        OkHttpUtils
                .post()
                .url(InitConfig.SERVICE+InitConfig.FINDALLSTU)
                .addParams("serachkey",key)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        fruitList.clear();
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        fruitList=gson.fromJson(response,new TypeToken<List<StudentForm>>(){}.getType());
                        final RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
                        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
                        recyclerView.setLayoutManager(layoutManager);
                        fruitAdapter=new StudentAdapter(fruitList);
                        recyclerView.setAdapter(fruitAdapter);
                    }
                });

        return view;
    }


}
