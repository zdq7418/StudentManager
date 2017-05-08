package com.zdq.studentmanager.activity.fragment.chnegji;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.add.AddClassActivity;
import com.zdq.studentmanager.activity.add.AddCourseActivity;
import com.zdq.studentmanager.bean.CourseForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

import cc.trity.floatingactionbutton.FloatingActionButton;
import okhttp3.Call;

public class KechengFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CommonAdapter<CourseForm> commonAdapter;
    private List<CourseForm> list=new ArrayList<>();
    private MyReceiver myReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        InitConfig.fragmentName="kechengfragment";
        myReceiver = new KechengFragment.MyReceiver();
        getActivity().registerReceiver(myReceiver, new IntentFilter("kechengfragment"));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_kecheng, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.kecneh_swipe_refresh);
        recyclerView= (RecyclerView) view.findViewById(R.id.kecheng_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData("");
        commonAdapter=new CommonAdapter<CourseForm>(getActivity(),R.layout.base_item,list) {
            @Override
            protected void convert(ViewHolder holder, CourseForm courseForm, int position) {
                holder.setText(R.id.baseText,courseForm.getCourseName());
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent=new Intent(getActivity(),AddCourseActivity.class);
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
                            StyledDialog.buildIosAlert( "提示", "确认删除\""+list.get(position).getCourseName()+"\"吗？",  new MyDialogListener() {
                                @Override
                                public void onFirst() {
                                    OkHttpUtils
                                            .post()
                                            .url(InitConfig.SERVICE+InitConfig.DELCOURSE)
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
        final FloatingActionButton fab= (FloatingActionButton) view.findViewById(R.id.fab_action_a);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCourseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData(String key){
        OkHttpUtils
                .post()//
                .addParams("serachkey",key)
                .url(InitConfig.SERVICE+InitConfig.FINDCOURSE)//
                .build()//
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<CourseForm> list1=InitConfig.gson.fromJson(response,new TypeToken<List<CourseForm>>(){}.getType());
                        list.clear();
                        for (CourseForm c:list1){
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
