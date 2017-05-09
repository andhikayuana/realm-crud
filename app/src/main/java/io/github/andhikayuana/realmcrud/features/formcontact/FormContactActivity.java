package io.github.andhikayuana.realmcrud.features.formcontact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import io.github.andhikayuana.realmcrud.R;
import io.github.andhikayuana.realmcrud.data.DataManager;
import io.github.andhikayuana.realmcrud.data.entity.Contact;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 5/9/17
 */

public class FormContactActivity extends AppCompatActivity {

    private EditText etAddContactName;
    private Button btnAddContactSave;
    private Contact currentContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initView();

        initData();

    }

    private void initData() {
        currentContact = (Contact) getIntent().getSerializableExtra(Contact.TAG);
        if (currentContact != null) etAddContactName.setText(currentContact.getName());
    }

    private void initView() {
        etAddContactName = (EditText) findViewById(R.id.etAddContactName);
        btnAddContactSave = (Button) findViewById(R.id.btnAddContactSave);

        btnAddContactSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentContact == null) {
                    saveContact();
                } else {
                    updateContact();
                }

            }
        });
    }

    private void updateContact() {
        if (isValid()) {
            currentContact.setName(etAddContactName.getText().toString());
            DataManager.getInstance().contactSave(currentContact);
            Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveContact() {
        if (isValid()) {
            Contact contact = new Contact();
            contact.setId(UUID.randomUUID().toString());
            contact.setName(etAddContactName.getText().toString());
            DataManager.getInstance().contactSave(contact);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean isValid() {

        boolean valid = true;

        if (TextUtils.isEmpty(etAddContactName.getText().toString())) {
            Toast.makeText(this, "Required", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
}
