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

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.mvcoding.financius.data.DataLoadApi;
import com.mvcoding.financius.data.model.Tag;
import com.mvcoding.financius.data.paging.Page;
import com.mvcoding.financius.data.paging.PageResult;
import com.mvcoding.financius.ui.ActivityScope;
import com.mvcoding.financius.ui.Presenter;
import com.mvcoding.financius.ui.PresenterView;
import com.mvcoding.financius.util.rx.RefreshEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;

@ActivityScope class TagsPresenter extends Presenter<TagsPresenter.View> {
    private final DisplayType displayType;
    private final DataLoadApi dataLoadApi;
    private final int pageSize;
    private final Scheduler uiScheduler;
    private final Scheduler ioScheduler;
    private final List<Tag> cache;

    TagsPresenter(@NonNull DisplayType displayType, @NonNull DataLoadApi dataLoadApi, @IntRange(from = 1) int pageSize, @NonNull Scheduler uiScheduler, @NonNull Scheduler ioScheduler) {
        this.displayType = displayType;
        this.dataLoadApi = dataLoadApi;
        this.pageSize = pageSize;
        this.uiScheduler = uiScheduler;
        this.ioScheduler = ioScheduler;
        cache = new ArrayList<>();
    }

    @Override protected void onViewAttached(@NonNull View view) {
        super.onViewAttached(view);
        view.setDisplayType(displayType);
        if (!cache.isEmpty()) {
            view.show(cache);
        }

        final Observable<Page> pageObservable = getPageObservable();
        dataLoadApi.loadTags(pageObservable)
                .doOnNext(this::cachePage)
                .subscribe(pageResult -> view.add(pageResult.getPage().getStart(), pageResult.getItems()));
    }

    @NonNull private Observable<Page> getPageObservable() {
        return Observable.just(new Page(0, pageSize));
    }

    private void cachePage(@NonNull PageResult<Tag> pageResult) {
        cache.addAll(pageResult.getPage().getStart(), pageResult.getItems());
    }

    @NonNull private Page getPage(@NonNull Edge edge) {
        //        if (pageResult == null) {
        //            return new Page(0, pageSize);
        //        }

        switch (edge) {
            case Start:
                return null;
            case End:
                return null;
            default:
                throw new IllegalArgumentException("Edge " + edge + " is not supported.");
        }
    }

    public enum DisplayType {
        View, Select, MultiChoice
    }

    public enum Edge {
        Start, End
    }

    public interface View extends PresenterView {
        @NonNull Observable<Edge> onEdgeReached();
        @NonNull Observable<RefreshEvent> onRefresh();
        void setDisplayType(@NonNull DisplayType displayType);
        void show(@NonNull List<Tag> tags);
        void add(int position, @NonNull List<Tag> tags);
    }
}
