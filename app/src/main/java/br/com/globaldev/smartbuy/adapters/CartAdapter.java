package br.com.globaldev.smartbuy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import br.com.globaldev.smartbuy.R;
import br.com.globaldev.smartbuy.models.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Product> products;

    public CartAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Product item = products.get(position);

        holder.mTextViewTitle.setText(Html.fromHtml(item.getName()));
        holder.mTextViewDescription.setText(Html.fromHtml(item.getDescription()));
        holder.mTextViewValue.setText(Html.fromHtml("R$ " +Double.toString(item.getPrice()).replace(".", ",")));
        holder.mTextViewQtd.setText(Html.fromHtml(String.valueOf(item.getQtd())));
        holder.mImageView.setImageDrawable(context.getResources().getDrawable(item.getImg())) ;

    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewTitle, mTextViewDescription, mTextViewValue, mTextViewQtd;
        public ImageView mImageView;

        public MyViewHolder(View v) {
            super(v);

            this.mTextViewTitle = v.findViewById(R.id.textViewTitle);
            this.mTextViewDescription = v.findViewById(R.id.textViewDescription);
            this.mTextViewValue = v.findViewById(R.id.textViewValue);
            this.mTextViewQtd = v.findViewById(R.id.textViewQtd);

            this.mImageView = v.findViewById(R.id.imageViewThumbnail);


        }
    }
}