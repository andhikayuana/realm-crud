package io.github.andhikayuana.realmcrud.features.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import io.github.andhikayuana.realmcrud.R;
import io.github.andhikayuana.realmcrud.data.DataManager;
import io.github.andhikayuana.realmcrud.data.entity.Contact;
import io.github.andhikayuana.realmcrud.data.entity.MainObject;
import io.github.andhikayuana.realmcrud.data.entity.RealmInt;
import io.github.andhikayuana.realmcrud.features.formcontact.FormContactActivity;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ContactsActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    private ListView lvContacts;
    private ContactsAdapter mAdapter;
    private Button btnContactsAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initTryRealmListPrimitive();
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

    /**
     * try realmlist primitive
     * please see https://gist.github.com/cmelchior/1a97377df0c49cd4fca9
     */
    private void initTryRealmListPrimitive() {
        Type token = new TypeToken<RealmList<RealmInt>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmInt>>() {

                    @Override
                    public void write(com.google.gson.stream.JsonWriter out, RealmList<RealmInt> value) throws IOException {

                    }

                    @Override
                    public RealmList<RealmInt> read(com.google.gson.stream.JsonReader in) throws IOException {
                        RealmList<RealmInt> list = new RealmList<RealmInt>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new RealmInt(in.nextInt()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .create();

        String jsonExample = "[\n" +
                "    { \"name\"  : \"Foo\",\n" +
                "      \"ints\" : [1, 2, 3]\n" +
                "    },\n" +
                "    { \"name\"  : \"Bar\",\n" +
                "      \"ints\" : []\n" +
                "    }\n" +
                "] ";

        // Convert JSON to objects as normal
        List<MainObject> objects = gson.fromJson(jsonExample, new TypeToken<List<MainObject>>() {
        }.getType());

        // Copy objects to Realm
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealm(objects);
        realm.commitTransaction();

        RealmResults<MainObject> all = realm.where(MainObject.class).findAll();

        List<MainObject> mainObjects = realm.copyFromRealm(all);

        Log.d("HAI", mainObjects.toString());
    }
}
