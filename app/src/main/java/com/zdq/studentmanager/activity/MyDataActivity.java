package com.zdq.studentmanager.activity;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.yanzhenjie.album.Album;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.fragment.StudentFragment;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.bean.TeacherForm;
import com.zdq.studentmanager.util.DateUtil;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import me.shaohui.bottomdialog.BottomDialog;
import okhttp3.Call;

public class MyDataActivity extends AppCompatActivity implements View.OnClickListener,OnDateSetListener {

    private Toolbar toolbar;
    private LinearLayout mydatallname,mydataLLSex,mydataLLBro,mydataLLTel,mydataLLAddr,mydataLLClas;
    private EditText editText;
    private TextView textView,mydataName,mydataTxtName,mydataTxtSex,mydataTxtBro,mydataTxtTel,mydataTxtAddr,mydataTxtClas,sexquren;
    private int viewName;
    private Button save;
    private RadioGroup radioGroup;
    private CircleImageView mydataLogo;
    private BottomDialog bottomDialog;
    private List<TeacherForm> teacherForms;
    private List<StudentForm> studentForms;
    private TeacherForm teacherForm;
    private StudentForm studentForm;
    private ClassFrom classFrom;
    TimePickerDialog mDialogYearMonthDay;
    private ArrayList<String> mImageList;
    private  ArrayList<String> pathList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        toolbar = (Toolbar) findViewById(R.id.mydata_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        save= (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        mydataLogo= (CircleImageView) findViewById(R.id.mydataLogo);
        mydataLogo.setOnClickListener(this);
        mImageList = new ArrayList<>();
        teacherForm=new TeacherForm();
        studentForm=new StudentForm();
        OkHttpUtils
                .post()
                .url(InitConfig.SERVICE+InitConfig.MYDATA)
                .addParams("account",InitConfig.accout)
                .addParams("role",InitConfig.roleId)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Snackbar.make(save, "网络错误!", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        if ("1".equals(InitConfig.roleId)){
                            if (response!=null){
                                teacherForms=gson.fromJson(response,new TypeToken<List<TeacherForm>>(){}.getType());
                                teacherForm=teacherForms.get(0);
                            }else{
                                teacherForm=new TeacherForm();
                            }
                            mydataName.setText(teacherForm.getTeacherName());
                            mydataTxtName.setText(teacherForm.getTeacherName());
                            mydataTxtTel.setText(teacherForm.getTeacherTel());
                            if (teacherForm.getTeacherUrlimage()!=null){
                                OkHttpUtils
                                        .get()//
                                        .url(InitConfig.SERVICE+teacherForm.getTeacherUrlimage())//
                                        .build()//
                                        .execute(new BitmapCallback()
                                        {

                                            @Override
                                            public void onError(Call call, Exception e, int id) {

                                            }

                                            @Override
                                            public void onResponse(Bitmap response, int id) {
                                                mydataLogo.setImageBitmap(response);
                                            }
                                        });
                            }
                        }else{
                            Map<String, String> map=new HashMap<String, String>();
                            map=gson.fromJson(response,new TypeToken<Map<String, Object>>(){}.getType());
                            String student= map.get("student");
                            String classF=  map.get("class");
                            if (classF!=null){
                                classFrom=gson.fromJson(classF,ClassFrom.class);
                            }
                            if (student!=null){
                                studentForms=gson.fromJson(student,new TypeToken<List<StudentForm>>(){}.getType());
                                studentForm=studentForms.get(0);
                            }else{
                                studentForm=new StudentForm();
                            }
                            if (classFrom!=null){
                                mydataTxtClas.setText(classFrom.getClassName());
                            }
                            mydataName.setText(studentForm.getStudentName());
                            mydataTxtName.setText(studentForm.getStudentName());
                            mydataTxtTel.setText(studentForm.getStudentTel());
                            mydataTxtAddr.setText(studentForm.getStudentAdd());
                            mydataTxtBro.setText(DateUtil.dateFormat(studentForm.getStudentBir()));
                            if (studentForm.getStudentUrlimage()!=null){
                                OkHttpUtils
                                        .get()//
                                        .url(InitConfig.SERVICE+studentForm.getStudentUrlimage())//
                                        .build()//
                                        .execute(new BitmapCallback()
                                        {

                                            @Override
                                            public void onError(Call call, Exception e, int id) {

                                            }

                                            @Override
                                            public void onResponse(Bitmap response, int id) {
                                                mydataLogo.setImageBitmap(response);
                                            }
                                        });
                            }
//                            mydataTxtBro.setText(studentForm.getStudentBir().toString());
                        }
                    }
                });
    }
    private void initView(){
        mydataLLAddr= (LinearLayout) findViewById(R.id.mydataLLAddr);
        mydataLLBro= (LinearLayout) findViewById(R.id.mydataLLBro);
        mydataLLClas= (LinearLayout) findViewById(R.id.mydataLLClas);
        mydatallname= (LinearLayout) findViewById(R.id.mydatallname);
        mydataLLSex= (LinearLayout) findViewById(R.id.mydataLLSex);
        mydataLLTel= (LinearLayout) findViewById(R.id.mydataLLTel);
        mydataName= (TextView) findViewById(R.id.mydataName);
        mydataTxtName= (TextView) findViewById(R.id.mydataTxtName);
        mydataTxtSex= (TextView) findViewById(R.id.mydataTxtSex);
        mydataTxtAddr= (TextView) findViewById(R.id.mydataTxtAddr);
        mydataTxtBro= (TextView) findViewById(R.id.mydataTxtBro);
        mydataTxtTel= (TextView) findViewById(R.id.mydataTxtTel);
        mydataTxtClas= (TextView) findViewById(R.id.mydataTxtClas);
        mydataLLBro.setOnClickListener(this);
        mydataLLAddr.setOnClickListener(this);
        mydataLLSex.setOnClickListener(this);
        mydatallname.setOnClickListener(this);
        mydataLLTel.setOnClickListener(this);
        if ("1".equals(InitConfig.roleId)){
            mydataLLTel.setVisibility(View.VISIBLE);
            mydatallname.setVisibility(View.VISIBLE);
        }else{
            mydatallname.setVisibility(View.VISIBLE);
            mydataLLTel.setVisibility(View.VISIBLE);
            mydataLLSex.setVisibility(View.VISIBLE);
            mydataLLAddr.setVisibility(View.VISIBLE);
            mydataLLBro.setVisibility(View.VISIBLE);
            mydataLLClas.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.action_search){

        }else if(id==android.R.id.home){
            MyDataActivity.this.finish();
        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.mydataLLTel){
            viewName=R.id.mydataTxtTel;
            bottomDialog= BottomDialog.create(getSupportFragmentManager())
                    .setViewListener(new BottomDialog.ViewListener() {
                        @Override
                        public void bindView(View v) {
                            initView(v);
                        }
                    })
                    .setLayoutRes(R.layout.dialog_edit_text)      // dialog layout
                    ;
            bottomDialog.show();
        }else if (id==R.id.mydataLLSex){

            viewName=R.id.mydataTxtSex;
            bottomDialog= BottomDialog.create(getSupportFragmentManager())
                    .setViewListener(new BottomDialog.ViewListener() {
                        @Override
                        public void bindView(View v) {
                            initSexView(v);
                        }
                    })
                    .setLayoutRes(R.layout.sex_layout);      // dialog layout
            bottomDialog.show();

        }else if (id==R.id.mydatallname){
            viewName=R.id.mydataTxtName;
            bottomDialog= BottomDialog.create(getSupportFragmentManager())
                    .setViewListener(new BottomDialog.ViewListener() {
                        @Override
                        public void bindView(View v) {
                            initView(v);
                        }
                    })
                    .setLayoutRes(R.layout.dialog_edit_text);      // dialog layout
            bottomDialog.show();

        }else if (id==R.id.mydataLLBro){
            viewName=R.id.mydataTxtBro;
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
        }else if (id==R.id.mydataLLAddr){
            viewName=R.id.mydataTxtAddr;
            bottomDialog= BottomDialog.create(getSupportFragmentManager())
                    .setViewListener(new BottomDialog.ViewListener() {
                        @Override
                        public void bindView(View v) {
                            initView(v);
                        }
                    })
                    .setLayoutRes(R.layout.dialog_edit_text)      // dialog layout
                   ;
            bottomDialog.show();
        }else if (id==R.id.queding){
            if (viewName==R.id.mydataTxtTel){
                mydataTxtTel.setText(editText.getText().toString());
                if ("1".equals(InitConfig.roleId)){
                    teacherForm.setTeacherTel(editText.getText().toString());
                }else {
                    studentForm.setStudentTel(editText.getText().toString());
                }
                bottomDialog.dismiss();
            }else if (viewName==R.id.mydataTxtName){
                mydataTxtName.setText(editText.getText().toString());
                mydataName.setText(editText.getText().toString());
                if ("1".equals(InitConfig.roleId)){
                    teacherForm.setTeacherName(editText.getText().toString());
                }else {
                    studentForm.setStudentName(editText.getText().toString());
                }
                bottomDialog.dismiss();
            }else if (viewName==R.id.mydataTxtAddr){
                mydataTxtAddr.setText(editText.getText().toString());
                studentForm.setStudentAdd(editText.getText().toString());
                bottomDialog.dismiss();
            }
        }else if (id==R.id.save){
            if ("1".equals(InitConfig.roleId)){
                OkHttpUtils
                        .post()
                        .url(InitConfig.SERVICE+InitConfig.UPDATETEACHER)
                        .addParams("teacherGson", JsonTools.createJsonString(teacherForm))
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
                if (pathList!=null&&pathList.size()!=0){
                    OkHttpUtils.post()//
                            .addParams("teacherGson", JsonTools.createJsonString(teacherForm))
                            .addFile("file", "messenger_01.png", new File(pathList.get(0)))//
                            .url(InitConfig.SERVICE+InitConfig.UPDATETEACHER)
                            .build()//
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {

                                }
                            });
                }
            }else{
                OkHttpUtils
                        .post()
                        .url(InitConfig.SERVICE+InitConfig.UPDATESTUDENT)
                        .addParams("studentGson", JsonTools.createJsonString(studentForm))
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
                if (pathList!=null&&pathList.size()!=0){
                    OkHttpUtils.post()//
                            .addParams("studentGson", JsonTools.createJsonString(studentForm))
                            .addFile("file", "messenger_01.png", new File(pathList.get(0)))//
                            .url(InitConfig.SERVICE+InitConfig.UPDATESTUDENT)
                            .build()//
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {

                                }
                            });
                }
            }

        }else if (id==R.id.mydataLogo){
            final List<String> strings = new ArrayList<>();
            strings.add("拍照");
            strings.add("从相册选择");


            StyledDialog.buildBottomItemDialog( strings, "取消",  new MyItemDialogListener() {
                @Override
                public void onItemClick(CharSequence text, int position) {
                    if ("拍照".equals(text)){
                        Album.camera(MyDataActivity.this)
                                .requestCode(666)
                                // .imagePath() // 指定相机拍照的路径，建议非特殊情况不要指定.
                                .start();
                    }else{
                        Album.album(MyDataActivity.this)
                                .requestCode(999) // 请求码，返回时onActivityResult()的第一个参数。
                                .title("图库") // 配置title。
                                .selectCount(9) // 最多选择几张图片。
                                .columnCount(2) // 相册展示列数，默认是2列。
                                .camera(true) // 是否有拍照功能。
                                .checkedList(mImageList) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                                .start();
                    }
                }

                @Override
                public void onBottomBtnClick() {
                }
            }).show();
        }else if (id==R.id.sexqueding){
            if (radioGroup.getCheckedRadioButtonId()==R.id.nan){
                mydataTxtSex.setText("男");
                studentForm.setStudentSex("1");
                bottomDialog.dismiss();
            }else{
                mydataTxtSex.setText("女");
                studentForm.setStudentSex("0");
                bottomDialog.dismiss();
            }
        }
    }
    private void initView(final View view) {
       editText = (EditText) view.findViewById(R.id.edit_text);
        textView= (TextView) view.findViewById(R.id.queding);
        textView.setOnClickListener(this);


    }

    private void initSexView(final View view) {
        sexquren= (TextView) view.findViewById(R.id.sexqueding);
        sexquren.setOnClickListener(this);
        radioGroup= (RadioGroup) view.findViewById(R.id.sexgroup);


    }


    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        mydataTxtBro.setText(text);
        try {
            studentForm.setStudentBir((DateUtil.date2TimeStamp(text,"yyyy-MM-dd")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 999) {
            if (resultCode == RESULT_OK) { // Successfully.
                // 不要质疑你的眼睛，就是这么简单。
                pathList = Album.parseResult(data);
                Uri uri = Uri.fromFile(new File(pathList.get(0)));
                mydataLogo.setImageURI(uri);

            } else if (resultCode == RESULT_CANCELED) { // User canceled.
                // 用户取消了操作。
            }
        }
        if(requestCode == 666) {
            if (resultCode == RESULT_OK) { // Successfully.
                // 这里的List的size肯定是1。
                pathList = Album.parseResult(data); // Parse path.
                Uri uri = Uri.fromFile(new File(pathList.get(0)));
                mydataLogo.setImageURI(uri);
            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了操作。
            }
        }
    }
}
