package com.directparking.app.ui.accept.adapter;

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
import android.widget.Toast;

import com.directparking.app.R;
import com.directparking.app.ui.accept.StatusListener;
import com.directparking.app.ui.accept.model.AcceptItem;
import com.directparking.app.util.AlertUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

import static com.directparking.app.util.Constants.ACCEPT_STATUS;
import static com.directparking.app.util.Constants.CANCEL_STATUS;

/**
 * Created by root on 31/7/18.
 */

public class RidesListRecyclerAdapter extends RecyclerView.Adapter<RidesListRecyclerAdapter.RidesViewHolder> {
    Context mContext;
    List<AcceptItem> ridesItemList;
    StatusListener statusListener;
    Calendar calendar;

    public RidesListRecyclerAdapter(Context context, List<AcceptItem> newRides, StatusListener listener) {
        mContext = context;
        ridesItemList = newRides;
        this.statusListener = listener;
        this.calendar = Calendar.getInstance();
    }

    @NonNull
    @Override
    public RidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rideItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rides_list, null);
        return new RidesViewHolder(rideItemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RidesViewHolder holder, int position) {

        String name = ridesItemList.get(position).getFname() + " " + ridesItemList.get(position).getLname();
        int statusColor;
        String status = ridesItemList.get(position).getStatus();
        holder.tvParkingLot.setText(ridesItemList.get(position).getParking_no());
        switch (status.toLowerCase()) {
            case "ongoing":
                statusColor = ContextCompat.getColor(mContext, R.color.ongoing);
                break;
            case "upcoming":
                statusColor = ContextCompat.getColor(mContext, R.color.upcoming_color);
                break;
            case "pending":
                statusColor = ContextCompat.getColor(mContext, R.color.pending_color);
                status = "Pending";
                break;
            case "accept":
                statusColor = ContextCompat.getColor(mContext, R.color.green);
                holder.accept.setVisibility(View.INVISIBLE);
                holder.decline.setVisibility(View.INVISIBLE);
                break;
            case "accepted":
                statusColor = ContextCompat.getColor(mContext, R.color.green);
                holder.accept.setVisibility(View.INVISIBLE);
                holder.decline.setVisibility(View.INVISIBLE);
                status = "Accepted";
                break;
            case "cancelled":
                statusColor = ContextCompat.getColor(mContext, R.color.cancelled);
                holder.accept.setVisibility(View.INVISIBLE);
                holder.decline.setVisibility(View.INVISIBLE);
                status = "Cancelled";
                break;
            default:
                statusColor = ContextCompat.getColor(mContext, R.color.black);
        }

        holder.riderName.setText(name);
        holder.status.setTextColor(statusColor);
        holder.status.setText(status);
        holder.pickUpAddress.setText(ridesItemList.get(position).getPick_address());
        holder.dropAddress.setText(ridesItemList.get(position).getDrop_address());
        holder.rbUserRating.setRating(ridesItemList.get(position).getRating());


        if (!TextUtils.isEmpty(ridesItemList.get(position).getSchedule_time())) {
            try {
                calendar.setTimeInMillis(Long.parseLong(ridesItemList.get(position).getSchedule_time()));
                SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                String rideTime = df.format(calendar.getTime());
//                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
//                DateTime dateTime = formatter.parseDateTime(ridesItemList.get(position).getSchedule_date() + " " + ridesItemList.get(position).getSchedule_time());
//                CharSequence prettyFormat = DateUtils.getRelativeTimeSpanString(mContext, dateTime);
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

//        if(status == "accept")


    }

    @Override
    public int getItemCount() {
        return ridesItemList.size();
    }

    class RidesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_profile)
        CircleImageView userPic;

        @BindView(R.id.tv_rider_name)
        TextView riderName;

        @BindView(R.id.tv_ride_time)
        TextView time;

        @BindView(R.id.tv_status)
        TextView status;

        @BindView(R.id.tv_pickup)
        TextView pickUpAddress;

        @BindView(R.id.tv_drop)
        TextView dropAddress;

        @BindView(R.id.tv_accept)
        TextView accept;

        @BindView(R.id.tv_cancel)
        TextView cancel;

        @BindView(R.id.tv_decline)
        TextView decline;

        @BindView(R.id.tv_parking_lot)
        TextView tvParkingLot;

        @BindView(R.id.rating_bar_rider_rating)
        RatingBar rbUserRating;

        public RidesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cancel.setVisibility(View.GONE);
        }

        @OnClick({R.id.tv_accept, R.id.tv_cancel, R.id.tv_decline})
        void onStatusChange(View v) {
            String status;
            switch (v.getId()) {
                case R.id.tv_accept:
                    status = ACCEPT_STATUS;
                    break;
                case R.id.tv_cancel:
                    status = CANCEL_STATUS;
                    break;
                case R.id.tv_decline:
                    status = CANCEL_STATUS;
                    break;
                default:
                    status = "";

            }
            statusListener.onStatusChange(getAdapterPosition(), status);
        }
    }

}
