package com.example.startnewversionwithtabs;

import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

public class TransitionReviews extends TransitionSet {

    public TransitionReviews() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform());
    }
}
