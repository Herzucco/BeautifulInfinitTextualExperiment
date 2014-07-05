package com.stratos.beautifulinfinittextualexperiment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardFlipActivity {
    /**
     * A fragment representing the front of the card.
     */
    @SuppressLint("ValidFragment")
	public class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_main, container, false);
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    @SuppressLint("ValidFragment")
	public class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_list, container, false);
        }
    }
}
