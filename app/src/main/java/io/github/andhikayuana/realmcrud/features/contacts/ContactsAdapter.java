package io.github.andhikayuana.realmcrud.features.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.github.andhikayuana.realmcrud.R;
import io.github.andhikayuana.realmcrud.data.entity.Contact;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 5/9/17
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private ContactsAdapterListener mListener;

    public ContactsAdapter(@NonNull Context context, @NonNull List<Contact> contacts) {
        super(context, 0, contacts);
    }

    public void setListener(ContactsAdapterListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);

        ViewHolder holder = new ViewHolder(convertView);
        holder.bind(getItem(position));

        return convertView;
    }

    public interface ContactsAdapterListener {
        void onLongClick(Contact item);

        void onClick(Contact item);
    }

    public class ViewHolder {

        private final TextView tvItemContactName;
        private final LinearLayout llItemContact;

        public ViewHolder(View v) {
            tvItemContactName = (TextView) v.findViewById(R.id.tvItemContactName);
            llItemContact = (LinearLayout) v.findViewById(R.id.llItemContact);
        }

        public void bind(final Contact item) {
            tvItemContactName.setText(item.getName());

            llItemContact.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onLongClick(item);
                    return true;
                }
            });
            llItemContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(item);
                }
            });
        }
    }
}
