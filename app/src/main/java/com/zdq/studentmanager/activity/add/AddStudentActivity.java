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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.bean.TeacherForm;
import com.zdq.studentmanager.bean.UserForm;
import com.zdq.studentmanager.util.DateUtil;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zdq.studentmanager.util.MD5;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class AddStudentActivity extends AppCompatActivity implements OnDateSetListener,View.OnClickListener,View.OnFocusChangeListener {
    private Toolbar toolbar;
    private List<ClassFrom> classFromList;
    private EditText stuname,stuaccount,stupwd,stushengri,stutel,stuaddr,stuno;
    private RadioGroup sex;
    private RadioButton nan,nv;
    private MaterialSpinner stuclass;
    private UserForm userForm;
    private Button save;
    private StudentForm studentForm;
    private TimePickerDialog mDialogYearMonthDay;
    private ClassFrom classFrom;
    private String data;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        toolbar = (Toolbar) findViewById(R.id.mydata_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Intent intent=getIntent();

        studentForm=new StudentForm();
        userForm=new UserForm();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stuaccount= (EditText) findViewById(R.id.stuaccount);
        stuname= (EditText) findViewById(R.id.stuname);
        stuclass= (MaterialSpinner) findViewById(R.id.banji);
        stupwd= (EditText) findViewById(R.id.stupwd);
        stushengri= (EditText) findViewById(R.id.shengri);
        stutel= (EditText) findViewById(R.id.stutel);
        stuaddr= (EditText) findViewById(R.id.stuaddr);
        stuno= (EditText) findViewById(R.id.stuno);
        sex= (RadioGroup) findViewById(R.id.sex);
        nan= (RadioButton) findViewById(R.id.nan);
        nv= (RadioButton) findViewById(R.id.nv);
        save= (Button) findViewById(R.id.save);
        title= (TextView) findViewById(R.id.title);
        stushengri.setText(DateUtil.now("yyyy-MM-dd"));
//        stushengri.setOnClickListener(this);
        stushengri.setOnFocusChangeListener(this);
        stushengri.setInputType(InputType.TYPE_NULL);
        save.setOnClickListener(this);
        OkHttpUtils
                .post()
                .url(InitConfig.SERVICE+InitConfig.FINDALLCLASS)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        classFromList=gson.fromJson(response,new TypeToken<List<ClassFrom>>(){}.getType());
                        ClassFrom tr=new ClassFrom();
                        tr.setClassName("请选择");
                        tr.setTeacherId(0);
                        tr.setClassNo(0);
                        classFromList.add(0,tr);
                        stuclass.setItems(classFromList);
                        /*for (int i=0;i<classFromList.size();i++){
                            if (classFromList.get(i).getTeacherId()==classFrom.getTeacherId()){
                                spinner.setSelectedIndex(i);
                                break;
                            }
                        }*/
                    }
                });
        stuclass.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<ClassFrom>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, ClassFrom item) {
//                classFrom.setTeacherId(item.getTeacherId());
//                classFrom.setClassTeacher(item.getTeacherName());
                studentForm.setStudentCla(item.getClassNo());

            }
        });


        if (intent!=null){
            data=intent.getStringExtra("data");
            if (data!=null){
                title.setText("查看详情");
                initData(data);
            }

        }

    }

    public void initData(String account){
        OkHttpUtils
                .post()
                .url(InitConfig.SERVICE+InitConfig.MYDATA)
                .addParams("account",data)
                .addParams("role","2")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Map<String ,String> map=new HashMap<String, String>();
                        map=InitConfig.gson.fromJson(response,new TypeToken<Map<String,String>>(){}.getType());
                       if (map!=null){
                           studentForm=new StudentForm();
                           studentForm=InitConfig.gson.fromJson(map.get("student"),StudentForm.class);
                           classFrom=InitConfig.gson.fromJson(map.get("class"),ClassFrom.class);
                           userForm=InitConfig.gson.fromJson(map.get("user"),UserForm.class);
                            stuaccount.setText(userForm.getUserAcct());
                           stuname.setText(studentForm.getStudentName());
                           stushengri.setText(DateUtil.dateFormat(studentForm.getStudentBir()));
                           stuaddr.setText(studentForm.getStudentAdd());
                           stutel.setText(studentForm.getStudentTel());
                           stuno.setText(studentForm.getStudentNo());
                           if ("1".equals(studentForm.getStudentSex())){
                               nan.setChecked(true);
                           }else{
                               nv.setChecked(true);
                           }
                           if (classFromList!=null&&classFromList.size()>0){
                               for (int i=1;i<classFromList.size();i++){
                                   if (studentForm.getStudentCla()==classFromList.get(i).getClassNo()){
                                       stuclass.setSelectedIndex(i);
                                       break;
                                   }
                               }
                           }

                       }
                    }
                });

    }
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        stushengri.setText(text);
        try {
            studentForm.setStudentBir((DateUtil.date2TimeStamp(text,"yyyy-MM-dd")));
            stushengri.clearFocus();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.save){
            stuaccount.setError(null);
            stuname.setError(null);
            stuno.setError(null);
            if (TextUtils.isEmpty(stuaccount.getText().toString())){
                stuaccount.setError("请输入学生账号");
                stuaccount.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(stuno.getText().toString())){
                stuno.setError("请输入学号");
                stuno.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(stuname.getText().toString())){
                stuname.setError("请输入学生姓名");
                stuname.requestFocus();
                return;
            }
            if (studentForm.getStudentCla()==0){
                Snackbar.make(save,"请选择班级",Snackbar.LENGTH_LONG).show();
                return;
            }
            userForm.setUserAcct(stuaccount.getText().toString());
            if (userForm.getUserId()==null){
                userForm.setPasswd(stupwd.getText().toString());
            }else {
                if (!"888888".equals(stupwd.getText().toString())){
                    userForm.setPasswd(MD5.getMd5(stupwd.getText().toString()));
                }
            }
            userForm.setRoleSeq(2);
            studentForm.setStudentSex(sex.getCheckedRadioButtonId()==R.id.nan?"1":"0");
            studentForm.setStudentAdd(stuaddr.getText().toString()==null?"":stuaddr.getText().toString());
            studentForm.setStudentTel(stutel.getText().toString()==null?"":stutel.getText().toString());
            studentForm.setStudentNo(stuno.getText().toString());
            studentForm.setStudentName(stuname.getText().toString());
            studentForm.setStudentAccout(stuaccount.getText().toString());
            try {
                studentForm.setStudentBir((DateUtil.date2TimeStamp(stushengri.getText().toString(),"yyyy-MM-dd")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Map<String, String> map=new HashMap<>();
            map.put("user", JsonTools.createJsonString(userForm));
            map.put("student",JsonTools.createJsonString(studentForm));
            OkHttpUtils
                    .post()
                    .addParams("studentGson",JsonTools.createJsonString(map))
                    .url(InitConfig.SERVICE+InitConfig.ADDSTUDENT)
                    .build()
                    .execute(new StringCallback()
                    {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if ("1".equals(response)){
                                Snackbar.make(save,"保存成功",Snackbar.LENGTH_LONG).show();
                                stuname.setText("");
                                stuaddr.setText("");
                                stuaccount.setText("");
                                stushengri.setText("");
                                stutel.setText("");
                                stuno.setText("");
                                stupwd.setText("888888");
                                stuclass.setSelectedIndex(0);
                                studentForm=new StudentForm();
                            }else{
                                Snackbar.make(save,"账户名已存在",Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id=v.getId();
        if (id==R.id.shengri&&hasFocus){
            long tenYears = 40L * 365 * 1000 * 60 * 60 * 24L;
            mDialogYearMonthDay = new TimePickerDialog.Builder()
                    .setCallBack(this)
                    .setCancelStringId("取消")
                    .setSureStringId("确认")
                    .setTitleStringId("选择生日")
                    .setYearText("年")
                    .setMonthText("月")
                    .setDayText("日")
                    .setCyclic(false)
                    .setMinMillseconds(System.currentTimeMillis()-tenYears)
                    .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                    .setCurrentMillseconds(System.currentTimeMillis())
                    .setType(Type.YEAR_MONTH_DAY)
                    .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                    .setWheelItemTextSelectorColor(getResources().getColor(R.color.theme_color))
                    .setWheelItemTextSize(12)
                    .build();
            mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
            stushengri.clearFocus();
        }
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
