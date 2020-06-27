package com.example.doublesucktopdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.doublesucktopdemo.adapter.FragmentAdapter;
import com.example.doublesucktopdemo.widget.RecyclerViewDivider;

import java.util.ArrayList;

/**
 * Created by weioule
 * on 2019/6/26.
 */
public class HomeFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ArrayList<ProductBean> list;

    public HomeFragment(ArrayList<ProductBean> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);

        FragmentAdapter adapter = new FragmentAdapter(list);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ProductBean productBean = (ProductBean) adapter.getItem(position);
                Toast.makeText(getContext(), productBean.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(getContext())
                .setStyle(RecyclerViewDivider.Style.BOTH)
                .setColor(0x00000000)
                .setOrientation(RecyclerViewDivider.GRIDE_VIW)
                .setSize(10)
                .build();
        mRecyclerView.addItemDecoration(divider);
    }
}
