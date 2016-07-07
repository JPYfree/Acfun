package me.jpyfree.acfun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jpyfree.acfun.R;
import me.jpyfree.acfun.activity.PartitionActivity;
import me.jpyfree.acfun.adapter.NavigationRecyclerViewAdapter;
import me.jpyfree.acfun.base.BaseFragment;

/**
 * Created by Administrator on 2016/6/20.
 */
public class NavigationFragment extends BaseFragment{

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    private RecyclerView recyclerView;
    private NavigationRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_navigation, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.navigation_recycler_view);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 3) {
                    return 2;
                }
                return 1;
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new NavigationRecyclerViewAdapter.MyDecoration());

        adapter = new NavigationRecyclerViewAdapter();
        adapter.setOnClickListener(new NavigationRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(View view, String partitionType) {
                //开启分区页面
                PartitionActivity.startActivity(getContext(), partitionType);
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    protected void lazyLoad() {

    }
}
