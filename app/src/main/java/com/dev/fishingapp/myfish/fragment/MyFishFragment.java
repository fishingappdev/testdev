package com.dev.fishingapp.myfish.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyFish;
import com.dev.fishingapp.myfish.support.FishListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;

import java.util.ArrayList;

/**
 * Created by user on 4/18/2016.
 */
public class MyFishFragment extends BaseToolbarFragment implements AdapterView.OnItemClickListener{
    private ListView mFishList;
    private ArrayList<MyFish> myFishArrayList;
    private FishListAdapter mFishListAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myfish, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.my_fish_header));
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.content_frame, new AddFishFragment()).hide(MyFishFragment.this).addToBackStack(null).commit();
            }
        });
        mFishList=(ListView)view.findViewById(R.id.myfish_list);
        myFishArrayList=new ArrayList<>();
        for(int i=0;i<5;i++){
            MyFish myFish=new MyFish("LONTAIL IN MI","USA","0.80kG","5 to 6 inches","21/04/16");
            myFishArrayList.add(myFish);
        }
        mFishListAdapter=new FishListAdapter(getActivity(),myFishArrayList);
        mFishList.setAdapter(mFishListAdapter);
        mFishList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fm= getActivity().getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content_frame,new FishDetailFragment()).hide(this).addToBackStack(null).commit();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("setUserVisibleHint", "MyFish Fragment"+hidden);
        if (!hidden) {
            ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.add(R.id.content_frame, new AddFishFragment()).hide(MyFishFragment.this).addToBackStack(null).commit();
                }
            });
        }

    }
}
