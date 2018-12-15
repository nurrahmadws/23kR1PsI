package com.example.matatabi.padm.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matatabi.padm.MyDiffUtilUsers;
import com.example.matatabi.padm.R;
import com.example.matatabi.padm.activities.EditUserActivity;
import com.example.matatabi.padm.model.Users;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private Context context;
    private List<Users> usersList;

    public UsersAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    public void insertData(List<Users> usersListt){
        MyDiffUtilUsers diffUtilUsers = new MyDiffUtilUsers(usersList, usersListt);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilUsers);

        usersList.addAll(usersListt);
        diffResult.dispatchUpdatesTo(this);
    }

    public void updateData(List<Users> newList){
        MyDiffUtilUsers diffUtilUsers = new MyDiffUtilUsers(usersList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilUsers);

        usersList.clear();
        usersList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_recyclerview_read_users, viewGroup, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int i) {
        Users users = usersList.get(i);
        usersViewHolder.txtIduser.setText(Integer.toString(users.getId_user()));
        usersViewHolder.txtUsernamee.setText(users.getUsername());
        usersViewHolder.txtLevel.setText(users.getLevel());
        usersViewHolder.txtPassword.setText(users.getPassword());
    }

//    @Override
////    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull List<Object> payloads) {
////        if (payloads.isEmpty()) {
////            super.onBindViewHolder(holder, position, payloads);
////        }else {
////            Bundle o = (Bundle) payloads.get(0);
////            for (String key : o.keySet()){
////                if (key.equals("username")){
////                    Users users = usersList.get(position);
////                    holder.txtIduser.setText(Integer.toString(users.getId_user()));
////                    holder.txtUsernamee.setText(users.getUsername());
////                    holder.txtPassword.setText(users.getPassword());
////                    holder.txtLevel.setText(users.getLevel());
////                    holder.txtUsernamee.setTextColor(Color.GREEN);
////                }
////                if (key.equals("password")){
////                    Users users = usersList.get(position);
////                    holder.txtIduser.setText(Integer.toString(users.getId_user()));
////                    holder.txtUsernamee.setText(users.getUsername());
////                    holder.txtPassword.setText(users.getPassword());
////                    holder.txtLevel.setText(users.getLevel());
////                    holder.txtPassword.setTextColor(Color.GREEN);
////                }
////                if (key.equals("level")){
////                    Users users = usersList.get(position);
////                    holder.txtIduser.setText(Integer.toString(users.getId_user()));
////                    holder.txtUsernamee.setText(users.getUsername());
////                    holder.txtPassword.setText(users.getPassword());
////                    holder.txtLevel.setText(users.getLevel());
////                    holder.txtLevel.setTextColor(Color.GREEN);
////                }
////            }
////        }
////    }
//
    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtIduser, txtUsernamee, txtLevel, txtPassword;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIduser = itemView.findViewById(R.id.txtIduser);
            txtUsernamee = itemView.findViewById(R.id.txtUsernamee);
            txtLevel = itemView.findViewById(R.id.txtLevel);
            txtPassword = itemView.findViewById(R.id.txtPassword);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String id_user = txtIduser.getText().toString();
            String username = txtUsernamee.getText().toString();
            String level = txtLevel.getText().toString();
            String password = txtPassword.getText().toString();

            Intent intent = new Intent(context, EditUserActivity.class);
            intent.putExtra("id_user", id_user);
            intent.putExtra("username", username);
            intent.putExtra("level", level);
            intent.putExtra("password", password);
            context.startActivity(intent);
        }
    }

}
