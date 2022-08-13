package com.fakhrads.uasakbif3101191116.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.fakhrads.uasakbif3101191116.R;
import com.fakhrads.uasakbif3101191116.ViewPagerAdapter;
import com.fakhrads.uasakbif3101191116.intro_page1;
import com.fakhrads.uasakbif3101191116.intro_page2;
import com.fakhrads.uasakbif3101191116.intro_page3;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fakhrads.uasakbif3101191116.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DotsIndicator dotsIndicator;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = getActivity().findViewById(R.id.viewpager);

        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new intro_page1(), "");
        viewPagerAdapter.add(new intro_page2(), "");
        viewPagerAdapter.add(new intro_page3(), "");

        // Set the adapter

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        dotsIndicator = (DotsIndicator) getActivity().findViewById(R.id.dots_indicator);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        dotsIndicator.setViewPager(viewPager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}