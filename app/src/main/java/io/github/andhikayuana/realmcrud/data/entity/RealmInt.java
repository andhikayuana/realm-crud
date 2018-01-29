package io.github.andhikayuana.realmcrud.data.entity;

import io.realm.RealmObject;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 7/18/17
 */

public class RealmInt extends RealmObject {

    private int val;

    public RealmInt() {
    }

    public RealmInt(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
