package org.dalol.cocktailpro.view.custom;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.dalol.cocktailpro.model.adapter.RecyclerListAdapter;

/**
 * @author Filippo <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/13/2016
 */
public class StickyHeaderView extends RecyclerView.ItemDecoration {

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() != null && parent.getAdapter().getItemCount() > 1) {

            View topView = parent.getChildAt(0);
            View secondView = parent.getChildAt(1);

            View headerView = getHeaderOf(parent, topView);

            if (isHeaderView(parent, secondView)) {
                if (secondView.getTop() <= headerView.getHeight()) {
                    c.translate(0, secondView.getTop() - headerView.getHeight());
                }
            }
            headerView.draw(c);
            c.save();
            c.translate(0, 0);
        }
    }

    /**
     * Determines whether the given View is a header.
     *
     * @param parent Parent of given view.
     * @param view   View to be checked.
     * @return true if the view is a header, false otherwise
     */
    private boolean isHeaderView(RecyclerView parent, View view) {
        if (parent.getAdapter() instanceof RecyclerListAdapter) {
            int childPosition = parent.getChildPosition(view);
            RecyclerListAdapter adapter = (RecyclerListAdapter) parent.getAdapter();
            return adapter.getItemViewType(childPosition) == RecyclerListAdapter.HEADER;
        }
        return false;
    }

    /**
     * Checks whether the given position is occupied by a header.
     *
     * @param parent   RecyclerView that contains the given position.
     * @param position Position to be checked.
     * @return true if the position is occupied by a header. false otherwise.
     */
    private boolean isHeaderPosition(RecyclerView parent, int position) {
        RecyclerListAdapter adapter = (RecyclerListAdapter) parent.getAdapter();
        return adapter.getItemViewType(position) == RecyclerListAdapter.HEADER;
    }


    /**
     * Finds the header of given view.
     *
     * @param parent Parent of the view.
     * @param view   View itself whose header will be found.
     * @return If the given View is already a header, returns that view.
     * If the view doesn't have a header returns null.
     * If the view has a header returns the view's header.
     */
    private View getHeaderOf(RecyclerView parent, View view) {
        if (isHeaderView(parent, view)) {
            return view;
        } else {
            int position = parent.getChildPosition(view);
            RecyclerListAdapter adapter = (RecyclerListAdapter) parent.getAdapter();
            for (int i = position; i >= 0; i--) {
                if (isHeaderPosition(parent, i)) {
                    RecyclerListAdapter.Holder viewHolder = adapter.onCreateViewHolder(parent, 0); //create its viewholder.
                    adapter.onBindViewHolder(viewHolder, i);

                    View header = viewHolder.itemView;

                    if (header.getLayoutParams() == null) {
                        header.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                    }

                    int widthSpec;
                    int heightSpec;

                    widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
                    heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

                    int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
                    int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);
                    header.measure(childWidth, childHeight);
                    header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
                    return header;
                }
            }
        }
        return null;
    }
}
