package br.com.globaldev.smartbuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.globaldev.smartbuy.adapters.CartAdapter;
import br.com.globaldev.smartbuy.models.Product;

public class Cart extends AppCompatActivity {

    private List<Product> mProducts = new ArrayList<Product>();
    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras;
        extras = getIntent().getExtras();

        TypeToken<List<Product>> token = new TypeToken<List<Product>>() {};

        mProducts = new Gson().fromJson(extras.getString("cart"), token.getType());

        mRecyclerView = findViewById(R.id.rv_cart);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCartAdapter = new CartAdapter(Cart.this, mProducts);
        mRecyclerView.setAdapter(mCartAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.cart, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == 16908332) {

            this.finish();
        }

        if(id == R.id.action_close){

        }

        return true;
    }

}
