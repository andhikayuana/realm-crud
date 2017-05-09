package io.github.andhikayuana.realmcrud.data;

import java.util.List;

import io.github.andhikayuana.realmcrud.data.entity.Contact;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 5/9/17
 */

public class DataManager {

    private static final DataManager ourInstance = new DataManager();

    public DataManager() {
    }

    public static DataManager getInstance() {
        return ourInstance;
    }

    public List<Contact> contactAll() {
        RealmResults<Contact> realmQuery = Realm.getDefaultInstance()
                .where(Contact.class)
                .findAllSorted("name");
        return Realm.getDefaultInstance().copyFromRealm(realmQuery);
    }

    public void contactSave(final Contact contact) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(contact);
            }
        });
    }

    public void contactRemove(final Contact item) {
        final Contact contactQuery = Realm.getDefaultInstance()
                .where(Contact.class)
                .equalTo("id", item.getId())
                .findFirst();
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                contactQuery.deleteFromRealm();
            }
        });
    }
}
