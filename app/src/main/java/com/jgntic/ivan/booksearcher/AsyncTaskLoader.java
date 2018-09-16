package com.jgntic.ivan.booksearcher;

import android.content.Context;

/**
 * Created by Ivan on 9/16/2018.
 */

public class AsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<String> {

    String query;
    Context context;

    public AsyncTaskLoader(Context context, String query) {
        super(context);
        this.query=query;
        this.context=context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Override
    public String loadInBackground() {
        SearchForBook searchForBook=new SearchForBook(context,query);

        return searchForBook.makeSearch();
    }
}
