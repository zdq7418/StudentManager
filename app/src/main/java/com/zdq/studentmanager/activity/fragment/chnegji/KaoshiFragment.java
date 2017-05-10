package com.zdq.studentmanager.activity.fragment.chnegji;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.add.AddChengjiActivity;
import com.zdq.studentmanager.activity.add.AddCourseActivity;
import com.zdq.studentmanager.bean.CourseForm;
import com.zdq.studentmanager.bean.TestFrom;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cc.trity.floatingactionbutton.FloatingActionButton;
import okhttp3.Call;

public class KaoshiFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CommonAdapter<TestFrom> commonAdapter;
    private List<TestFrom> list=new ArrayList<>();
    private List<CourseForm> courseFormList=new ArrayList<>();
    private MyReceiver myReceiver;
    private MaterialSpinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        InitConfig.fragmentName="kaoshifragment";
        myReceiver = new KaoshiFragment.MyReceiver();
        getActivity().registerReceiver(myReceiver, new IntentFilter("kaoshifragment"));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_kaoshi, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.kecneh_swipe_refresh);
        recyclerView= (RecyclerView) view.findViewById(R.id.kecheng_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);
        initData("");
        initSpanner();
        commonAdapter=new CommonAdapter<TestFrom>(getActivity(),R.layout.base_item,list) {
            @Override
            protected void convert(ViewHolder holder, TestFrom courseForm, int position) {
                holder.setText(R.id.baseText,courseForm.getTestName());
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent=new Intent(getActivity(),AddChengjiActivity.class);
                intent.putExtra("data", JsonTools.createJsonString(list.get(position)));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int position) {
                final List<String> strings = new ArrayList<>();
                strings.add("删除");


                StyledDialog.buildBottomItemDialog( strings, "取消",  new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int poition) {
                        if ("删除".equals(text)){
                            StyledDialog.buildIosAlert( "提示", "确认删除\""+list.get(position).getTestName()+"\"吗？",  new MyDialogListener() {
                                @Override
                                public void onFirst() {
                                    OkHttpUtils
                                            .post()
                                            .url(InitConfig.SERVICE+InitConfig.DELTEST)
                                            .addParams("courseGson", JsonTools.createJsonString(list.get(position)))
                                            .build()
                                            .execute(new StringCallback()
                                            {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    Snackbar.make(recyclerView,"网络错误",Snackbar.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    if ("1".equals(response)){
                                                        list.remove(position);
                                                        commonAdapter.notifyDataSetChanged();
                                                        Snackbar.make(recyclerView,"删除成功",Snackbar.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                }

                                @Override
                                public void onSecond() {
                                }
                            }).setBtnText("确认","取消").show();
                        }
                    }

                    @Override
                    public void onBottomBtnClick() {
                    }
                }).show();
                return false;
            }
        });
        recyclerView.setAdapter(commonAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData("");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        serachTest(courseFormList.get(position).getCourseId().toString());
    }
});

    }

    private void initData(String key){
        OkHttpUtils
                .post()//
                .addParams("serachkey",key)
                .url(InitConfig.SERVICE+InitConfig.FINDTEST)//
                .build()//
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<TestFrom> list1=InitConfig.gson.fromJson(response,new TypeToken<List<TestFrom>>(){}.getType());
                        list.clear();
                        for (TestFrom c:list1){
                            list.add(c);
                        }
                        commonAdapter.notifyDataSetChanged();
                    }

                });


    }

    private void initSpanner(){
        OkHttpUtils
                .post()//
                .addParams("serachkey","")
                .url(InitConfig.SERVICE+InitConfig.FINDCOURSE)//
                .build()//
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        courseFormList=InitConfig.gson.fromJson(response,new TypeToken<List<CourseForm>>(){}.getType());
                        spinner.setItems(courseFormList);
                    }

                });
    }

    private void serachTest(String key){
        OkHttpUtils
                .post()//
                .addParams("serachkey",key)
                .url(InitConfig.SERVICE+InitConfig.FINDTESTBYCOURSEID)//
                .build()//
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<TestFrom> list1=InitConfig.gson.fromJson(response,new TypeToken<List<TestFrom>>(){}.getType());
                        list.clear();
                        for (TestFrom c:list1){
                            list.add(c);
                        }
                        commonAdapter.notifyDataSetChanged();
                    }

                });
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = intent.getStringExtra("key");
            initData(key);
        }
    }

    @Override
    public void onResume() {
        initData("");
        super.onResume();
    }

}
