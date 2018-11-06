package com.meetic.shuffle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meetic.dragueur.DraggableView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

public class CardDraggableView extends DraggableView {

    int colorLeft;
    int colorRight;

    @Nullable
    View overlayLeft;
    @Nullable
    View overlayRight;
    ViewGroup overlay;

    int layoutLeftResId;
    int layoutRightResId;

    ViewGroup content;

    public CardDraggableView(Context context) {
        super(context);
    }

    public CardDraggableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardDraggableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewGroup getOverlayView() {
        return overlay;
    }

    public ViewGroup getContent() {
        return content;
    }

    public void setOverlayColors(int colorLeft, int colorRight) {
        this.colorLeft = colorLeft;
        this.colorRight = colorRight;
    }

    @Override
    public void reset() {
        ViewCompat.setAlpha(content, 1f);
        ViewCompat.setAlpha(overlay, 0f);
    }

    public void setOverlayLayouts(int layoutLeftResId, int layoutRightResId) {
        if (layoutLeftResId != 0) {
            this.layoutLeftResId = layoutLeftResId;
            if (overlayLeft != null) {
                overlay.removeView(overlayLeft);
            }
            overlayLeft = LayoutInflater.from(getContext()).inflate(this.layoutLeftResId, overlay, false);
            if (overlayLeft != null) {
                overlay.addView(overlayLeft);
                setOverlayLeftAlpha(0f);
            }
        }

        if (layoutRightResId != 0) {
            this.layoutRightResId = layoutRightResId;
            if (overlayRight != null) {
                overlay.removeView(overlayRight);
            }
            overlayRight = LayoutInflater.from(getContext()).inflate(this.layoutRightResId, overlay, false);
            if (overlayRight != null) {
                overlay.addView(overlayRight);
                setOverlayRightAlpha(0f);
            }
        }
    }

    public int getColorLeft() {
        return colorLeft;
    }

    public int getColorRight() {
        return colorRight;
    }

    @Nullable
    public View getOverlayLeft() {
        return overlayLeft;
    }

    public void setOverlayLeftAlpha(float alpha) {
        if (overlayLeft != null) {
            ViewCompat.setAlpha(overlayLeft, alpha);
        }
    }

    public void setOverlayRightAlpha(float alpha) {
        if (overlayRight != null) {
            ViewCompat.setAlpha(overlayRight, alpha);
        }
    }

    @Nullable
    public View getOverlayRight() {
        return overlayRight;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.shuffle_card, this);
        content = findViewById(R.id.content);
        overlay = findViewById(R.id.overlay);
    }
}
