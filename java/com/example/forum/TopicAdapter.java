package com.example.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
//    private List<Topic> topics;
//    private OnItemClickListener listener;
//    static Topic topic;
//    public String username;
//    public String data;
//    public String combinedText;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public static class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public TextView titleTextView;
//        public TextView descriptionTextView;
//        public TextView UserData;
//        private OnItemClickListener listener;
//
//        public TopicViewHolder(View itemView) {
//            super(itemView);
//            titleTextView = itemView.findViewById(R.id.title_text_view);
//            descriptionTextView = itemView.findViewById(R.id.description_text_view);
//            UserData = itemView.findViewById(R.id.data_username);
//            itemView.setOnClickListener(this);
//        }
//        @Override
//        public void onClick(View view) {
//            if (listener!= null) {
//                listener.onItemClick(getAdapterPosition());
//            }
//        }
//    }
//
//    public TopicAdapter(List<Topic> topics) {
//        this.topics = topics;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
//    @Override
//    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.topic_fragment, parent, false);
//        return new TopicViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(TopicViewHolder holder, int position) {
//        topic = topics.get(position);
//        holder.titleTextView.setText(topic.getTitle());
//        holder.descriptionTextView.setText(topic.getDescription());
//        username = topic.getUsername();
//        data = topic.getData();
//        combinedText = data + " " + username;
//        holder.UserData.setText(combinedText);
//    }
//
//    @Override
//    public int getItemCount() {
//        return topics.size();
//    }
//}