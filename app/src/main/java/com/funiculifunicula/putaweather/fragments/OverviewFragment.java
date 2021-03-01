package com.funiculifunicula.putaweather.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.funiculifunicula.putaweather.R;
import com.funiculifunicula.putaweather.overviewrecycler.OverviewItem;
import com.funiculifunicula.putaweather.overviewrecycler.OverviewRecyclerAdapter;

public class OverviewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_fragment, container, false);

        OverviewRecyclerAdapter adapter = new OverviewRecyclerAdapter();
        adapter.add(new OverviewItem("Test"));
        adapter.add(new OverviewItem("Hey"));
        adapter.add(new OverviewItem("Cheese"));
        adapter.add(new OverviewItem("1234"));
        adapter.add(new OverviewItem("the Netherlands"));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}