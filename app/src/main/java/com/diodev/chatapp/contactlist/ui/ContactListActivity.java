package com.diodev.chatapp.contactlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.diodev.chatapp.R;
import com.diodev.chatapp.addcontact.ui.AddContactFragment;
import com.diodev.chatapp.contactlist.ContactListPresenter;
import com.diodev.chatapp.contactlist.ContactListPresenterImpl;
import com.diodev.chatapp.contactlist.ui.adapters.ContactListAdapter;
import com.diodev.chatapp.contactlist.ui.adapters.OnItemClickListener;
import com.diodev.chatapp.entities.User;
import com.diodev.chatapp.lib.GlideImageLoader;
import com.diodev.chatapp.lib.ImageLoader;
import com.diodev.chatapp.login.ui.LoginActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;

    private ContactListAdapter adapter;
    private ContactListPresenter contactListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        contactListPresenter = new ContactListPresenterImpl(this);
        contactListPresenter.onCreate();
        setupToolbar();

        setupAdapter();
        setupRecyclerView();
    }

    private void setupAdapter() {
        ImageLoader loader = new GlideImageLoader(getApplicationContext());
        adapter = new ContactListAdapter(new ArrayList<User>(), loader, this);
    }

    private void setupRecyclerView() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    private void setupToolbar() {
        toolbar.setTitle(contactListPresenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            contactListPresenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        contactListPresenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        contactListPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        contactListPresenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.fab)
    public void addContact(){
        new AddContactFragment().show(getSupportFragmentManager(), getString(R.string.addcontact_message_title));
    }

    @Override
    public void onContactAdded(User user) {
        adapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        adapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(User user) {
        contactListPresenter.removeContact(user.getEmail());
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
    }
}
