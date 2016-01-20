package org.dalol.cocktailpro.model.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dalol.cocktailpro.R;
import org.dalol.cocktailpro.view.activity.CocktailOverviewActivity;

/**
 * Created by Filippo-TheAppExpert on 1/13/2016.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.Holder> {


    public static final int HEADER = 0;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row;

        if (viewType == HEADER) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
        } else {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        }


        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 150;
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

    @Override
    public int getItemViewType(int position) {
        return isHeaderType(position) ? HEADER : 1;
    }

    private boolean isHeaderType(int position) {
        return position % 8 == 0;
    }
}
