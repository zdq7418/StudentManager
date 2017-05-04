package com.zdq.studentmanager.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.adapter.ClassAdapter;
import com.zdq.studentmanager.adapter.StudentAdapter;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.lfrecyclerview.LFRecyclerView;
import me.leefeng.lfrecyclerview.OnItemClickListener;
import okhttp3.Call;

/**
 * Created by ThundeRobot on 2017/4/22.
 */

public class ClassFragment extends Fragment implements LFRecyclerView.LFRecyclerViewListener,OnItemClickListener,LFRecyclerView.LFRecyclerViewScrollChange {
    private List<ClassFrom> fruitList=new ArrayList<>();
    private ClassAdapter fruitAdapter;
    String key="";
    private LFRecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.class_fragment, container, false);
        Bundle bundle = getArguments();
        key=bundle.getString("key");
        recyclerView= (LFRecyclerView) view.findViewById(R.id.recycleview);
        recyclerView.setRefresh(false);
        recyclerView.setLoadMore(true);//设置为可上拉加载,默认false
        recyclerView.setLFRecyclerViewListener(this);
        recyclerView.setNoDateShow();
        fruitAdapter=new ClassAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);
        OkHttpUtils
                .post()
                .url(InitConfig.SERVICE+InitConfig.FINDALLCLASS)
                .addParams("serachkey",key)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        fruitAdapter=new ClassAdapter(fruitList);
                        fruitAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        List<ClassFrom> fruitLists=gson.fromJson(response,new TypeToken<List<ClassFrom>>(){}.getType());
                        for (ClassFrom c:fruitLists){
                            fruitList.add(c);
                        }
                        fruitAdapter.notifyDataSetChanged();

                    }
                });
        return view;
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.stopLoadMore();
//                list.add(list.size(), "leefeng.me" + "==onLoadMore");

            }
        }, 2000);
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onLongClick(int po) {

    }

    @Override
    public void onRecyclerViewScrollChange(View view, int i, int i1) {

    }
}
