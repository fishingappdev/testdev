package com.dev.fishingapp.myfish.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.fishingapp.AbstractActivity;
import com.dev.fishingapp.HomeActivity;
import com.dev.fishingapp.LoaderCallbacks.FishCategoryCallback;
import com.dev.fishingapp.R;
import com.dev.fishingapp.data.model.MyFishResponse;
import com.dev.fishingapp.myfish.support.FishListAdapter;
import com.dev.fishingapp.support.BaseToolbarFragment;
import com.dev.fishingapp.util.AlertMessageDialog;
import com.dev.fishingapp.util.AppConstants;
import com.dev.fishingapp.util.FishingPreferences;

/**
 * Created by user on 6/4/2016.
 */
public class FishCategoryFragment extends BaseToolbarFragment implements AdapterView.OnItemClickListener {

    private ListView mFishList;
    private FishListAdapter mFishListAdapter;
    private LoaderManager loaderManager;
    private AlertMessageDialog dialog;
    MyFishResponse fishList;
    private String uId,catId;
    private FishCategoryBroadcastReceiver receiver;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        catId= args.getString("catid");
        return inflater.inflate(R.layout.fragment_myfish, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderManager = getActivity().getSupportLoaderManager();
        ((HomeActivity) getActivity()).setToolBarTitle(getResources().getString(R.string.my_fish_header));
        ((HomeActivity) getActivity()).showRightOption(HomeActivity.ADD_FISH_OPTION, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.content_frame, new AddFishFragment()).hide(FishCategoryFragment.this).addToBackStack(null).commit();
*/            addFragmentWithBackStack(new AddFishFragment());
            }
        });
        mFishList=(ListView)view.findViewById(R.id.myfish_list);
       uId= FishingPreferences.getInstance().getCurrentUserId();

        if(loaderManager.getLoader(R.id.loader_fish_category)== null){
            loaderManager.initLoader(R.id.loader_fish_category, null, new FishCategoryCallback(((AbstractActivity) getActivity()),true,uId,catId));
        } else {
            loaderManager.restartLoader(R.id.loader_fish_category, null, new FishCategoryCallback(((AbstractActivity) getActivity()),true,uId,catId));
        }




    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("My Fish category", "On RESUME");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(AppConstants.FISH_CATEGORY_CALLBACK_BROADCAST);
        receiver = new FishCategoryBroadcastReceiver();
        localBroadcastManager.registerReceiver(receiver, intentFilter);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).setCurrentFragment(FishCategoryFragment.this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("My Fish category", "On stop");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(receiver);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("nid", fishList.get(position).getNid());
        Fragment fragment=new FishDetailFragment();
        fragment.setArguments(bundle);
        addFragmentWithBackStack(fragment);
      /*  FragmentManager fm= getActivity().getSupportFragmentManager();
        fm.beginTransaction().add(R.id.content_frame,fragment).hide(this).addToBackStack(null).commit();*/
    }

    class FishCategoryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(AppConstants.FISH_CATEGORY_CALLBACK_BROADCAST)) {
                if (intent.getSerializableExtra("data") != null) {
                    fishList=(MyFishResponse)intent.getSerializableExtra("data");
                    if(fishList.size()>0){
                        mFishListAdapter=new FishListAdapter(getActivity(),fishList);
                        mFishList.setAdapter(mFishListAdapter);
                        mFishList.setOnItemClickListener(FishCategoryFragment.this);
                    }else{
                        dialog=new AlertMessageDialog(((HomeActivity)getActivity()),getActivity().getString(R.string.error_txt),getString(R.string.empty_list));
                        dialog.setAcceptButtonText(getString(R.string.ok_txt));
                        dialog.show();

                    }

                }

            }
        }
    }

}
