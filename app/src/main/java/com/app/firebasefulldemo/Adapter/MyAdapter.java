package com.app.firebasefulldemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.firebasefulldemo.Model.CustomModel;
import com.app.firebasefulldemo.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends FirebaseRecyclerAdapter<CustomModel,MyAdapter.viewholder> {
    Context context;
    public MyAdapter(@NonNull FirebaseRecyclerOptions<CustomModel> options) {
        super( options );
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull CustomModel model) {
        holder.stdName.setText( model.getName() );
        holder.stdCource.setText( model.getCourse() );
        holder.stdContact.setText( model.getContact() );
        Glide.with(  holder.image.getContext()).load( model.getImageLink() ).into( holder.image );
        holder.edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.image.getContext())
                        .setContentHolder( new ViewHolder( R.layout.dialog_view ) )
                        .setExpanded( true,1100 )
                        .create();
                     View myview=dialogPlus.getHolderView();
                EditText ImageLink=myview.findViewById( R.id.image_link );
                EditText Name=myview.findViewById( R.id.name );
                EditText Course=myview.findViewById( R.id.course );
                EditText Contact=myview.findViewById( R.id.contact );
            }
        } );
        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.row_items,parent,false );
       return new viewholder( view );
    }

   public class viewholder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView stdName,stdCource,stdContact;
        Button edit,delete;
        public viewholder(@NonNull View itemView) {
            super( itemView );
            image=itemView.findViewById( R.id.profile );
            stdName=itemView.findViewById( R.id.name );
            stdCource=itemView.findViewById( R.id.course );
            stdContact=itemView.findViewById( R.id.contact );
            edit=itemView.findViewById( R.id.edit );
            delete=itemView.findViewById( R.id.delete );
        }
    }
}
