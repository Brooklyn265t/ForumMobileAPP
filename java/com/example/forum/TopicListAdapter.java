package com.example.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TopicListAdapter extends ArrayAdapter<Topic> {
    private Context context;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView userDataTextView;
    private Topic topic;
    private String username;
    private String data;
    private String combinedText;

    public TopicListAdapter(Context context, List<Topic> topics) {
        super(context, 0, topics);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.topic_fragment, parent, false);
        }
        titleTextView = view.findViewById(R.id.title_text_view);
        descriptionTextView = view.findViewById(R.id.description_text_view);
        userDataTextView = view.findViewById(R.id.data_username);

        topic = getItem(position);
        titleTextView.setText(topic.getTitle());
        descriptionTextView.setText(topic.getDescription());
        data = topic.getData();
        username = topic.getUsername();
        combinedText = data + " " + username;
        userDataTextView.setText(combinedText);
        return view;
    }
}