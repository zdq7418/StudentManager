package com.zdq.studentmanager.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.bean.TeacherForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class AddClassActivity extends AppCompatActivity {
    private List<TeacherForm> teacherFormList;
    private Toolbar toolbar;
    private ClassFrom classFrom;
    private EditText teacName;
    private Button save;
    MaterialSpinner spinner;
    private String data;
    private TextView classTi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        toolbar = (Toolbar) findViewById(R.id.class_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        classTi= (TextView) findViewById(R.id.classTi);
        classFrom=new ClassFrom();
        classFrom.setTeacherId(0);
        Intent intent=getIntent();
        data=intent.getStringExtra("data");
        if (data!=null){
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            classFrom=gson.fromJson(data,ClassFrom.class);
            classTi.setText("编辑班级");
        }
        teacName= (EditText) findViewById(R.id.className);
        save= (Button) findViewById(R.id.saveClass);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
       teacName.setText(classFrom.getClassName());

        OkHttpUtils
                .post()
                .url(InitConfig.SERVICE+InitConfig.FINDALLTER)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        teacherFormList=gson.fromJson(response,new TypeToken<List<TeacherForm>>(){}.getType());
                        TeacherForm tr=new TeacherForm();
                        tr.setTeacherName("请选择");
                        tr.setTeacherId(0);
                        teacherFormList.add(0,tr);
                        spinner.setItems(teacherFormList);
                        for (int i=0;i<teacherFormList.size();i++){
                            if (teacherFormList.get(i).getTeacherId()==classFrom.getTeacherId()){
                                spinner.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                });
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<TeacherForm>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, TeacherForm item) {
                classFrom.setTeacherId(item.getTeacherId());
                classFrom.setClassTeacher(item.getTeacherName());
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(teacName.getText())){
                    Snackbar.make(toolbar, "请填写班级名称!", Snackbar.LENGTH_LONG).show();
                    return;
                }else {
                    classFrom.setClassName(teacName.getText().toString());
                }
                if (classFrom.getTeacherId()==null || 0==classFrom.getTeacherId()){
                    Snackbar.make(toolbar, "请选择班主任!", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (classFrom.getClassNo()!=null){
                    OkHttpUtils
                            .post()
                            .url(InitConfig.SERVICE+InitConfig.UPDATECLASS)
                            .addParams("classGson", JsonTools.createJsonString(classFrom))
                            .build()
                            .execute(new StringCallback()
                            {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    if ("1".equals(response)){
                                        Snackbar.make(save, "编辑成功!", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    OkHttpUtils
                                    .post()
                                    .url(InitConfig.SERVICE+InitConfig.ADDCLASS)
                                .addParams("classGson", JsonTools.createJsonString(classFrom))
                                    .build()
                                .execute(new StringCallback()
                            {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                            }

                                @Override
                                public void onResponse(String response, int id) {
                                if ("1".equals(response)){
                                    Snackbar.make(save, "保存成功!", Snackbar.LENGTH_LONG).show();
                                }
                            }
                            });
                }


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            AddClassActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
