package com.zdq.studentmanager.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.fragment.ChengjiGLFragment;
import com.zdq.studentmanager.activity.fragment.ClassFragment;
import com.zdq.studentmanager.activity.fragment.StudentFragment;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.bean.TeacherForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.trity.floatingactionbutton.FloatingActionButton;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private StudentFragment studentFragment;

    private ClassFragment classFragment;
    private SharedPreferences preferences;
    private Toolbar toolbar;

    private List<TeacherForm> teacherForms;
    private List<StudentForm> studentForms;
    private TeacherForm teacherForm;
    private StudentForm studentForm;
    private ChengjiGLFragment chengjiGLFragment;
    private  CircleImageView logo;
    private TextView name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initMydata();
        studentFragment=new StudentFragment();
        toolbar.setTitle("学生信息管理");
        InitConfig.fragmentName="menu_studentMa";
        Bundle bundle=new Bundle();
        bundle.putString("key", "");
        studentFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, studentFragment)
                .commit();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.menu_studentMa);
        View headerView = navigationView.getHeaderView(0);
        logo= (CircleImageView) headerView.findViewById(R.id.imageView);
        name= (TextView) headerView.findViewById(R.id.username);
        phone= (TextView) headerView.findViewById(R.id.phone);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,MyDataActivity.class);
                startActivity(intent);
            }
        });
        studentForm=new StudentForm();
        teacherForm=new TeacherForm();
        Menu menu=navigationView.getMenu();

        if ("2".equals(InitConfig.roleId)){
            for (int i=0;i<menu.size();i++){
                if (menu.getItem(i).getItemId()==R.id.menu_studentMa || menu.getItem(i).getItemId()==R.id.menu_classMa){
                    menu.getItem(i).setEnabled(false);
                    menu.getItem(i).setVisible(false);
                }
            }
        }


    }

    private void refreshFragment(String key){
        if ("menu_studentMa".equals(InitConfig.fragmentName)){
            studentFragment=new StudentFragment();
            Bundle bundle=new Bundle();
            bundle.putString("key", key);
            studentFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, studentFragment)
                    .commit();
        }else if ("menu_classMa".equals(InitConfig.fragmentName)){

            classFragment=new ClassFragment();
            Bundle bundle=new Bundle();
            bundle.putString("key", key);
            classFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, classFragment)
                    .commit();

        }else if ("menu_studentsorec".equals(InitConfig.fragmentName)){

        }else if ("menu_studentWeiji".equals(InitConfig.fragmentName)){

        }
    }

    private void initMydata(){
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
                        Snackbar.make(toolbar, "网络错误!", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        if ("1".equals(InitConfig.roleId)){
                            if (response!=null){
                                teacherForms=gson.fromJson(response,new TypeToken<List<TeacherForm>>(){}.getType());
                                teacherForm=teacherForms.get(0);

                                if (teacherForm.getTeacherUrlimage()!=null){
                                    OkHttpUtils
                                            .post()//
                                            .url(InitConfig.SERVICE+teacherForm.getTeacherUrlimage())//
                                            .build()//
                                            .execute(new BitmapCallback()
                                            {

                                                @Override
                                                public void onError(Call call, Exception e, int id) {

                                                }

                                                @Override
                                                public void onResponse(Bitmap response, int id) {
                                                    logo.setImageBitmap(response);
                                                }
                                            });
                                }
                            }else{
                                teacherForm=new TeacherForm();
                            }
                            phone.setText(teacherForm.getTeacherTel());
                            name.setText(teacherForm.getTeacherName());
                        }else{
                            Map<String, String> map=new HashMap<String, String>();
                            map=gson.fromJson(response,new TypeToken<Map<String, Object>>(){}.getType());
                            String student= map.get("student");
                            String classF=  map.get("class");

                            if (student!=null){
                                studentForm=gson.fromJson(student,StudentForm.class);
                            }else{
                                studentForm=new StudentForm();
                            }
                            phone.setText(studentForm.getStudentTel());
                            name.setText(studentForm.getStudentName());
                            if (studentForm.getStudentUrlimage()!=null){
                                OkHttpUtils
                                        .post()//
                                        .url(InitConfig.SERVICE+studentForm.getStudentUrlimage())//
                                        .build()//
                                        .execute(new BitmapCallback()
                                        {

                                            @Override
                                            public void onError(Call call, Exception e, int id) {

                                            }

                                            @Override
                                            public void onResponse(Bitmap response, int id) {
                                                logo.setImageBitmap(response);
                                            }
                                        });
                            }
//                            mydataTxtBro.setText(studentForm.getStudentBir().toString());
                        }
                    }
                });
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        // 当SearchView获得焦点时弹出软键盘的类型，就是设置输入类型
        searchView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        // 设置回车键表示查询操作
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        // 为searchView添加事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // 输入后点击回车改变文本
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent();
                intent.setAction(InitConfig.fragmentName);
                intent.putExtra("key",query);
                sendBroadcast(intent);
                refreshFragment(query);
                return false;
            }

            // 随着输入改变文本
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.action_search){

        }else if(id==android.R.id.home){
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_studentMa) {
            studentFragment=new StudentFragment();
            InitConfig.fragmentName="menu_studentMa";
            toolbar.setTitle("学生信息管理");
                            Bundle bundle=new Bundle();
                            bundle.putString("key", "");
                            studentFragment.setArguments(bundle);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content, studentFragment)
                                    .commit();
            // Handle the camera action
        } else if (id == R.id.menu_classMa) {
            toolbar.setTitle("班级信息管理");
            InitConfig.fragmentName="menu_classMa";
            classFragment=new ClassFragment();
            Bundle bundle=new Bundle();
            bundle.putString("key", "");
            classFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, classFragment)
                    .commit();

        } else if (id == R.id.menu_studentsorec) {
            toolbar.setTitle("学生成绩管理");
            InitConfig.fragmentName="menu_studentsorec";
            chengjiGLFragment=new ChengjiGLFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, chengjiGLFragment)
                    .commit();
        } else if (id == R.id.menu_studentWeiji) {
            toolbar.setTitle("违纪信息管理");
            InitConfig.fragmentName="menu_studentWeiji";

        } else if (id == R.id.menu_setting) {
            StyledDialog.buildIosAlert( "提示", "确认注销吗？",  new MyDialogListener() {
                @Override
                public void onFirst() {
                    preferences = getSharedPreferences("student", MODE_PRIVATE);
                    if(preferences.getString("loginFlag", "").equals("1")){
                        Intent intent=new Intent();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("loginFlag", "0");
                        editor.putString("roleId","");
                        editor.putString("account","");
                        InitConfig.accout="";
                        InitConfig.roleId="";
                        editor.commit();
                        intent.setClass(Main2Activity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }

                @Override
                public void onSecond() {
                }
            }).setBtnText("确认","取消").show();

        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        initMydata();
        refreshFragment("");
        super.onResume();
    }

}
