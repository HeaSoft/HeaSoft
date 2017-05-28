package com.development.mobile.heasoft.heasoft;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, null);
        ImageView aboutHea = (ImageView) view.findViewById(R.id.heasoft_image);
        Animation aboutTrans = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.about_trans);
        aboutHea.startAnimation(aboutTrans);

        return view;
    }
}
