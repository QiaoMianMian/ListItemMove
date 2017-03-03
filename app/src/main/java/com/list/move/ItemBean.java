package com.list.move;


import java.io.Serializable;

public class ItemBean implements Serializable {
    private int type;
    private String tag;
    private int resId1;
    private int resId2;
    private String name;
    private String desc;
    private boolean isAdd;

    public ItemBean(int type, String tag, int resId1, int resId2, String name, String desc, boolean isAdd) {
        this.type = type;
        this.tag = tag;
        this.resId1 = resId1;
        this.resId2 = resId2;
        this.name = name;
        this.desc = desc;
        this.isAdd = isAdd;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResId1() {
        return resId1;
    }

    public void setResId1(int resId1) {
        this.resId1 = resId1;
    }

    public int getResId2() {
        return resId2;
    }

    public void setResId2(int resId2) {
        this.resId2 = resId2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "resId1=" + resId1 +
                ", resId2=" + resId2 +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", isAdd=" + isAdd +
                '}';
    }
}
