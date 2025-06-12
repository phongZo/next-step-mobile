package itz.next_step.android.ui.main.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import itz.next_step.android.R;
import itz.next_step.android.data.model.api.response.company.CompanyResponse;
import itz.next_step.android.data.model.api.response.post.PostClientListResponse;

public class PostsPagerAdapter extends RecyclerView.Adapter<PostsPagerAdapter.PageViewHolder> {
    private final List<List<PostClientListResponse<CompanyResponse>>> pages;
    private final HomeViewModel viewModel;

    public PostsPagerAdapter(List<List<PostClientListResponse<CompanyResponse>>> pages, HomeViewModel viewModel) {
        this.pages = pages;
        this.viewModel = viewModel;
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerViewInPage); // bạn cần layout cho 1 page
        }
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_post, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        PostsAdapter adapter = new PostsAdapter(viewModel);
        adapter.setData(pages.get(position));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }
}

