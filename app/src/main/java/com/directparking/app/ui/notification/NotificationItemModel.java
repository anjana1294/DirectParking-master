package com.directparking.app.ui.notification;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseHolder;
import com.directparking.app.ui.base.BaseView;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import timber.log.Timber;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.item_notification_list)
public abstract class NotificationItemModel extends EpoxyModelWithHolder<NotificationItemModel.Holder> {

    @EpoxyAttribute BaseView view;
    @EpoxyAttribute String title;
    @EpoxyAttribute String desc;
    @EpoxyAttribute String timestamp;
    @EpoxyAttribute(DoNotHash) View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        if (!TextUtils.isEmpty(title)) {
            holder.title.setText(title);
        }

        holder.desc.setText(desc);

        if (!TextUtils.isEmpty(timestamp)) {
            try {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
                DateTime dateTime = formatter.parseDateTime(timestamp);
                CharSequence prettyFormat = DateUtils.getRelativeTimeSpanString(view.context(), dateTime);
                if (!TextUtils.isEmpty(prettyFormat)) {
                    holder.timestamp.setText(prettyFormat);
                }
            } catch (IllegalArgumentException e) {
                Timber.e(e);
            }
        }

        holder.getItemView().setOnClickListener(clickListener);
    }

    static class Holder extends BaseHolder {

        @BindView(R.id.tv_title) TextView title;
        @BindView(R.id.tv_desc) TextView desc;
        @BindView(R.id.tv_timestamp) TextView timestamp;

    }
}