package itz.next_step.android.ui.main.home;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import itz.next_step.android.data.model.api.response.company.CompanyResponse;
import itz.next_step.android.data.model.api.response.post.PostClientListResponse;
import itz.next_step.android.databinding.ItemJobBinding;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<PostClientListResponse<CompanyResponse>> postList = new ArrayList<>();
    private static OnPostClickListener listener;
    public interface OnPostClickListener {
        void onItemClick(Long postId);
    }
    private HomeViewModel viewModel;
    public PostsAdapter(HomeViewModel viewModel, OnPostClickListener listener){
        this.viewModel = viewModel;
        this.listener = listener;
    }

    public void setData(List<PostClientListResponse<CompanyResponse>> newData){
        postList.clear();

        if(newData != null){
            postList.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemJobBinding binding = ItemJobBinding.inflate(inflater,parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("PostsAdapter", "Binding item at position: " + position);
        if(holder instanceof PostViewHolder){
            ((PostViewHolder) holder).bind(postList.get(position), viewModel);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    static class PostViewHolder extends RecyclerView.ViewHolder{
        private final ItemJobBinding binding;
        private final MutableLiveData<Bitmap> liveLogo;
        public PostViewHolder(ItemJobBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.liveLogo = new MutableLiveData<>();
        }
        public void bind(PostClientListResponse<CompanyResponse> item, HomeViewModel viewModel){
            binding.tvJobPosition.setText(item.getName());
            binding.tvCompanyName.setText(item.getCompany().getName());

            int minSalary = item.getMinSalary();
            int maxSalary = item.getMaxSalary();

            String salaryText = (minSalary / 1000000) + " - " + (maxSalary/1000000) + " triệu";
            binding.tvSalary.setText(salaryText);

            liveLogo.observe((LifecycleOwner) binding.getRoot().getContext(), bitmap -> {
                if (bitmap != null) {
                    binding.ivLogo.setImageBitmap(bitmap);
                }
            });
            if(item!= null && item.getCompany().getLogo() != null){
                String url = item.getCompany().getLogo();
                viewModel.loadLogo(url, liveLogo);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (item != null) {
                    listener.onItemClick(item.getId());
                }
            });
        }
    }
}
