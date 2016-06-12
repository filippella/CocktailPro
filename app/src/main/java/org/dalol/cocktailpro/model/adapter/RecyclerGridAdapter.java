package org.dalol.cocktailpro.model.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.cocktail.CocktailActivity;
import org.dalol.cocktailpro.view.activity.DetailActivity;
import org.dalol.cocktailpro.view.custom.SquareImageView;
import org.dalol.model.cocktailpro.cocktail.CocktailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 1/13/2016.
 */
public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.Holder>  implements Filterable {

    private List<CocktailItem> items = new ArrayList<>();
    private List<CocktailItem> objects = new ArrayList<>();

//    public RecyclerGridAdapter(List<CocktailItem> items) {
//        this.items = items;
//    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // Here you apply the animation when the view is bound
        //setAnimation(holder.itemView, position);
        SquareImageView imageView = (SquareImageView) holder.itemView.findViewById(R.id.picture);

//        imageView.setImageBitmap(this.images.get(position));

        CocktailItem cocktailItem = items.get(position);

        TextView cocktailName = (TextView) holder.itemView.findViewById(R.id.cocktailName);
        cocktailName.setText(cocktailItem.getCocktail().getName());

        Picasso.with(imageView.getContext())
                .load(cocktailItem.getImageId())
//                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);

    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    private int lastPosition = -1;
    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            int deltaY = 0 - viewToAnimate.getTop();
            Animator animator = ObjectAnimator.ofFloat(viewToAnimate, "translationY", deltaY);
            animator.setDuration(750L);
            animator.start();

            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void addCocktailItem(CocktailItem item) {
        int position = items.size();
        this.items.add(position, item);
        this.objects = items;
//        notifyDataSetChanged();
        notifyItemInserted(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            CocktailItem cocktailItem = items.get(getAdapterPosition());

            CocktailActivity activity = (CocktailActivity) v.getContext();

            DetailActivity.launch(activity,  v, cocktailItem.getImageId());

            //v.getContext().startActivity(new Intent(v.getContext(), DetailActivity.class));
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new Filter.FilterResults();
                final List<CocktailItem> results = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    oReturn.values = items;
                    oReturn.count = items.size();
                }

                else {
                    objects = items;

                    for (CocktailItem item : objects) {
                        if (item.getCocktail().getName().toString().toLowerCase().contains(constraint.toString())) {
                            results.add(item);
                        }
                    }
                    oReturn.values = results;
                    oReturn.count = results.size();
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                objects = (ArrayList<CocktailItem>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                }
            }
        };
    }
}
