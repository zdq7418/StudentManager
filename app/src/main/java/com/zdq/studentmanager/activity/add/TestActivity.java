package com.zdq.studentmanager.activity.add;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.bean.CourseForm;
import com.zdq.studentmanager.bean.TestFrom;
import com.zdq.studentmanager.util.DateUtil;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;

public class TestActivity extends AppCompatActivity implements OnDateSetListener,View.OnClickListener,View.OnTouchListener {
    private Toolbar toolbar;
    private EditText testtime,testqihao,testname;
    private TextView coursename;
    private Button save;
    private String data;
    private CourseForm courseForm;
    private TestFrom testFrom;
    private TimePickerDialog mDialogYearMonthDay;
    private CardView card_test_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        toolbar= (Toolbar) findViewById(R.id.mydata_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testname= (EditText) findViewById(R.id.test_name);
        testqihao= (EditText) findViewById(R.id.test_qihao);
        testtime= (EditText) findViewById(R.id.test_time);
        coursename= (TextView) findViewById(R.id.coursename);
        card_test_time= (CardView) findViewById(R.id.card_test_time);
        save= (Button) findViewById(R.id.save);
        courseForm=new CourseForm();
        testFrom=new TestFrom();
        Intent intent=getIntent();
        data=intent.getStringExtra("data");
        if (data!=null){
            courseForm= InitConfig.gson.fromJson(data,CourseForm.class);
        }
        coursename.setText(courseForm.getCourseName());
//        testtime.setEnabled(false);
        testtime.setOnTouchListener(this);
        testtime.setInputType(InputType.TYPE_NULL);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testtime.setError(null);
                testqihao.setError(null);
                testname.setError(null);
                if (TextUtils.isEmpty(testtime.getText().toString())){
                    testtime.setError("请选择考试时间");
                    testtime.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(testqihao.getText().toString())){
                    testqihao.setError("请输入考试期号");
                    testqihao.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(testname.getText().toString())){
                    testname.setError("请输入考试名称");
                    testname.requestFocus();
                    return;
                }
                testFrom.setCourseId(courseForm.getCourseId());
                testFrom.setTestName(testname.getText().toString());
                testFrom.setTestQihao(testqihao.getText().toString());
                OkHttpUtils.post().url(InitConfig.SERVICE+InitConfig.ADDORUPTEST).addParams("testGson", JsonTools.createJsonString(testFrom)).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if ("1".equals(response)){
                            Snackbar.make(save,"保存成功",Snackbar.LENGTH_LONG).show();

                        }else{
                            Snackbar.make(save,"保存失败，请重试",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

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
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        testtime.setText(text);
        try {
            testFrom.setTestTime((DateUtil.date2TimeStamp(text,"yyyy-MM-dd HH:mm")));
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            Date d = new Date(millseconds);
            testqihao.setText(sf.format(d)+"期");
            testtime.clearFocus();
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

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId()==R.id.test_time&&event.getAction()== MotionEvent.ACTION_DOWN) {
            long tenYears = 40L * 365 * 1000 * 60 * 60 * 24L;
            mDialogYearMonthDay = new TimePickerDialog.Builder()
                    .setCallBack(this)
                    .setCancelStringId("取消")
                    .setSureStringId("确认")
                    .setTitleStringId("选择日期")
                    .setYearText("年")
                    .setMonthText("月")
                    .setDayText("日")
                    .setCyclic(false)
                    .setMinMillseconds(System.currentTimeMillis() - tenYears)
                    .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                    .setCurrentMillseconds(System.currentTimeMillis())
                    .setType(Type.ALL)
                    .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                    .setWheelItemTextSelectorColor(getResources().getColor(R.color.theme_color))
                    .setWheelItemTextSize(12)
                    .build();
            mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
        }
        return false;
    }
}
