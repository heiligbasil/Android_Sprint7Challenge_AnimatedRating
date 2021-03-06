package com.lambdaschool.android_sprint4challenge_animatedrating;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class AnimatedRating extends LinearLayout {

    public static ArrayList<Integer> imageViewResources;

    public AnimatedRating(Context context) {
        super(context);
        init(null);
    }

    public AnimatedRating(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AnimatedRating(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public AnimatedRating(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    int maxRating, startRating, currentRating, symbolFilling, symbolFilled, symbolEmptying, symbolEmpty;
    ViewGroup inflatedViewGroup;

    protected void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AnimatedRating);
        maxRating = typedArray.getInt(R.styleable.AnimatedRating_max_rating, 7);
        startRating = typedArray.getInt(R.styleable.AnimatedRating_start_rating, 3);
        symbolFilling = typedArray.getResourceId(R.styleable.AnimatedRating_symbol_filling, R.drawable.avd_line_star_fill);
        symbolFilled = typedArray.getResourceId(R.styleable.AnimatedRating_symbol_filled, R.drawable.avd_star_filled);
        symbolEmptying = typedArray.getResourceId(R.styleable.AnimatedRating_symbol_emptying, R.drawable.avd_line_star_empty);
        symbolEmpty = typedArray.getResourceId(R.styleable.AnimatedRating_symbol_empty, R.drawable.avd_star_empty);
        setCurrentRating(startRating - 1);
        inflatedViewGroup = (ViewGroup) inflate(getContext(), R.layout.animated_rating_group_layout, null);
        for (int i = 0; i < maxRating; ++i) {
            ImageView imageView = new ImageView(getContext(), attrs);
            LayoutParams layoutParams = new LayoutParams(getContext(), attrs);
            layoutParams.width = 100;
            layoutParams.height = 100;
            imageView.setLayoutParams(layoutParams);
            if (i < startRating)
                imageView.setImageDrawable(getResources().getDrawable(symbolFilled));
            else
                imageView.setImageDrawable(getResources().getDrawable(symbolEmpty));

            imageView.setTag(i);
            imageView.setOnClickListener(onClickListener);
            inflatedViewGroup.addView(imageView);
        }
        this.addView(inflatedViewGroup);

        typedArray.recycle();
    }

    public int getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(int currentRating) {
        this.currentRating = currentRating;
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            int ratingClicked = (int) v.getTag();

            Drawable drawable = getResources().getDrawable(symbolFilled);
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;

            if (ratingClicked > getCurrentRating()) { // Increase rating
                for (int i = 0; i < maxRating; ++i) {
                    if (i < ratingClicked && i <= getCurrentRating())
                        drawable = v.getResources().getDrawable(symbolFilled);
                    else if (i < ratingClicked)
                        drawable = v.getResources().getDrawable(symbolFilling);
                    else if (i == ratingClicked)
                        drawable = v.getResources().getDrawable(symbolFilling);
                    else
                        drawable = v.getResources().getDrawable(symbolEmpty);

                    animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                    ((ImageView) inflatedViewGroup.getChildAt(i)).setImageDrawable(drawable);
                    animatedVectorDrawable.start();
                }
            } else if (ratingClicked < getCurrentRating()) { // Decrease rating
                for (int i = 0; i < maxRating; ++i) {
                    if (i < ratingClicked)
                        drawable = v.getResources().getDrawable(symbolFilled);
                    else if (i == ratingClicked)
                        drawable = v.getResources().getDrawable(symbolFilled);
                    else if (i > ratingClicked && i > getCurrentRating())
                        drawable = v.getResources().getDrawable(symbolEmpty);
                    else
                        drawable = v.getResources().getDrawable(symbolEmptying);

                    animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                    ((ImageView) inflatedViewGroup.getChildAt(i)).setImageDrawable(drawable);
                    animatedVectorDrawable.start();
                }
            } else {

            }


            setCurrentRating(ratingClicked);

        }
    };
}
