package com.development.mobile.heasoft.heasoft;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment{

    private final String BASE_URL = "http://195.19.44.155";
    private NewsInterface service;
    private ProgressBar prBar;
    private ArrayList<ModelItem> itemsList;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private LinearLayoutManager horizontalLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_news);

        itemsList = new ArrayList<>();
        prBar = (ProgressBar) view.findViewById(R.id.progress);

        verticalLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        horizontalLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(verticalLinearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NewsInterface.class);
        Call<Example> call = service.getNews();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                prBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                List<News> news = response.body().getNews();

                for(int i =0; i<news.size(); i++) {
                    itemsList.add(new ModelItem(news.get(i).getAuthor(), R.mipmap.ic_launcher, news.get(i).getTitle(), news.get(i).getContent()));
                }
                adapter.addAll(itemsList);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

        return view;
    }
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {


        private TextView headerTag;
        private TextView headerDesk;
        private TextView headerAuthor;
        private ImageView imgIdHeader;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            headerTag = (TextView) itemView.findViewById(R.id.tagHeader);
            headerDesk = (TextView) itemView.findViewById(R.id.deskHeader);
            headerAuthor = (TextView) itemView.findViewById(R.id.authorHeader);
            imgIdHeader = (ImageView) itemView.findViewById(R.id.imageHeader);

        }

        void bind(ModelItem modelItem) {
            imgIdHeader.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), modelItem.getImgIdHeader()));
            headerTag.setText(modelItem.getHeaderTag());
            headerDesk.setText(modelItem.getHeaderDesk());
            headerAuthor.setText(modelItem.getHeaderAuthor());
        }


    }

    private class RecyclerAdapter extends RecyclerView.Adapter<NewsFragment.RecyclerViewHolder> {
        private ArrayList<ModelItem> items = new ArrayList<>();

        void addAll(List<ModelItem> fakeItems){
            int pos = getItemCount();
            this.items.addAll(fakeItems);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @Override
        public NewsFragment.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_news, parent, false);
            return new NewsFragment.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsFragment.RecyclerViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }


    public static Fragment newInstance() {
        return new NewsFragment();
    }
}