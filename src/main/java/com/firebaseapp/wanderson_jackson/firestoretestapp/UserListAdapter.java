package com.firebaseapp.wanderson_jackson.firestoretestapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    public List<Users> usersList;

    public    UserListAdapter(List<Users> usersList){
        this.usersList = usersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {
        holder.nameText.setText(usersList.get(position).getName());
        holder.jobText.setText(usersList.get(position).getJob());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView nameText;
        public TextView jobText;


        public ViewHolder(View itemView){
            super(itemView);
            mView= itemView;
            nameText =  mView.findViewById(R.id.textView_name);
            jobText = mView.findViewById(R.id.textView_job);
        }
    }
}
