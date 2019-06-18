package br.com.globaldev.smartbuy;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import br.com.globaldev.smartbuy.models.Product;

public class Qr extends AppCompatActivity {

    private ViewGroup viewGroup;

    private List<Product> mProducts = new ArrayList<Product>();;
    private List<Product> myCart = new ArrayList<Product>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewGroup = findViewById(android.R.id.content);

        getSupportActionBar().setTitle("Smart Buy");


        Product p1 = new Product();
        p1.setName("Acucar");
        p1.setDescription("");
        p1.setImg(R.drawable.default_qrcode);
        p1.setQtd(1);
        p1.setPrice(50.2);
        p1.setId(1);

        mProducts.add(p1);

        findViewById(R.id.imageViewQr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Qr.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Passe o scan em um QrCode ou código de barras");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Qr.this, Login.class));
                Intent it = new Intent(Qr.this, Cart.class);
                it.putExtra("cart", new Gson().toJson(myCart));
                startActivity(it);
            }
        });


        dialogProduct(p1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult != null){
            if (intentResult.getContents() !=  null){

                int pId = Integer.parseInt(intentResult.getContents().toString());

                for (Product p: mProducts){
                    if (p.getId() == pId){
                        dialogProduct(p);
                        break;
                    }
                }

            }else{
                Toast.makeText(Qr.this, "Falha ao escanear", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }



    public void dialogProduct(final Product p){

        final View dialogView = LayoutInflater.from(Qr.this).inflate(R.layout.dialog_custom_add_product, viewGroup, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(Qr.this);

        ImageView imageViewImage = dialogView.findViewById(R.id.dialog_product_image);
        TextView textViewTitle = dialogView.findViewById(R.id.dialog_product_title);
        TextView textViewPrice = dialogView.findViewById(R.id.dialog_product_value);
        final TextView textViewQtd = dialogView.findViewById(R.id.textViewQtd);
        Button buttonOk = dialogView.findViewById(R.id.dialog_product_button_cart_add);


        ImageView btPositive = dialogView.findViewById(R.id.imageViewNegative);
        ImageView btNegative = dialogView.findViewById(R.id.imageViewPositive);

        imageViewImage.setBackground(getApplication().getResources().getDrawable(p.getImg()));
        textViewTitle.setText(p.getName());
        textViewPrice.setText("R$ "+Double.toString(p.getPrice()));
        textViewQtd.setText(String.valueOf(p.getQtd()));

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCart.add(p);
                alertDialog.dismiss();
            }
        });


        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setQtd(p.getQtd() - 1);
                textViewQtd.setText(String.valueOf(p.getQtd()));
            }
        });


        btNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setQtd(p.getQtd() + 1);
                textViewQtd.setText(String.valueOf(p.getQtd()));
            }
        });

        //alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //alertDialog.setContentView(dialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

    }

}
