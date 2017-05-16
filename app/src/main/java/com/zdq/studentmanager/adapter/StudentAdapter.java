package com.zdq.studentmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.add.AddStudentActivity;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by ThundeRobot on 2017/4/14.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context mContext;
    private List<StudentForm> mFrultList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                StudentForm fruit=mFrultList.get(position);
                Intent intent=new Intent(mContext, AddStudentActivity.class);
                intent.putExtra("data", fruit.getStudentAccout());
                mContext.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position=holder.getAdapterPosition();
                final List<String> strings = new ArrayList<>();
                strings.add("删除");


                StyledDialog.buildBottomItemDialog( strings, "取消",  new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, final int position) {
                        final int index=holder.getAdapterPosition();
                        if ("删除".equals(text)){
                            StyledDialog.buildIosAlert( "提示", "确认删除吗？",  new MyDialogListener() {
                                @Override
                                public void onFirst() {
                                    OkHttpUtils
                                            .post()
                                            .url(InitConfig.SERVICE+InitConfig.DELSTUANDACOU)
                                            .addParams("account", mFrultList.get(index).getStudentAccout())
                                            .build()
                                            .execute(new StringCallback()
                                            {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {
                                                    Toast.makeText(mContext,"网络错误",Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    if ("1".equals(response)){
                                                        mFrultList.remove(index);
                                                        notifyDataSetChanged();
                                                        Toast.makeText(mContext,"删除成功",Toast.LENGTH_LONG).show();
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
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentForm fruit=mFrultList.get(position);
        holder.fruitName.setText(fruit.getStudentName());
        if (fruit.getStudentUrlimage()!=null){
            Glide.with(mContext).load(InitConfig.IMAGESERVICE+fruit.getStudentUrlimage()).into(holder.fruitImage);
        }

    }

    @Override
    public int getItemCount() {
        return mFrultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView;
            fruitImage= (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName= (TextView) itemView.findViewById(R.id.fruit_name);

        }


    }
    public StudentAdapter(List<StudentForm> fruitList){
        mFrultList=fruitList;
    }

}
