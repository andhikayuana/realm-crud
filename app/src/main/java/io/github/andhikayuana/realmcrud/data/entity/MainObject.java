package io.github.andhikayuana.realmcrud.data.entity;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 7/18/17
 */

public class MainObject extends RealmObject {

    private String name;
    private RealmList<RealmInt> ints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<RealmInt> getInts() {
        return ints;
    }

    public void setInts(RealmList<RealmInt> ints) {
        this.ints = ints;
    }
}
