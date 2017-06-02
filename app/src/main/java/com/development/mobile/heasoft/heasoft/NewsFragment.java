package com.development.mobile.heasoft.heasoft;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {

    private final String BASE_URL = "http://46.173.218.141:8080";
    private NewsInterface service;
    private ArrayList<ModelItem> itemsList;
    private RecyclerAdapter adapter;
    private Retrofit retrofit;
    public boolean checkLikes[];
    public boolean checkLikesOnce = false;
    public boolean likesArray[];


    @BindView(R.id.progress)
    ProgressBar prBar;

    @BindView(R.id.recycler_news)
    RecyclerView recyclerView;

    private List<News> news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        ButterKnife.bind(this, view);

        itemsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
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
                news = response.body().getNews();
                likesArray = new boolean[news.size()];
                checkLikes = new boolean[news.size()];
                for (int i = news.size() - 1; i >= 0; i--) {
                    if (news.get(i).getType().equals("simple")) {
                        itemsList.add(new ModelItem(news.get(i).getAuthor(), news.get(i).getUrlToImage(), news.get(i).getTitle(), news.get(i).getContent(), i, Integer.valueOf(news.get(i).getLikes()), news.get(i).getType()));

                    }
                    else if(news.get(i).getType().equals("small")) {
                        itemsList.add(new ModelItem(news.get(i).getTitle(), news.get(i).getType()));
                    }
                    else {
                        itemsList.add(new ModelItem(news.get(i).getTitle(),news.get(i).getAuthor(), news.get(i).getContent(), news.get(i).getBigcontent(), news.get(i).getType(), 1));
                    }
                }
                adapter.addAll(itemsList);

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("checkLikes", checkLikes);
        outState.putBooleanArray("likesArray", likesArray);
        outState.putBoolean("checkLikesOnce", checkLikesOnce);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            checkLikes = savedInstanceState.getBooleanArray("checkLikes");
            likesArray = savedInstanceState.getBooleanArray("likesArray");
            checkLikesOnce = savedInstanceState.getBoolean("checkLikesOnce");

        } catch (NullPointerException ignored) {
        }
    }

    public class RecyclerViewHolderSimple extends RecyclerView.ViewHolder {

        @BindView(R.id.tagHeader)
        TextView headerTag;

        @BindView(R.id.deskHeader)
        TextView headerDesk;

        @BindView(R.id.authorHeader)
        TextView headerAuthor;

        @BindView(R.id.imageHeader)
        ImageView imgIdHeader;

        @BindView(R.id.likes)
        TextView like;

        RecyclerViewHolderSimple(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ModelItem modelItem) {
            try {
                Picasso.with(getActivity().getApplicationContext()).load("http://46.173.218.141:8080/heasoft/images/news/" + modelItem.getUrlToImage() + ".png").into(imgIdHeader);
            } catch (Exception ignored) {}

            headerTag.setText(modelItem.getHeaderTag());
            headerDesk.setText(modelItem.getHeaderDesk());
            headerAuthor.setText(modelItem.getHeaderAuthor());

            like.setText(String.valueOf(modelItem.getLikes()));
        }
    }

    private  class RecyclerViewHolderSmall extends RecyclerView.ViewHolder {
        //simple
        private TextView content;

        RecyclerViewHolderSmall(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content_small);
        }
        void bind(ModelItem modelItem) {
            content.setText(modelItem.getSmallContent());
        }
    }

    private  class RecyclerViewHolderCombo extends RecyclerView.ViewHolder {
        //simple

        private TextView news1;
        private TextView news2;
        private TextView news3;
        private TextView title;

        RecyclerViewHolderCombo(View itemView) {
            super(itemView);
            news1 = (TextView) itemView.findViewById(R.id.news_1);
            news2 = (TextView) itemView.findViewById(R.id.news_2);
            news3 = (TextView) itemView.findViewById(R.id.news_3);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        void bind(ModelItem modelItem) {
            news1.setText(modelItem.getNews1());
            news2.setText(modelItem.getNews2());
            news3.setText(modelItem.getNews3());
            title.setText(modelItem.getComboTitle());
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<ModelItem> items = new ArrayList<>();

        void addAll(List<ModelItem> fakeItems) {
            int pos = getItemCount();
            this.items.addAll(fakeItems);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @Override
        public int getItemViewType(int position)
        {
            ModelItem item = itemsList.get(position);
            switch (item.getType()){
                case "simple": return 0;
                case "small": return 1;
                default: return 2;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            RecyclerView.ViewHolder vh=null;
            View itemLayoutView;

            switch (viewType)
            {
                case 0:
                    itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_news, parent, false);
                    vh = new RecyclerViewHolderSimple(itemLayoutView);
                    break;
                case 1:
                    itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_small_news, parent, false);
                    vh = new RecyclerViewHolderSmall(itemLayoutView);
                    break;
                case 2:
                    itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_combo, parent, false);
                    vh = new RecyclerViewHolderCombo(itemLayoutView);
                    break;
            }
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (this.getItemViewType(position))
            {
                case 0:
                    RecyclerViewHolderSimple simple = (RecyclerViewHolderSimple) holder;
                    simple.bind(items.get(position));
                    break;
                case 1:
                    RecyclerViewHolderSmall small = (RecyclerViewHolderSmall) holder;
                    small.bind(items.get(position));
                    break;
                case 2:
                    RecyclerViewHolderCombo combo = (RecyclerViewHolderCombo) holder;
                    combo.bind(items.get(position));
                    break;

            }
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