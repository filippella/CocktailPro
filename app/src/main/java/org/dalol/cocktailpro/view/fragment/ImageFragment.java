package org.dalol.cocktailpro.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import org.dalol.cocktailpro.R;

/**
 * Created by Filippo-TheAppExpert on 1/14/2016.
 */
public class ImageFragment extends Fragment {

    private ImageView mImageView;

    private int mImageId;

    public static ImageFragment newInstance(int imageId) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("imageID", imageId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageId = getArguments().getInt("imageID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cocktail_image, container, false);
        mImageView = (ImageView) view.findViewById(R.id.cocktailImage);
        if (mImageId != 0) {
            Picasso.with(getActivity())
                    .load(mImageId)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mImageView);
        }
        return view;
    }
}