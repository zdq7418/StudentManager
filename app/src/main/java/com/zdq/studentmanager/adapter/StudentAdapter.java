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

import com.bumptech.glide.Glide;
import com.zdq.studentmanager.R;
import com.zdq.studentmanager.activity.FruitActivity;
import com.zdq.studentmanager.bean.Fruit;
import com.zdq.studentmanager.bean.StudentForm;
import com.zdq.studentmanager.util.InitConfig;

import java.util.List;

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
                Intent intent=new Intent(mContext, FruitActivity.class);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentForm fruit=mFrultList.get(position);
        holder.fruitName.setText(fruit.getStudentName());
        Glide.with(mContext).load(InitConfig.SERVICE+fruit.getStudentUrlimage()).into(holder.fruitImage);
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