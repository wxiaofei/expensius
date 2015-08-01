/*
 * Copyright (C) 2015 Mantas Varnagiris.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.mvcoding.financius.ui.tag;

import android.support.annotation.NonNull;

import com.mvcoding.financius.R;
import com.mvcoding.financius.data.model.Tag;
import com.mvcoding.financius.ui.ActivityComponent;
import com.mvcoding.financius.ui.BaseActivity;
import com.mvcoding.financius.ui.Presenter;
import com.mvcoding.financius.util.rx.RefreshEvent;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class TagsActivity extends BaseActivity<TagsPresenter.View, TagsComponent> implements TagsPresenter.View {
    @Inject TagsPresenter presenter;

    @Override protected int getLayoutId() {
        return R.layout.activity_tags;
    }

    @NonNull @Override protected TagsComponent createComponent(@NonNull ActivityComponent component) {
        return component.plus(new TagsModule(TagsPresenter.DisplayType.View));
    }

    @Override protected void inject(@NonNull TagsComponent component) {
        component.inject(this);
    }

    @NonNull @Override protected Presenter<TagsPresenter.View> getPresenter() {
        return presenter;
    }

    @NonNull @Override protected TagsPresenter.View getPresenterView() {
        return this;
    }

    @NonNull @Override public Observable<TagsPresenter.Edge> onEdgeReached() {
        return null;
    }

    @NonNull @Override public Observable<RefreshEvent> onRefresh() {
        return null;
    }

    @Override public void setDisplayType(@NonNull TagsPresenter.DisplayType displayType) {

    }

    @Override public void show(@NonNull List<Tag> tags) {

    }

    @Override public void add(int position, @NonNull List<Tag> tags) {

    }
}
