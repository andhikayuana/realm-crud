package io.github.andhikayuana.realmcrud.data.entity;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 5/9/17
 */

public class Contact extends RealmObject implements Serializable {

    public static final String TAG = "DATA_CONTACT";

    @PrimaryKey
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
