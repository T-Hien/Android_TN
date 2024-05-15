package com.example.multiplechoiceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.models.User;
import com.example.multiplechoiceapp.utils.ClickListener;
import com.example.multiplechoiceapp.viewholders.UserViewHolder;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAdapter  extends RecyclerView.Adapter<UserViewHolder> implements Filterable {

    private final ClickListener listener;

    List<User> list;

    List<User> filterUsers;

    Context context;

    public UserAdapter(ClickListener listener, List<User> list1, Context context) {
        this.listener = listener;
        this.filterUsers = list1;
        list = new ArrayList<>(list1);
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.share_card,parent,false);
        UserViewHolder userViewHolder = new UserViewHolder(view,listener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.imgUser.setImageResource(R.drawable.baseline_person_24);
        holder.fullName.setText(list.get(index).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(list);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (User item : filterUsers) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
