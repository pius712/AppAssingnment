package com.example.pius712.appassingnment;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pius712.appassingnment.object.Obj_lost_info;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder>{
    private ArrayList<Obj_lost_info> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView stuff;
        TextView seekerID;
        TextView phone;
        TextView description;
//        ImageView imageURI;


        public MyViewHolder(View view) {
            super(view);
            stuff = view.findViewById(R.id.stuff);
            seekerID = view.findViewById(R.id.seekerID);
            phone = view.findViewById(R.id.phone);
            description = view.findViewById(R.id.description);
//            imageURI = view.findViewById(R.id.image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeRecyclerAdapter(ArrayList<Obj_lost_info> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Obj_lost_info tmpData =mDataset.get(position);

        holder.stuff.setText(tmpData.stuff);
        holder.seekerID.setText(tmpData.seekerID);
        holder.phone.setText(tmpData.phone);
        holder.description.setText(tmpData.description);
//        holder.imageURI.setImageURI(Uri.parse(tmpData.imageURI));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset ==null? 0: mDataset.size();
    }

}
