package com.laurinware.pokebrain.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.laurinware.pokebrain.Model.PokemonList;
import com.laurinware.pokebrain.Model.RegionList;
import com.laurinware.pokebrain.R;
import com.laurinware.pokebrain.Data.RemoteDataSource;
import com.laurinware.pokebrain.ViewModel.PokemonListViewModel;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int num = r.nextInt( 802) + 1;
                Log.d("RANDOM", "Numero: " + num);
                String url = String.format("https://pokeapi.co/api/v2/pokemon/%1$d/", num);
                Intent intent = new Intent(context, PokemonDetailActivity.class);
                intent.putExtra(PokemonDetailFragment.ARG_ITEM_ID, url);

                context.startActivity(intent);
            }
        });

        // Check for Internet Connection
        if(!isOnline()){
            Log.d("INTERNET","NO INTERNET");
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }

        context = getApplicationContext();

        Button buttonPokemon = (Button) findViewById(R.id.pokemon_button);
        buttonPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PokemonListActivity.class);
                startActivity(intent);
            }
        });

        Button buttonRegions = (Button) findViewById(R.id.regions_button);
        buttonRegions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegionListActivity.class);
                startActivity(intent);
            }
        });

    }

    // Options menu stuff
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

    /**
     * Checks if internet conection is online
     * @return
     */
    boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
