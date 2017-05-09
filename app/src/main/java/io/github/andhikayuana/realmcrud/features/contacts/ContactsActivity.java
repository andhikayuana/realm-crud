package io.github.andhikayuana.realmcrud.features.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import io.github.andhikayuana.realmcrud.R;
import io.github.andhikayuana.realmcrud.data.DataManager;
import io.github.andhikayuana.realmcrud.data.entity.Contact;
import io.github.andhikayuana.realmcrud.features.formcontact.FormContactActivity;

public class ContactsActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    private ListView lvContacts;
    private ContactsAdapter mAdapter;
    private Button btnContactsAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        lvContacts = (ListView) findViewById(R.id.lvContacts);
        btnContactsAdd = (Button) findViewById(R.id.btnContactsAdd);

        btnContactsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactsActivity.this, FormContactActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mAdapter = new ContactsAdapter(this, DataManager.getInstance().contactAll());
        mAdapter.setListener(this);
        lvContacts.setAdapter(mAdapter);
    }

    @Override
    public void onLongClick(final Contact item) {
        new AlertDialog.Builder(this)
                .setTitle("Remove")
                .setMessage("Remove contact " + item.getName() + " ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataManager.getInstance().contactRemove(item);
                        mAdapter.remove(item);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onClick(Contact item) {
        Intent intent = new Intent(ContactsActivity.this, FormContactActivity.class);
        intent.putExtra(Contact.TAG, item);
        startActivity(intent);
    }
}
