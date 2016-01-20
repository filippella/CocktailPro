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

import com.squareup.picasso.Picasso;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.view.activity.CocktailDetailActivity;
import org.dalol.cocktailpro.view.activity.CocktailOverviewActivity;
import org.dalol.cocktailpro.view.custom.SquareImageView;

/**
 * Created by Filippo-TheAppExpert on 1/13/2016.
 */
public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.Holder> {


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);
        SquareImageView imageView = (SquareImageView) holder.itemView.findViewById(R.id.picture);
        Picasso.with(imageView.getContext())
                .load(R.drawable.cocktail_bg)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);

    }

    @Override
    public int getItemCount() {
        return 150;
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
//            int deltaY = 0 - viewToAnimate.getTop();
//            Animator animator = ObjectAnimator.ofFloat(viewToAnimate, "translationY", deltaY);
//            animator.setDuration(750L);
//            animator.start();

//            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_up);
//            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(v.getContext(), CocktailOverviewActivity.class));
        }
    }
}
