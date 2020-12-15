package com.app.firebasefulldemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.firebasefulldemo.Model.CustomModel;
import com.app.firebasefulldemo.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.holder> {
    List<CustomModel> datamodel;
    Context context;
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context ).inflate( R.layout.row_items,parent,false );
        return new holder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        CustomModel customModel= datamodel.get( position );
        holder.stdName.setText( datamodel.get( position ).getName() );
        holder.stdCource.setText( datamodel.get( position ).getCourse() );
        holder.stdContact.setText( datamodel.get( position ).getContact() );

    }

    @Override
    public int getItemCount() {
        return datamodel.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView stdName,stdCource,stdContact;
        public holder(@NonNull View itemView) {
            super( itemView );
            image=itemView.findViewById( R.id.profile );
            stdName=itemView.findViewById( R.id.name );
            stdCource=itemView.findViewById( R.id.course );
            stdContact=itemView.findViewById( R.id.contact );
        }
    }
}
