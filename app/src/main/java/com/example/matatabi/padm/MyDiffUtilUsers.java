package com.example.matatabi.padm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.example.matatabi.padm.model.Users;

import java.util.ArrayList;
import java.util.List;

public class MyDiffUtilUsers extends DiffUtil.Callback {
    private List<Users> newusers;
    private List<Users> oldusers;

    public MyDiffUtilUsers(List<Users> newusers, List<Users> oldusers) {
        this.newusers = newusers;
        this.oldusers = oldusers;
    }

    @Override
    public int getOldListSize() {
        return oldusers.size();
    }

    @Override
    public int getNewListSize() {
        return newusers.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItem, int newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(int oldItem, int newItem) {
        return oldusers.get(oldItem) == newusers.get(newItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Users newUsers = newusers.get(newItemPosition);
        Users oldUsers = oldusers.get(oldItemPosition);

        Bundle diff = new Bundle();
        if (!newUsers.getUsername().equals(oldUsers.getUsername())){
            diff.putString("username", newUsers.getUsername());
        }
        if (!newUsers.getPassword().equals(oldUsers.getPassword())){
            diff.putString("password", newUsers.getPassword());
        }
        if (!newUsers.getLevel().equals(oldUsers.getLevel())){
            diff.putString("level", newUsers.getLevel());
        }
        if (diff.size() == 0){
            return null;
        }
        return diff;
    }
}
