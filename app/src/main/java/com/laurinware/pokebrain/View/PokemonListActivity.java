package com.laurinware.pokebrain.View;

import android.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.laurinware.pokebrain.Model.PokemonList;
import com.laurinware.pokebrain.Model.PokemonListItem;
import com.laurinware.pokebrain.R;
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
    RecyclerView mRecyclerView;

    SimpleItemRecyclerViewAdapter mAdapter;
    SearchView searchView;

    List<PokemonListItem> mPokemonNamesList = new ArrayList<PokemonListItem>();

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
        mRecyclerView = (RecyclerView) findViewById(R.id.pokemon_list);

        final Toast t = Toast.makeText(this, getResources().getString(R.string.action_loading), Toast.LENGTH_SHORT);
        t.show();
        pokemonListViewModel = ViewModelProviders.of(this).get(PokemonListViewModel.class);
        pokemonListViewModel.getPokemonList().observe(this, new Observer<PokemonList>() {
            @Override
            public void onChanged(@Nullable PokemonList pokemonNamesList) {
                //Log.d("ONCHANGED",pokemonNamesList.getResults().get(0).getName());
                mPokemonNamesList.clear();
                mPokemonNamesList.addAll(pokemonNamesList.getResults());
                mAdapter = new SimpleItemRecyclerViewAdapter(act, pokemonNamesList.getResults(), mTwoPane);
                mRecyclerView.setAdapter(mAdapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String query) {
                        query = query.toLowerCase();

                        final List<PokemonListItem> filteredModelList = new ArrayList<>();
                        for (PokemonListItem model : mPokemonNamesList) {
                            final String text = model.getName().toLowerCase();
                            if (text.contains(query)) {
                                filteredModelList.add(model);
                            }
                        }
                        mAdapter.animateTo(filteredModelList);
                        mRecyclerView.scrollToPosition(0);
                        return true;
                    }
                });

                t.cancel();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                // TODO
                Toast.makeText(this, "Settings activity", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.action_about_text)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final PokemonListActivity mParentActivity;
        private List<PokemonListItem> mValues;

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


        public PokemonListItem removeItem(int position) {
            final PokemonListItem model = mValues.remove(position);
            notifyItemRemoved(position);
            return model;
        }

        public void addItem(int position, PokemonListItem model) {
            mValues.add(position, model);
            notifyItemInserted(position);
        }

        public void moveItem(int fromPosition, int toPosition) {
            final PokemonListItem model = mValues.remove(fromPosition);
            mValues.add(toPosition, model);
            notifyItemMoved(fromPosition, toPosition);
        }
        public void animateTo(List<PokemonListItem> models) {
            applyAndAnimateRemovals(models);
            applyAndAnimateAdditions(models);
            applyAndAnimateMovedItems(models);
        }
        private void applyAndAnimateRemovals(List<PokemonListItem> newModels) {
            for (int i = mValues.size() - 1; i >= 0; i--) {
                final PokemonListItem model = mValues.get(i);
                if (!newModels.contains(model)) {
                    removeItem(i);
                }
            }
        }
        private void applyAndAnimateAdditions(List<PokemonListItem> newModels) {
            for (int i = 0, count = newModels.size(); i < count; i++) {
                final PokemonListItem model = newModels.get(i);
                if (!mValues.contains(model)) {
                    addItem(i, model);
                }
            }
        }
        private void applyAndAnimateMovedItems(List<PokemonListItem> newModels) {
            for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
                final PokemonListItem model = newModels.get(toPosition);
                final int fromPosition = mValues.indexOf(model);
                if (fromPosition >= 0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
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
    }
}
