package com.zdq.studentmanager.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.bean.UserForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int REQUEST_READ_CONTACTS = 0;
    private AutoCompleteTextView emailAutoComplete;

    private TextView login_title,register_title;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
        login_title= (TextView) findViewById(R.id.login_title);
        login_title.setText("登录");
        register_title= (TextView) findViewById(R.id.register_title);
        register_title.setText("注册");
        preferences = getSharedPreferences("student", MODE_PRIVATE);
        if(preferences.getString("loginFlag", "").equals("1")){
            Intent intent=new Intent();
            intent.putExtra("roleId",preferences.getString("roleId", ""));
            intent.putExtra("account",preferences.getString("account", ""));
            InitConfig.accout=preferences.getString("account", "");
            InitConfig.roleId=preferences.getString("roleId", "");
            intent.setClass(LoginActivity.this,Main2Activity.class);
            startActivity(intent);
            this.finish();
            return;
        }
        ((DefaultLoginView)login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {

            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                String user = loginUser.getEditText().getText().toString();
                if (user.isEmpty()) {
                    loginUser.setError("请输入用户名");
                    return;
                }
                loginUser.setError("");

                String pass = loginPass.getEditText().getText().toString();
                if (pass.isEmpty()) {
                    loginPass.setError("请输入密码");
                    return;
                }
                OkHttpUtils
                        .post()
                        .url(InitConfig.SERVICE+InitConfig.LOGIN)
                        .addParams("account", user)
                        .addParams("password",pass)
                        .build()
                        .execute(new StringCallback()
                        {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Gson gson = new Gson();
                                List<UserForm> list= gson.fromJson(response,new TypeToken<List<UserForm>>(){}.getType());
                                if (list!=null&&list.size()!=0){

                                    UserForm u=list.get(0);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("loginFlag", "1");
                                    editor.putString("roleId",u.getRoleSeq().toString());
                                    editor.putString("account",u.getUserAcct());
                                    editor.commit();
                                    InitConfig.accout=u.getUserAcct();
                                    InitConfig.roleId=u.getRoleSeq().toString();
                                    Intent intent=new Intent();
                                    intent.putExtra("roleId",u.getRoleSeq());
                                    intent.putExtra("account",u.getUserAcct());
                                    intent.setClass(LoginActivity.this,Main2Activity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }else{
                                    Snackbar.make(login, "用户名或密码不正确!", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });

        ((DefaultRegisterView)login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep, Switch role) {
                String user = registerUser.getEditText().getText().toString();
                if (user.isEmpty()) {
                    registerUser.setError("请输入用户名");
                    return;
                }
                registerUser.setError("");
                String pass = registerPass.getEditText().getText().toString();
                if (pass.isEmpty()) {
                    registerPass.setError("请输入密码");
                    return;
                }
                registerPass.setError("");

                String passRep = registerPassRep.getEditText().getText().toString();
                if (!pass.equals(passRep)) {
                    registerPassRep.setError("两次密码不一致");
                    return;
                }
                registerPassRep.setError("");
                String roleId="";
                if (role.isChecked()){
                     roleId="1";
                }else{
                     roleId="2";
                }

                OkHttpUtils
                        .post()
                        .url(InitConfig.SERVICE+InitConfig.REGISTER)
                        .addParams("account",user)
                        .addParams("password",pass)
                        .addParams("role",roleId)
                        .build()
                        .execute(new StringCallback()
                        {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if ("1".equals(response)){
                                    Snackbar.make(login, "注册成功!", Snackbar.LENGTH_LONG).show();
                                    login.animateLogin();
                                }else{
                                    Snackbar.make(login, "用户名已存在!", Snackbar.LENGTH_LONG).show();
                                }
                            }


                        });


            }
        });

        emailAutoComplete = (AutoCompleteTextView) findViewById(R.id.login_user_autocomplete);
        populateAutoComplete();
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(emailAutoComplete, "Contacts permissions are needed for providing email completions.", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        emailAutoComplete.setAdapter(adapter);
    }
}
