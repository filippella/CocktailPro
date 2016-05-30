package org.dalol.cocktailpro.model.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.view.activity.DetailActivity;
import org.dalol.cocktailpro.view.custom.SquareImageView;

import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 1/13/2016.
 */
public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.Holder> {

    private List<Bitmap> images;

    public RecyclerGridAdapter(List<Bitmap> images) {
        this.images = images;
    }

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

        imageView.setImageBitmap(this.images.get(position));

//        Picasso.with(imageView.getContext())
//                .load(R.drawable.cocktail_bg)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(imageView);

    }

    @Override
    public int getItemCount() {
        return this.images.size();
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
            v.getContext().startActivity(new Intent(v.getContext(), DetailActivity.class));
        }
    }
}
