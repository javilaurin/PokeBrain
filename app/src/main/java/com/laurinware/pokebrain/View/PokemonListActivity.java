package com.laurinware.pokebrain.View;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.laurinware.pokebrain.Model.PokemonList;
import com.laurinware.pokebrain.Model.PokemonListItem;
import com.laurinware.pokebrain.R;
import com.laurinware.pokebrain.Data.RemoteDataSource;
import com.laurinware.pokebrain.ViewModel.PokemonListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Pokemons. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PokemonDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PokemonListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private PokemonListActivity act = this;

    PokemonListViewModel pokemonListViewModel;
    RecyclerView recyclerView;

    SimpleItemRecyclerViewAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.pokemon_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        searchView = (SearchView) findViewById(R.id.pokemon_searchView);
        recyclerView = (RecyclerView) findViewById(R.id.pokemon_list);

        final Toast t = Toast.makeText(this, getResources().getString(R.string.action_loading), Toast.LENGTH_SHORT);
        t.show();
        pokemonListViewModel = ViewModelProviders.of(this).get(PokemonListViewModel.class);
        pokemonListViewModel.getPokemonList().observe(this, new Observer<PokemonList>() {
            @Override
            public void onChanged(@Nullable PokemonList pokemonNamesList) {
                //Log.d("ONCHANGED",pokemonNamesList.getResults().get(0).getName());
                adapter = new SimpleItemRecyclerViewAdapter(act, pokemonNamesList.getResults(), mTwoPane);
                recyclerView.setAdapter(adapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });

                t.cancel();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);

        return true;
    }


    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final PokemonListActivity mParentActivity;
        private List<PokemonListItem> mValues;
        private List<PokemonListItem> mCopiedValues;
        CustomFilter customFilter = new CustomFilter();
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PokemonListItem item = (PokemonListItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(PokemonDetailFragment.ARG_ITEM_ID, item.getUrl());
                    PokemonDetailFragment fragment = new PokemonDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pokemon_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, PokemonDetailActivity.class);
                    intent.putExtra(PokemonDetailFragment.ARG_ITEM_ID, item.getUrl());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(PokemonListActivity parent,
                                      List<PokemonListItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;

            mCopiedValues = mValues;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pokemon_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            String[] separated = mValues.get(position).getUrl().split("/");
            holder.mIdView.setText(separated[separated.length - 1]);
            String name = mValues.get(position).getName();
            String upName = name.substring(0, 1).toUpperCase() + name.substring(1);
            holder.mContentView.setText(upName);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }



        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }

        public Filter getFilter() {
            if (customFilter == null) {
                customFilter = new CustomFilter();
            }
            return customFilter;
        }

        private class CustomFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    List<PokemonListItem> filterList = new ArrayList<PokemonListItem>();
                    for (int i = 0; i < mValues.size(); i++) {
                        //Se filtra por el nombre del pokemon
                        if ((mValues.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                            filterList.add(mValues.get(i));
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mCopiedValues.size();
                    results.values = mCopiedValues;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mCopiedValues = (ArrayList) results.values;
                notifyDataSetChanged();
            }

        }
    }
}
