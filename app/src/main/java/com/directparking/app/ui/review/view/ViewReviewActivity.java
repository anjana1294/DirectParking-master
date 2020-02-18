package com.directparking.app.ui.review.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.custom.EmptyRecyclerView;
import com.directparking.app.ui.review.view.adapter.ReviewListRecyclerAdapter;
import com.directparking.app.ui.review.view.model.ReviewItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewReviewActivity extends BaseActivity implements ViewReviewView {

    @Inject
    ViewReviewPresenter presenter;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView rvReviews;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private ReviewListRecyclerAdapter reviewListRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);
        ButterKnife.bind(this);
        ((BaseApplication) getApplication()).getViewReviewComponent().inject(this);
        String driverId = getIntent().getExtras().getString("id");
        presenter.setView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        rvReviews.setLayoutManager(layoutManager);
        rvReviews.addItemDecoration(new DividerItemDecoration(context(), DividerItemDecoration.VERTICAL));
        rvReviews.setHasFixedSize(true);
        rvReviews.setEmptyView(emptyView);
        presenter.fetchReviewList(driverId);
        swipeLayout.setOnRefreshListener(() -> presenter.fetchReviewList(driverId));

        toolbar.setTitle(getString(R.string.title_view_review_rating));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvReviews.setVisibility(View.GONE);
    }

    @Override
    public void showReviewList(List<ReviewItem> reviewItemList) {
        reviewListRecyclerAdapter = new ReviewListRecyclerAdapter(this, reviewItemList);
        rvReviews.setAdapter(reviewListRecyclerAdapter);
        emptyView.setVisibility(View.GONE);
        rvReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        ((BaseApplication) getApplication()).releaseViewReviewComponent();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
