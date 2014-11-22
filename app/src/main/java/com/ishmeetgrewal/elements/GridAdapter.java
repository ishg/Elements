package com.ishmeetgrewal.elements;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Ishmeet Grewals on 11/21/2014.
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
            R.drawable.icon_blank,
    };

    // Constructor
    public GridAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setBackgroundColor(Color.rgb(238,238,238));
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        }else{
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

}
