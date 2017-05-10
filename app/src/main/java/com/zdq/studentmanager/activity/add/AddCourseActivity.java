package com.zdq.studentmanager.activity.add;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zdq.studentmanager.R;
import com.zdq.studentmanager.bean.CourseForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class AddCourseActivity extends AppCompatActivity {
    private EditText kechengname;
    private Button save,addtest;
    private CourseForm courseForm;
    private String data;
    private Toolbar toolbar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        kechengname= (EditText) findViewById(R.id.kechengname);
        save= (Button) findViewById(R.id.save);
        addtest= (Button) findViewById(R.id.addTest);
        toolbar= (Toolbar) findViewById(R.id.mydata_toolbar);
        title= (TextView) findViewById(R.id.title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        courseForm=new CourseForm();
        Intent intent=getIntent();
        data=intent.getStringExtra("data");
        if (data!=null){
            courseForm=InitConfig.gson.fromJson(data,CourseForm.class);
            title.setText("编辑课程");
            kechengname.setText(courseForm.getCourseName());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=kechengname.getText().toString();
                kechengname.setError(null);
                if (TextUtils.isEmpty(name)){
                    kechengname.setError("请输入课程名称");
                    kechengname.requestFocus();
                    return;
                }
                if (name.length()>10){
                    kechengname.setError("课程名称应小于10位长度");
                    kechengname.requestFocus();
                    return;
                }
                courseForm.setCourseName(name);
                kechengname.setEnabled(false);
                OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.ADDORUPCOURSE).addParams("courseGson", JsonTools.createJsonString(courseForm)).build().execute(new StringCallback(){
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if ("1".equals(response)){
                            Snackbar.make(save,"保存成功",Snackbar.LENGTH_LONG).show();
                            if (courseForm.getCourseId()==null){
                                kechengname.setText("");
                                courseForm=new CourseForm();
                            }
                            kechengname.setEnabled(true);
                        }else{
                            Snackbar.make(save,"保存失败",Snackbar.LENGTH_LONG).show();
                            kechengname.setEnabled(true);
                        }
                    }
                });
            }
        });
        addtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti=new Intent(AddCourseActivity.this,TestActivity.class);
                intenti.putExtra("data",JsonTools.createJsonString(courseForm));
                startActivity(intenti);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
