package com.sylvesterllc.newapps1.Adapters;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sylvesterllc.newapps1.Interfaces.onDataChangerListener;
import com.sylvesterllc.newapps1.MainActivity;
import com.sylvesterllc.newapps1.Models.GuardApiData;
import com.sylvesterllc.newapps1.Models.NewsArticle;
import com.sylvesterllc.newapps1.R;
import com.sylvesterllc.newapps1.Services.NewsService;

import java.security.Guard;
import java.util.ArrayList;

public class NewsAdapater extends RecyclerView.Adapter<NewsAdapater.ViewHolder> {

    private Context context;
    private GuardApiData fullData;
    private MutableLiveData<ArrayList<NewsArticle>> data1 = new MutableLiveData<>();

    public NewsAdapater(Context ctx, final MainActivity act, MutableLiveData<ArrayList<NewsArticle>> article) {

        context = ctx;
//        new NewsService(context).GetNews(new onDataChangerListener() {
//            @Override
//            public void onDataChange(ArrayList<NewsArticle> data) {
//
//                data1 = data;
//                act.mAdapter.notifyDataSetChanged();
//
//            }
//        });
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

        TextView articleName;
        TextView sectionName;
        TextView sectionID;
        TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);

            articleName = itemView.findViewById(R.id.txtArticleTitle);
            sectionName = itemView.findViewById(R.id.txtSectionName);
            sectionID = itemView.findViewById(R.id.txtSectionID);
            desc = itemView.findViewById(R.id.txtDesc);

        }

        public void bindItem(final NewsArticle att, final Context context) {

            articleName.setText(att.webTitle);
            sectionName.setText(att.sectionName);
            sectionID.setText(att.sectionId);
            desc.setText(att.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}