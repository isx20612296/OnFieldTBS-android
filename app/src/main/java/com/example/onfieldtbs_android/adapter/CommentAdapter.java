package com.example.onfieldtbs_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onfieldtbs_android.databinding.CommentRowBinding;
import com.example.onfieldtbs_android.models.Comment;
import com.example.onfieldtbs_android.utils.Utils;
import com.example.onfieldtbs_android.utils.mappers.CommentDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<CommentDate> comments;
    private Context context;

    public CommentAdapter(List<CommentDate> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CommentRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
//        List<Comment> comments = new ArrayList<>(commentSet);
        String fullUsername = "@" + comments.get(position).getTechnician().getUser().getUsername();
        holder.binding.commentUsername.setText(fullUsername);
        holder.binding.commentDate.setText(Utils.formatDateTime(comments.get(position).getCreatedAt().toString()));
        holder.binding.commentMessage.setText(comments.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CommentRowBinding binding;
        public ViewHolder(@NonNull CommentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
