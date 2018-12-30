package com.example.matatabi.padm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.example.matatabi.padm.model.Users;

import java.util.ArrayList;
import java.util.List;

public class MyDiffUtilUsers extends DiffUtil.Callback {
    private List<Users> newListUser;
    private List<Users> oldListUser;

    public MyDiffUtilUsers(List<Users> newListUser, List<Users> oldListUser) {
        this.newListUser = newListUser;
        this.oldListUser = oldListUser;
    }

    @Override
    public int getOldListSize() {
        return oldListUser != null ? oldListUser.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newListUser != null ? newListUser.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Users newUser = newListUser.get(newItemPosition);
        Users oldUser = oldListUser.get(oldItemPosition);

        Bundle diff = new Bundle();
        if (!newUser.getUsername().equals(oldUser.getUsername())){
            diff.putString("username", newUser.getUsername());
        }
        if (!newUser.getPassword().equals(oldUser.getPassword())){
            diff.putString("password", newUser.getPassword());
        }
        if (!newUser.getLevel().equals(oldUser.getLevel())){
            diff.putString("level", newUser.getLevel());
        }
        if (diff.size()==0){
            return null;
        }
        return diff;
    }
}
