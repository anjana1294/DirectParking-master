package com.directparking.app.ui.history.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.history.model.HistoryRide;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

/**
 * Created by root on 31/7/18.
 */

public class HistoryItemRecyclerAdapter extends RecyclerView.Adapter<HistoryItemRecyclerAdapter.RidesViewHolder> {
    Context mContext;
    List<HistoryRide> ridesItemList;
    Calendar calendar;

    public HistoryItemRecyclerAdapter(List<HistoryRide> newRides, Context context) {
        mContext = context;
        ridesItemList = newRides;
        this.calendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public RidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rideItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_my_trip, null);
        return new RidesViewHolder(rideItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RidesViewHolder holder, int position) {
        String name = ridesItemList.get(position).getFname() + " " + ridesItemList.get(position).getLname();
        int statusColor;
        String status = ridesItemList.get(position).getStatus();
        switch (status.toLowerCase()) {
            case "complete":
                statusColor = ContextCompat.getColor(mContext, R.color.green);
                holder.rbUserRating.setVisibility(View.VISIBLE);
                holder.rbUserRating.setRating(ridesItemList.get(position).getRating());
                status = "Completed";
                break;
            case "cancel":
                statusColor = ContextCompat.getColor(mContext, R.color.cancelled);
                holder.rbUserRating.setVisibility(View.GONE);
                status = "Cancelled";
                break;
            default:
                statusColor = ContextCompat.getColor(mContext, R.color.black);
        }

        holder.username.setText(name);
        holder.rideStatus.setTextColor(statusColor);
        holder.rideStatus.setText(status);
        holder.pickUpAddress.setText(ridesItemList.get(position).getPick_address());
        holder.dropAddress.setText(ridesItemList.get(position).getDrop_address());


        if (!TextUtils.isEmpty(ridesItemList.get(position).getSchedule_time())) {
            try {
//                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//                DateTime dateTime = formatter.parseDateTime(ridesItemList.get(position).getSchedule_date() + " " + ridesItemList.get(position).getSchedule_time());
//                CharSequence prettyFormat = DateUtils.getRelativeTimeSpanString(mContext, dateTime);

                calendar.setTimeInMillis(Long.parseLong(ridesItemList.get(position).getSchedule_time()));
                SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                String rideTime = df.format(calendar.getTime());
                if (!TextUtils.isEmpty(rideTime)) {
                    holder.time.setText(rideTime);
                }
            } catch (IllegalArgumentException e) {
                Timber.e(e);
            }
        }

        try {
            Picasso.with(mContext).load(ridesItemList.get(position).getProfile()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).fit().into(holder.userPic);
        } catch (Exception e) {
            e.printStackTrace();
            Picasso.with(mContext).load(R.mipmap.ic_launcher).fit().into(holder.userPic);
        }
    }

    @Override
    public int getItemCount() {
        return ridesItemList.size();
    }

    class RidesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_profile)
        CircleImageView userPic;

        @BindView(R.id.tv_user_name)
        TextView username;

        @BindView(R.id.tv_ride_time)
        TextView time;

        @BindView(R.id.tv_pickup)
        TextView pickUpAddress;

        @BindView(R.id.tv_drop)
        TextView dropAddress;

        @BindView(R.id.tv_ride_status)
        TextView rideStatus;

        @BindView(R.id.rb_rider_driver_rating)
        RatingBar rbUserRating;

        public RidesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
