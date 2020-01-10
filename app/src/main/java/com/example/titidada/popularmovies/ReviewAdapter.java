package com.example.titidada.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.titidada.popularmovies.model.UserReview;
import com.example.titidada.popularmovies.utilities.JsonUtils;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.NumberViewHolder> {
    private int numReviews = 0;
    private List<UserReview> reviews;

    public ReviewAdapter(String queryResult){
        refreshReviews(queryResult);
    }

    public void refreshReviews(String queryResult){
        reviews = JsonUtils.parseReviewsJson(queryResult);
        numReviews = (reviews != null) ? reviews.size() : 0;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int i) {
        UserReview review = reviews.get(i);
        StringBuffer reviewDetail = new StringBuffer(review.getAuthor());
        reviewDetail.append(":\n");
        reviewDetail.append(review.getContent());
        numberViewHolder.textView.setText(reviewDetail.toString());

    }

    @Override
    public int getItemCount() {
        return numReviews;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public NumberViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.item_review);
        }

    }
}
