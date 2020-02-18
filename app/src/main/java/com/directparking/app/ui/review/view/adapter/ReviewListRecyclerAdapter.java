package com.directparking.app.ui.review.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.review.view.model.ReviewItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

/**
 * Created by root on 31/7/18.
 */

public class ReviewListRecyclerAdapter extends RecyclerView.Adapter<ReviewListRecyclerAdapter.ViewReviewViewHolder> {
    Context mContext;
    List<ReviewItem> reviewItems;
    Calendar calendar;

    public ReviewListRecyclerAdapter(Context context, List<ReviewItem> reviewItems) {
        mContext = context;
        this.reviewItems = reviewItems;
        this.calendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public ViewReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rideItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_review, null);
        return new ViewReviewViewHolder(rideItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewReviewViewHolder holder, int position) {

        holder.userName.setText(reviewItems.get(position).getFname() + " " + reviewItems.get(position).getLname());
        holder.review.setText(reviewItems.get(position).getReview());

        if (!TextUtils.isEmpty(reviewItems.get(position).getTime())) {
            try {
               // calendar.setTimeInMillis(Long.parseLong(reviewItems.get(position).getTime()));
                Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reviewItems.get(position).getTime());

                SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                String rideTime = df.format(dateTime.getTime());
                if (!TextUtils.isEmpty(rideTime)) {
                    holder.reviewTime.setText(rideTime);
                }
            } catch (IllegalArgumentException e) {
                Timber.e(e);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.userRating.setRating(reviewItems.get(position).getRating());

        try {
            Picasso.with(mContext).load(reviewItems.get(position).getProfile()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).fit().into(holder.userPic);
        } catch (Exception e) {
            e.printStackTrace();
            Picasso.with(mContext).load(R.mipmap.ic_launcher).fit().into(holder.userPic);
        }
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    class ViewReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_user_profile)
        CircleImageView userPic;

        @BindView(R.id.tv_user_name)
        TextView userName;

        @BindView(R.id.tv_timestamp)
        TextView reviewTime;

        @BindView(R.id.tv_review)
        TextView review;

        @BindView(R.id.ratingbar_user_rating)
        RatingBar userRating;

        public ViewReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
