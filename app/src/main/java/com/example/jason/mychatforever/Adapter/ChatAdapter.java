package com.example.jason.mychatforever.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jason.mychatforever.Data.Chat;
import com.example.jason.mychatforever.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ChatAdapter extends RecyclerView.Adapter {
    public static final String TAG_LOCAL_DATA = "myLocalData";
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<Chat> mMessageList;
    SharedPreferences mylocaldata;

    public ChatAdapter(Context context, List<Chat> messageList) {
        mContext = context;
        mMessageList = messageList;
        mylocaldata = context.getSharedPreferences(TAG_LOCAL_DATA, MODE_PRIVATE);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Chat message = mMessageList.get(position);

        String uid = mylocaldata.getString("uid", "");
        if (message.getSender().equals(uid)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_receiver, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time1);
        }

        void bind(Chat message) {
            messageText.setText(message.getPesan());

            SimpleDateFormat dformat = new SimpleDateFormat("HH:mm");
            String dateString = dformat.format(new Date(Long.parseLong(message.getTanggal().toString())));
            timeText.setText(dateString);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;
        PopupWindow popupWindow;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time2);
            nameText = itemView.findViewById(R.id.text_message_name);
            profileImage = itemView.findViewById(R.id.image_message_profile);
        }

        void bind(final Chat message) {
            messageText.setText(message.getPesan());

            SimpleDateFormat dformat = new SimpleDateFormat("HH:mm");
            String dateString = dformat.format(new
                    Date(Long.parseLong(message.getTanggal().toString())));
            timeText.setText(dateString);

            nameText.setText(message.getSender());
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View costumView = inflater.inflate(R.layout.popup_user, null);

                     popupWindow = new PopupWindow(costumView, 500, 500);

                    if(Build.VERSION.SDK_INT>=21){
                        popupWindow.setElevation(5.0f);
                    }

                    TextView tv_nama = costumView.findViewById(R.id.tv_nama);
                    tv_nama.setText(message.getSender());
                    Button btn_tutup = costumView.findViewById(R.id.btn_tutup);
                    btn_tutup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.rgb(206,206,206)));
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAtLocation(costumView, Gravity.CENTER, 0,0);
                }

            });


        }
    }
}