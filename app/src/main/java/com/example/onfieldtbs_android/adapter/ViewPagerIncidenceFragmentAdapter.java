package com.example.onfieldtbs_android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.onfieldtbs_android.ui.views.AllIncidenceFragment;
import com.example.onfieldtbs_android.ui.views.MyIncidenceFragment;
public class ViewPagerIncidenceFragmentAdapter extends FragmentStateAdapter {


    public ViewPagerIncidenceFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new MyIncidenceFragment();
            case 1:
                return new AllIncidenceFragment();
        }

        return new MyIncidenceFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
