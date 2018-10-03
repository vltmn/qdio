package io.hamo.qdio.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.hamo.qdio.R;
import io.hamo.qdio.music.Artist;
import io.hamo.qdio.music.Track;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {
    private final OnListItemClickedListener onResultItemListener;
    private List<Track> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View inflated = inflater.inflate(R.layout.search_result_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(inflated);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Track t = data.get(i);
        viewHolder.bind(t, onResultItemListener);
    }

    public TrackListAdapter(List<Track> results, OnListItemClickedListener onResultItemListener) {
        data = results;
        this.onResultItemListener = onResultItemListener;
    }

    public void setData(List<Track> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnListItemClickedListener {
        void onSearchResultItemClicked(Track t);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView songNameView;
        TextView artistNameView;

        ViewHolder(@NonNull View inflated) {
            super(inflated);
            this.songNameView = inflated.findViewById(R.id.songName);
            this.artistNameView = inflated.findViewById(R.id.artistName);
        }


        public void bind(final Track t, final OnListItemClickedListener onListItemClickedListener) {
            songNameView.setText(t.getName());
            StringBuilder builder = new StringBuilder();
            for (Artist a : t.getArtists()) {
                if (builder.length() > 0) builder.append(", ");
                builder.append(a.getName());
            }
            artistNameView.setText(builder.toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListItemClickedListener.onSearchResultItemClicked(t);
                }
            });
        }
    }
}
