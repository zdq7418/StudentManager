package com.zdq.studentmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.AddClassActivity;
import com.zdq.studentmanager.activity.FruitActivity;
import com.zdq.studentmanager.activity.fragment.ClassFragment;
import com.zdq.studentmanager.bean.ClassFrom;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.util.InitConfig;
import com.zdq.studentmanager.util.JsonTools;

import java.util.List;

/**
 * Created by ThundeRobot on 2017/4/14.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    private Context mContext;
    private List<ClassFrom> mFrultList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.class_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                ClassFrom fruit=mFrultList.get(position);
                Intent intent=new Intent(mContext, AddClassActivity.class);
                intent.putExtra("data", JsonTools.createJsonString(fruit));
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassFrom fruit=mFrultList.get(position);
        holder.className.setText(fruit.getClassName());
        holder.teacName.setText(fruit.getClassTeacher());
    }

    @Override
    public int getItemCount() {
        return mFrultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView className,teacName;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView;
            className= (TextView) itemView.findViewById(R.id.class_name);
            teacName= (TextView) itemView.findViewById(R.id.teac_name);

        }


    }
    public ClassAdapter(List<ClassFrom> fruitList){
        mFrultList=fruitList;
    }

}
