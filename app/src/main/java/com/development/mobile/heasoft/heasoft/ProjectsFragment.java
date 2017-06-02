package com.development.mobile.heasoft.heasoft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ProjectsFragment extends Fragment {
    private final String BASE_URL = "http://46.173.218.141:8080";
    private ProjectsInterface service;
    private ProgressBar prBar;
    private ArrayList<ModelItem> itemsList;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private LinearLayoutManager horizontalLinearLayoutManager;
    private Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_news);

        itemsList = new ArrayList<>();
        prBar = (ProgressBar) view.findViewById(R.id.progress);

        verticalLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        horizontalLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(verticalLinearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ProjectsInterface.class);
        Call<Projects> call = service.getProjects();
        call.enqueue(new Callback<Projects>() {
            @Override
            public void onResponse(Call<Projects> call, Response<Projects> response) {
                prBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                List<Project> projects = response.body().getProjects();
                for (int i = projects.size() - 1; i >= 0; i--) {

                    itemsList.add(new ModelItem(projects.get(i).getTitle(), projects.get(i).getDescription(), projects.get(i).getUrlToImage(), projects.get(i).getUrl()));

                }
                adapter.addAll(itemsList);
            }

            @Override
            public void onFailure(Call<Projects> call, Throwable t) {

            }
        });
        
        return view;

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
           return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            RecyclerView.ViewHolder vh=null;
            View itemLayoutView;

            switch (getItemViewType(viewType))
            {
                case 0:
                    itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_project, parent, false);
                    vh = new RecyclerViewHolder(itemLayoutView);
                    break;

            }
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (this.getItemViewType(position))
            {
                case 0:
                    RecyclerViewHolder simple = (RecyclerViewHolder) holder;
                    simple.bind(items.get(position));
                    break;
            }
        }


        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //simple
        private TextView headerTag;
        private TextView headerDesk;
        private ImageView imgIdHeader;
        private String url;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            headerTag = (TextView) itemView.findViewById(R.id.tagHeader);
            headerDesk = (TextView) itemView.findViewById(R.id.deskHeader);
            imgIdHeader = (ImageView) itemView.findViewById(R.id.imageHeader);

        }

        void bind(ModelItem modelItem) {
            try {
                Picasso.with(getActivity().getApplicationContext()).load("http://46.173.218.141:8080/heasoft/images/projects/" + modelItem.getUrlToImageProjects() + ".png").into(imgIdHeader);
            } catch (Exception ignored) {
            }
            headerTag.setText(modelItem.getTitlePro());
            headerDesk.setText(modelItem.getDeskPro());
            url = modelItem.getUrl();
            imgIdHeader.setOnClickListener(this);
            headerDesk.setOnClickListener(this);
            headerTag.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }


}
