package com.sylvesterllc.newapps1.Adapters;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sylvesterllc.newapps1.MainActivity;
import com.sylvesterllc.newapps1.Models.GuardApiData;
import com.sylvesterllc.newapps1.Models.NewsArticle;
import com.sylvesterllc.newapps1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapater extends RecyclerView.Adapter<NewsAdapater.ViewHolder> {

    private final String TAG = "NewAdapter";
    private Context context;
    private GuardApiData fullData;
    private MutableLiveData<ArrayList<NewsArticle>> data1 = new MutableLiveData<>();

    public NewsAdapater(Context ctx, final MainActivity act, MutableLiveData<ArrayList<NewsArticle>> article) {

        context = ctx;
        data1 = article;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.news_article_item, parent, false);

        ViewHolder vh = new NewsAdapater.ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindItem(data1.getValue().get(position), context);
    }

    @Override
    public int getItemCount() {
        return (data1 == null || data1.getValue() == null) ? 0 : data1.getValue().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final String TAG = "NewAdapter - ViewHolder";
        TextView articleName;
        TextView sectionName;
        TextView sectionID;
        TextView desc;
        TextView webTitle;
        TextView webPublishDate;

        public ViewHolder(View itemView) {
            super(itemView);

            articleName = itemView.findViewById(R.id.txtArticleTitle);
            sectionName = itemView.findViewById(R.id.txtSectionName);
            sectionID = itemView.findViewById(R.id.txtSectionID);
            desc = itemView.findViewById(R.id.txtDesc);
            webTitle = itemView.findViewById(R.id.txtWebTitle);
            webPublishDate = itemView.findViewById(R.id.txtPublishDate);
        }

        public void bindItem(final NewsArticle att, final Context context) {

            articleName.setText(att.webTitle);
            sectionName.setText(att.sectionName);
            sectionID.setText(att.sectionId);
            desc.setText(att.description);
            webTitle.setText((att.tags.size() == 0) ? "" : att.tags.get(0).webTitle);
            webTitle.setText((att.webPublicationDate.toString().length() == 0) ? "" : att.webPublicationDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("HelpD", att.webTitle + " Item Has been clicked on Recyclerer");


                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(att.webUrl));
                    i.putExtra("url", att.webUrl);
                    context.startActivity(i);


                }
            });
        }

    }
}