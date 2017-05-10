package com.zdq.studentmanager.activity.add;

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

import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.CourseForm;
import com.zdq.studentmanager.bean.ScoreForm;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.bean.TestFrom;
import com.zdq.studentmanager.util.DateUtil;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class AddChengjiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MaterialSpinner banji,xuesheng;
    private Button save;
    private TextView title;
    private EditText chengji;
    private String data;
    private List<ClassFrom> classFromList=new ArrayList<>();
    private List<StudentForm> studentForms=new ArrayList<>();
    private TestFrom testFrom;
    private ScoreForm scoreForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chengji);
        toolbar= (Toolbar) findViewById(R.id.mydata_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        banji= (MaterialSpinner) findViewById(R.id.banji_spinner);
        xuesheng= (MaterialSpinner) findViewById(R.id.xuesheng_spinner);
        save= (Button) findViewById(R.id.save);
        title= (TextView) findViewById(R.id.title);
        chengji= (EditText) findViewById(R.id.chengji);
        testFrom=new TestFrom();
        scoreForm=new ScoreForm();
        Intent intent=getIntent();
        if (intent!=null){
            data=intent.getStringExtra("data");
            testFrom= InitConfig.gson.fromJson(data,TestFrom.class);
            title.setText(testFrom.getTestName());
            scoreForm.setTestId(testFrom.getId());
        }
        initClass();
        banji.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                initStudent(classFromList.get(position).getClassNo().toString());
                scoreForm.setScoreCls(classFromList.get(position).getClassNo());
            }
        });
        xuesheng.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                scoreForm.setStudentNo(studentForms.get(position).getStudentNo());
                scoreForm.setStudentId(studentForms.get(position).getStudentId());
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chengji.setError(null);
                if (scoreForm.getScoreCls()==null || scoreForm.getScoreCls()==0){
                    Snackbar.make(save,"请选择班级",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (scoreForm.getStudentId()==null || scoreForm.getStudentId()==0){
                    Snackbar.make(save,"请选择学生",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(chengji.getText().toString())){
                    chengji.setError("请输入分数");
                    return;
                }
                if (!DateUtil.isDouble(chengji.getText().toString())){
                    chengji.setError("请输入数字");
                    return;
                }
                if (Double.valueOf(chengji.getText().toString())>120){
                    chengji.setError("请输入正确的分数");
                    return;
                }
                scoreForm.setScoreSco(Double.valueOf(chengji.getText().toString()));
                OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.SAVESCORE).addParams("scoreGson", JsonTools.createJsonString(scoreForm)).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if ("1".equals(response)){
                            Snackbar.make(save,"添加成功",Snackbar.LENGTH_LONG).show();
                            chengji.setText("");
                            scoreForm.setScoreSco(null);
                        }else{
                            Snackbar.make(save,"添加失败",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }

    private void initClass(){
        OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.FINDALLCLASS).addParams("serachkey","").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                classFromList=InitConfig.gson.fromJson(response,new TypeToken<List<ClassFrom>>(){}.getType());
                ClassFrom a=new ClassFrom();
                a.setClassNo(0);
                a.setClassName("请选择");
                classFromList.add(0,a);
                banji.setItems(classFromList);
            }
        });
    }

    private void initStudent(String id){
        OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.FINDSTUBYCLASSID).addParams("serachkey",id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                studentForms.clear();
                studentForms=InitConfig.gson.fromJson(response,new TypeToken<List<StudentForm>>(){}.getType());
                StudentForm s=new StudentForm();
                s.setStudentNo("0");
                s.setStudentId(0);
                s.setStudentName("请选择");
                studentForms.add(0,s);
                xuesheng.setItems(studentForms);
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