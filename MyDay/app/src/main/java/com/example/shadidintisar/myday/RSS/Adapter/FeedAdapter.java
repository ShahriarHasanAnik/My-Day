package com.example.shadidintisar.myday.RSS.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shadidintisar.rss.Model.RSSObject;
import com.example.shadidintisar.rss.R;

public class FeedAdapter extends  RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private RSSObject rssObject;
    private Context mcontext;


    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtPubDate, txtContent;

        public FeedViewHolder(View itemview) {
            super(itemview);

            txtTitle = itemview.findViewById(R.id.txtTitle);
            txtPubDate = itemview.findViewById(R.id.txtPubDate);
            txtContent = itemview.findViewById(R.id.txtContent);

        }
    }

    public FeedAdapter(RSSObject rssObject, Context mcontext) {
        this.rssObject = rssObject;
        this.mcontext = mcontext;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        FeedViewHolder vh = new FeedViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, final int position) {
        feedViewHolder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        feedViewHolder.txtPubDate.setText(rssObject.getItems().get(position).getPubDate());
        feedViewHolder.txtContent.setText(rssObject.getItems().get(position).getContent());

        feedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(browserIntent);
            }

        });

    }
    @Override
    public int getItemCount () {
        return rssObject.items.size();
    }
}
