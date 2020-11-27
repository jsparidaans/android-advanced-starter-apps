package com.example.android.fragmentexample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragment extends Fragment {

    //Constants
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    //Bundle keys
    private static final String CHOICE = "choice";

    public int mRadioButtonChoice = NONE;
    OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
    }

    public SimpleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param choice radio button choice
     * @return A new instance of fragment SimpleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        final RatingBar ratingBar = rootView.findViewById(R.id.rating_bar);

        if (getArguments().containsKey(CHOICE)) {
            //Get choice
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            if (mRadioButtonChoice != NONE) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener((group, checkId) -> {
            View radioButton = radioGroup.findViewById(checkId);
            int index = radioGroup.indexOfChild(radioButton);
            TextView textView = rootView.findViewById(R.id.fragment_header);
            switch (index) {
                case YES:
                    textView.setText(R.string.yes_message);
                    mRadioButtonChoice = YES;
                    mListener.onRadioButtonChoice(YES);
                    break;
                case NO:
                    textView.setText(R.string.no_message);
                    mRadioButtonChoice = NO;
                    mListener.onRadioButtonChoice(NO);
                    break;
                default:
                    mRadioButtonChoice = NONE;
                    mListener.onRadioButtonChoice(NONE);
                    break;
            }
        });

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> Toast.makeText(getContext(), "My rating: " + rating, Toast.LENGTH_SHORT).show());

        return rootView;
    }
}