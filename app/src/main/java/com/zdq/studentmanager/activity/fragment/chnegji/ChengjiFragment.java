package com.zdq.studentmanager.activity.fragment.chnegji;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.CourseForm;
import com.zdq.studentmanager.bean.ScoreForm;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.bean.TeacherForm;
import com.zdq.studentmanager.bean.TestFrom;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ChengjiFragment extends Fragment {
    private MaterialSpinner banji,xuesheng,kecheng,qihao;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private CommonAdapter<List> commonAdapter;
    private List<List> list=new ArrayList<>();
    private List<StudentForm> studentForms=new ArrayList<>();
    private List<ClassFrom> classFroms=new ArrayList<>();
    private List<TestFrom> testFroms=new ArrayList<>();
    private List<CourseForm> courseForms=new ArrayList<>();
    private Map<String,Object> map=new HashMap<>();
    private Map<String,String> serachkey=new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chengji, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        banji= (MaterialSpinner) view.findViewById(R.id.banjispinner);
        xuesheng= (MaterialSpinner) view.findViewById(R.id.xueshengspinner);
        kecheng= (MaterialSpinner) view.findViewById(R.id.kechengspinner);
        qihao= (MaterialSpinner) view.findViewById(R.id.qihaospinnser);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.kecneh_swipe_refresh);
        recyclerView= (RecyclerView) view.findViewById(R.id.kecheng_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter=new CommonAdapter<List>(getActivity(),R.layout.chengji_item,list) {
            @Override
            protected void convert(ViewHolder holder, List scoreForm, int position) {
                List a=scoreForm;
//                Object [] obj= scoreForm.get(position);
                Map<Object,Object> s= (Map<Object, Object>) a.get(0);
                Map<Object,Object> c= ( Map<Object,Object>) a.get(1);
                Map<Object,Object> t= ( Map<Object,Object>) a.get(2);
                Map<Object,Object> stu= ( Map<Object,Object>) a.get(4);
                holder.setText(R.id.stuname,stu.get("studentName").toString());
                holder.setText(R.id.kecheng,c.get("courseName").toString());
                holder.setText(R.id.qihao,t.get("testQihao").toString());
                holder.setText(R.id.chengji,s.get("scoreSco").toString());
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerView.setAdapter(commonAdapter);
        initCoure();
        initTest("0");
        initClass();
        initStudent("0");
        initScore("");
        commonAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initScore(JsonTools.createJsonString(serachkey));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        kecheng.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                initTest(courseForms.get(position).getCourseId().toString());
                serachkey.put("courseId",courseForms.get(position).getCourseId().toString());
                serachkey.put("testId",testFroms.get(qihao.getSelectedIndex()).getId().toString());
                serachkey.put("classNo",classFroms.get(banji.getSelectedIndex()).getClassNo().toString());
                serachkey.put("studentId",studentForms.get(xuesheng.getSelectedIndex()).getStudentId().toString());
                initScore(JsonTools.createJsonString(serachkey));
            }
        });
        qihao.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                serachkey.put("courseId",courseForms.get(kecheng.getSelectedIndex()).getCourseId().toString());
                serachkey.put("testId",testFroms.get(position).getId().toString());
                serachkey.put("classNo",classFroms.get(banji.getSelectedIndex()).getClassNo().toString());
                serachkey.put("studentId",studentForms.get(xuesheng.getSelectedIndex()).getStudentId().toString());
                initScore(JsonTools.createJsonString(serachkey));
            }
        });
        banji.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                initStudent(classFroms.get(position).getClassNo().toString());
                serachkey.put("courseId",courseForms.get(kecheng.getSelectedIndex()).getCourseId().toString());
                serachkey.put("testId",testFroms.get(qihao.getSelectedIndex()).getId().toString());
                serachkey.put("classNo",classFroms.get(position).getClassNo().toString());
                serachkey.put("studentId",studentForms.get(xuesheng.getSelectedIndex()).getStudentId().toString());
                initScore(JsonTools.createJsonString(serachkey));
            }
        });
        xuesheng.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                serachkey.put("courseId",courseForms.get(kecheng.getSelectedIndex()).getCourseId().toString());
                serachkey.put("testId",testFroms.get(qihao.getSelectedIndex()).getId().toString());
                serachkey.put("classNo",classFroms.get(banji.getSelectedIndex()).getClassNo().toString());
                serachkey.put("studentId",studentForms.get(position).getStudentId().toString());
                initScore(JsonTools.createJsonString(serachkey));
            }
        });
    }

    private void initStudent(String classId){
        OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.FINDSTUBYCLASSID).addParams("serachkey",classId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                studentForms.clear();
                studentForms=InitConfig.gson.fromJson(response,new TypeToken<List<StudentForm>>(){}.getType());
                StudentForm s=new StudentForm();
                s.setStudentId(0);
                s.setStudentName("请选择");
                studentForms.add(0,s);
                xuesheng.setItems(studentForms);
            }
        });
    }

    private void initClass(){
        OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.FINDALLCLASS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                classFroms=InitConfig.gson.fromJson(response,new TypeToken<List<ClassFrom>>(){}.getType());
                ClassFrom c=new ClassFrom();
                c.setClassNo(0);
                c.setClassName("请选择");
                classFroms.add(0,c);
                banji.setItems(classFroms);
            }
        });
    }

    private void initTest(String coureId){
        OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.FINDTESTBYCOURSEID).addParams("serachkey",coureId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                testFroms=InitConfig.gson.fromJson(response,new TypeToken<List<TestFrom>>(){}.getType());
                TestFrom t=new TestFrom();
                t.setId(0);
                t.setTestName("请选择");
                testFroms.add(0,t);
                qihao.setItems(testFroms);
            }
        });
    }

    private void initCoure(){
        OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.FINDCOURSE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                courseForms=InitConfig.gson.fromJson(response,new TypeToken<List<CourseForm>>(){}.getType());
                CourseForm c=new CourseForm();
                c.setCourseId(0);
                c.setCourseName("请选择");
                courseForms.add(0,c);
                kecheng.setItems(courseForms);
            }
        });
    }

    private void initScore(String seachkey){
        OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.FINDSCORE).addParams("serachkey",seachkey).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                List<List> lista=InitConfig.gson.fromJson(response,new TypeToken<List<List>>(){}.getType());
                list.clear();
                for (int i=0;i<lista.size();i++){
                    list.add(lista.get(i));
                }
                commonAdapter.notifyDataSetChanged();
            }
        });
    }
}
