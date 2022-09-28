package com.example.groceryshop.Modal;

import android.view.ViewGroup;

import com.example.groceryshop.Adapter.DrawerAdapter;

public abstract  class DrawerItems<T extends DrawerAdapter.ViewHolder> {

    protected boolean isChecked;

    public abstract T createViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder);

    public com.example.groceryshop.Modal.DrawerItems<T> setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isSelectable() {
        return true;
    }
}
