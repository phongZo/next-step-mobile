package itz.next_step.android.ui.main.postDetail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PostDetailViewPagerAdapter extends FragmentStateAdapter {
    public PostDetailViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new InfoJobFragment();
        else if (position == 1) return new CompanyFragment();
        else return new CompetitionFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
