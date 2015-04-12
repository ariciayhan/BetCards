package com.example.ayhan.myapplication.ui;

/**
 * Created by ayhan on 06/04/15.
 */
import flingswipe.SwipeFlingAdapterView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ayhan.myapplication.R;
import com.example.ayhan.myapplication.pos.BetSearcher;
import com.example.ayhan.myapplication.pos.sports.Event;
import com.example.ayhan.myapplication.ui.swipe.EventR;
import com.example.ayhan.myapplication.ui.swipe.EventAdapter;

import java.util.ArrayList;
import java.util.List;

public class BetCardsFragment extends android.support.v4.app.Fragment {
    private static final String TAG = BetCardsFragment.class.getName();

    private EventAdapter betAdapter;
    private int i;

    SwipeFlingAdapterView flingContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swipe, container, false);
        Log.d("BetCards", "OnCreateView");

        flingContainer = (SwipeFlingAdapterView) rootView.findViewById(R.id.swipeadapter);
        Log.d(TAG, "Before EventAdapter");
        betAdapter = new EventAdapter(getActivity(), R.layout.bet_view, new ArrayList<Event>());
        flingContainer.setAdapter(betAdapter);
        Log.d(TAG, " After EventAdapter");
        (new AsyncListViewLoader()).execute();


        //flingContainer.setAdapter(arrayAdapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                betAdapter.pop();
                betAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(getActivity(), "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(getActivity(), "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
               /* EventR tempObject = new EventR();
                tempObject.Participant1 = "Red".concat(String.valueOf(i));
                tempObject.Participant2 = "Blue".concat(String.valueOf(i));
                betAdapter.push(tempObject);
                betAdapter.notifyDataSetChanged();*/
                Log.d(TAG, "onAdapterAboutToEmpty");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(getActivity(), "Clicked!");
            }
        });

        return rootView;
    }

    public static BetCardsFragment newInstance() {
        BetCardsFragment fragment = new BetCardsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Event>> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPostExecute(List<Event> result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute Async Task" + result.size());
            dialog.dismiss();
            betAdapter.addAll(result);
            betAdapter.notifyDataSetChanged();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading bets for you...");
            dialog.show();
        }

        @Override
        protected List<Event> doInBackground(String... params) {
            List<Event> resultss = new ArrayList<>();
            Log.d(TAG, "doInBackground...");
            try {

                BetSearcher b = new BetSearcher();
                resultss = b.SearchBets();

                Log.d(TAG, "Before return doInBackground");
                return resultss;
               // Log.d("Tag", "Results count" + resultss.size());
             /*   new ArrayList<EventR>();
                EventR tempObject = new EventR();
                tempObject.Participant2 = "Blue";
                tempObject.Participant1 = "Red";
                result.add(tempObject);
                tempObject = new EventR();
                tempObject.Participant2 = "Besiktas";
                tempObject.Participant1 = "Galatasaray";
                result.add(tempObject);
                tempObject = new EventR();
                tempObject.Participant2 = "Barcelona";
                tempObject.Participant1 = "Sevilla";
                result.add(tempObject);
                tempObject = new EventR();
                tempObject.Participant2 = "FC Bayer";
                tempObject.Participant1 = "BVB";
                result.add(tempObject);
                tempObject = new EventR();
                tempObject.Participant2 = "Blue SMT";
                tempObject.Participant1 = "Red SMT";
                result.add(tempObject);
                tempObject = new EventR();
                tempObject.Participant2 = "L";
                tempObject.Participant1 = "R";*/


            }
            catch(Exception e) {
                Log.d(TAG, e.getMessage());
            }
            return resultss;
        }


    }



}