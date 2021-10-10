package com.laurinware.pokebrain.View;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.res.Resources;

import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.laurinware.pokebrain.Model.Pokemon;
import com.laurinware.pokebrain.R;
import com.laurinware.pokebrain.ViewModel.PokemonListViewModel;

/**
 * A fragment representing a single Pokemon detail screen.
 * This fragment is either contained in a {@link PokemonListActivity}
 * in two-pane mode (on tablets) or a {@link PokemonDetailActivity}
 * on handsets.
 */
public class PokemonDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_url";

    PokemonListViewModel pokemonListViewModel;
    private Pokemon mItem;

    private PokemonDetailFragment act = this;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PokemonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            final Toast t = Toast.makeText(this.getContext(), getResources().getString(R.string.action_loading),
                    Toast.LENGTH_SHORT);
            t.show();

            pokemonListViewModel = ViewModelProviders.of(this).get(PokemonListViewModel.class);
            pokemonListViewModel.getPokemon(getArguments().getString(ARG_ITEM_ID)).observe(this, new Observer<Pokemon>() {
                @Override
                public void onChanged(@Nullable Pokemon pokemon) {
                    mItem = pokemon;
                    Activity activity = act.getActivity();
                    CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                    if (appBarLayout != null) {
                        String name = mItem.getName().substring(0,1).toUpperCase()
                                + mItem.getName().substring(1);
                        appBarLayout.setTitle(name);
                    }

                    Resources res = getResources();

                    TextView number_tv = (TextView) activity.findViewById(R.id.pokemon_number_label);
                    number_tv.setText(String.format(res.getString(R.string.number_label), mItem.getId()));
                    TextView height_tv = (TextView) activity.findViewById(R.id.pokemon_height_label);
                    height_tv.setText(String.format(res.getString(R.string.height_label), mItem.getHeight()/10.0));
                    TextView weight_tv = (TextView) activity.findViewById(R.id.pokemon_weight_label);
                    weight_tv.setText(String.format(res.getString(R.string.weight_label), mItem.getWeight()/10.0));

                    ImageView imageView = (ImageView) activity.findViewById(R.id.pokemon_imageView);
                    Glide.with(act)
                            .load(mItem.getSprites().getFrontDefault())
                            .apply(new RequestOptions().override(384, 384))
                            .into(imageView);
                    t.cancel();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pokemon_detail, container, false);

        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.pokemon_detail)).setText(mItem.getHeight());

        }

        return rootView;
    }
}
