package com.diodev.chatapp.contactlist.ui.adapters;

import com.diodev.chatapp.entities.User;

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
