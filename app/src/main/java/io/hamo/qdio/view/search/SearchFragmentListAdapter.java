package io.hamo.qdio.view.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.hamo.qdio.music.Track;

public class SearchFragmentListAdapter extends RecyclerView.Adapter<SearchFragmentListAdapter.ViewHolder>{
    private List<Track> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView textView = new TextView(viewGroup.getContext());
        ViewHolder vh = new ViewHolder(textView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(data.get(i).getName());
    }

    public SearchFragmentListAdapter(List<Track> results) {
        data = results;
    }

    public void setData(List<Track> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        ViewHolder(@NonNull TextView textView) {
            super(textView);
            this.textView = textView;
        }


    }
}
