package com.zdq.studentmanager.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.yanzhenjie.album.Album;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.MyDataActivity;
import com.zdq.studentmanager.adapter.ClassAdapter;
import com.zdq.studentmanager.adapter.StudentAdapter;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.lfrecyclerview.LFRecyclerView;
import me.leefeng.lfrecyclerview.OnItemClickListener;
import me.shaohui.bottomdialog.BottomDialog;
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
        recyclerView.setOnItemClickListener(this);// 条目点击,点击和长按监听
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

    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onLongClick(final int po) {
        final List<String> strings = new ArrayList<>();
        strings.add("删除");


        StyledDialog.buildBottomItemDialog( strings, "取消",  new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                if ("删除".equals(text)){
                    StyledDialog.buildIosAlert( "提示", "确认删除吗？",  new MyDialogListener() {
                        @Override
                        public void onFirst() {
                            OkHttpUtils
                                    .post()
                                    .url(InitConfig.SERVICE+InitConfig.DELCLASS)
                                    .addParams("classGson", JsonTools.createJsonString(fruitList.get(po)))
                                    .build()
                                    .execute(new StringCallback()
                                    {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            if ("1".equals(response)){
                                                fruitList.remove(po);
                                                fruitAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void onRecyclerViewScrollChange(View view, int i, int i1) {

    }
}
