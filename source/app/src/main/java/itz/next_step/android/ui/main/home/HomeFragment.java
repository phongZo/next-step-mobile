package itz.next_step.android.ui.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import itz.next_step.android.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.FragmentHomeBinding;
import itz.next_step.android.di.component.FragmentComponent;
import itz.next_step.android.ui.base.fragment.BaseFragment;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    private PostsAdapter postsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        performDataBinding();
        View searchPlate = binding.searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackground(null);
        }
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private final Handler handler = new Handler();
            private Runnable searchRunnable;
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchJob(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> viewModel.searchJob(newText);

                handler.postDelayed(searchRunnable, 500);

                return true;
            }
        });
        postsAdapter = new PostsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcvRecommendedJobs.setLayoutManager(linearLayoutManager);
        binding.rcvRecommendedJobs.setAdapter(postsAdapter);

        viewModel.fetchPostList();
        viewModel.getPostList().observe(getViewLifecycleOwner(), postClientListResponses -> {

            if(postClientListResponses != null){
                viewModel.searchJob("");
            }
        });
        viewModel.getFilteredPostList().observe(getViewLifecycleOwner(), filteredList -> {
            postsAdapter.setData(filteredList);
        });
        return binding.getRoot();
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);

    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
