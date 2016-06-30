package com.ryanbrooks.expandablerecyclerviewsample.linear.vertical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.LoadMoreRecyclerView;
import com.ryanbrooks.expandablerecyclerviewsample.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sample Activity for the vertical linear RecyclerView sample.
 * Uses ButterKnife to inject view resources.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class VerticalLinearRecyclerViewSampleActivity extends AppCompatActivity {

    private RecipeAdapter mAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, VerticalLinearRecyclerViewSampleActivity.class);
    }

    List<Recipe> recipes;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_sample);

        Ingredient beef = new Ingredient("beef");
        Ingredient cheese = new Ingredient("cheese");
        Ingredient salsa = new Ingredient("salsa");
        Ingredient tortilla = new Ingredient("tortilla");
        Ingredient ketchup = new Ingredient("ketchup");
        Ingredient bun = new Ingredient("bun");

        Recipe taco = new Recipe("taco", Arrays.asList(beef, cheese, salsa, tortilla));
        Recipe quesadilla = new Recipe("quesadilla", Arrays.asList(cheese, tortilla));
        Recipe burger = new Recipe("burger", Arrays.asList(beef, cheese, ketchup, bun));


        recipes = new ArrayList<>();

        recipes.add(taco);
        recipes.add(quesadilla);
        recipes.add(burger);
        recipes.add(taco);
        recipes.add(quesadilla);
        recipes.add(burger);
        recipes.add(taco);
        recipes.add(quesadilla);
        recipes.add(burger);
        recipes.add(taco);
        recipes.add(quesadilla);
        recipes.add(burger);

        final LoadMoreRecyclerView recyclerView = (LoadMoreRecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new RecipeAdapter(this, recipes);
        mAdapter.addFooterView(R.layout.list_foot_loading);
        mAdapter.setFooterEnable(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAutoLoadMoreEnable(true);


        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {



              recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Ingredient beef = new Ingredient("beef1");
                        count ++;



                        final Recipe burger1 = new Recipe("burger"+count, Arrays.asList(beef));
                        recipes.add(burger1);

                        mAdapter.setListItem(recipes);
                        recyclerView.notifyMoreFinish(count != 5 );

//                        mAdapter.notifyParentItemChanged(re);


                    }
                }, 1000);
            }
        });
        mAdapter.notifyDataSetChanged();
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                Recipe expandedRecipe = recipes.get(position);

                String toastMsg = getResources().getString(R.string.expanded, expandedRecipe.getName());
                Toast.makeText(VerticalLinearRecyclerViewSampleActivity.this,
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onListItemCollapsed(int position) {
                Recipe collapsedRecipe = recipes.get(position);

                String toastMsg = getResources().getString(R.string.collapsed, collapsedRecipe.getName());
                Toast.makeText(VerticalLinearRecyclerViewSampleActivity.this,
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }
}
