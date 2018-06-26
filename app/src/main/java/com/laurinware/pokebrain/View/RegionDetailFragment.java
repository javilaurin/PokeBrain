package com.laurinware.pokebrain.View;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.laurinware.pokebrain.Model.RegionItem;
import com.laurinware.pokebrain.R;
import com.laurinware.pokebrain.dummy.DummyContent;

/**
 * A fragment representing a single Region detail screen.
 * This fragment is either contained in a {@link RegionListActivity}
 * in two-pane mode (on tablets) or a {@link RegionDetailActivity}
 * on handsets.
 */
public class RegionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_name";
    Activity activity = this.getActivity();

    /**
     * The dummy content this fragment is presenting.
     */
    private String mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RegionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = getArguments().getString(ARG_ITEM_ID);

            activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                String tit = mItem.substring(0,1).toUpperCase() + mItem.substring(1);
                appBarLayout.setTitle(tit);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.region_detail, container, false);


        ImageView imageView = (ImageView) rootView.findViewById(R.id.region_imageView);



        if (mItem != null) {
            Log.d("mITEM",mItem);
            switch (mItem){
                case "BASE":
                    loadImage(this, "Mapa_completo.png", imageView);
                    break;
                case "kanto":
                    loadImage(this, "region_Kanto.jpg",imageView);
                    break;
                case "johto":
                    loadImage(this, "region_Johto.png",imageView);
                    break;
                case "hoenn":
                    loadImage(this, "region_Hoenn.png",imageView);
                    break;
                case "sinnoh":
                    loadImage(this, "region_Sinnoh.jpg",imageView);
                    break;
                case "unova":
                    loadImage(this, "region_Unova.png",imageView);
                    break;
                case "kalos":
                    loadImage(this, "region_Kalos.png",imageView);
                    break;
                case "alola":
                    loadImage(this, "region_Alola.png",imageView);
                    break;
            }

        }

        return rootView;
    }
    public void loadImage(Fragment frag, String serverName, ImageView imageView){
        String base_url = "https://javilaurin.github.io/pokemonregionimages/";
        String full_url = String.format(base_url + serverName);
        Log.d("DIR",full_url);
        Glide.with(this)
                .load(full_url)
                .into(imageView);
    }
}
