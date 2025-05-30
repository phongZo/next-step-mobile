package itz.next_step.android.ui.main.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import itz.next_step.android.R;
import itz.next_step.android.data.model.api.response.company.CompanyResponse;
import itz.next_step.android.data.model.api.response.post.PostClientListResponse;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<PostClientListResponse<CompanyResponse>> postList = new ArrayList<>();

    public void setData(List<PostClientListResponse<CompanyResponse>> newData){
        postList.clear();

        if(newData != null){
            int size = Math.min(newData.size(), 6);
            postList.addAll(newData.subList(0, size));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("PostsAdapter", "Binding item at position: " + position);
        if(holder instanceof PostViewHolder){
            ((PostViewHolder) holder).bind(postList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    static class PostViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvJobPosition;
        private final TextView tvCompanyName;
        private final TextView tvSalary;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobPosition = itemView.findViewById(R.id.tvJobPosition);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvSalary = itemView.findViewById(R.id.tvSalary);
        }
        public void bind(PostClientListResponse<CompanyResponse> item){
            tvJobPosition.setText(item.getName());
            tvCompanyName.setText(item.getCompany().getName());

            int minSalary = item.getMinSalary();
            int maxSalary = item.getMaxSalary();

            String salaryText = (minSalary / 1000000) + " - " + (maxSalary/1000000) + " triệu";
            tvSalary.setText(salaryText);
        }
    }
}
