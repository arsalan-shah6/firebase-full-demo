package com.app.firebasefulldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.app.firebasefulldemo.Adapter.MyAdapter;
import com.app.firebasefulldemo.Model.CustomModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RetrieveDataActivity extends AppCompatActivity {

   MyAdapter adapter;
    DatabaseReference mRef;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_retrieve_data );
        recyclerView=findViewById( R.id.recycler_view );
       recyclerView.setLayoutManager( new LinearLayoutManager( this ) );



      FirebaseRecyclerOptions<CustomModel> options =
                new FirebaseRecyclerOptions.Builder<CustomModel>()
                        .setQuery(  FirebaseDatabase.getInstance().getReference( "Users" ), CustomModel.class)
                        .build();
        adapter=new MyAdapter( options );
        recyclerView.setAdapter( adapter );
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu,menu );
        MenuItem item=menu.findItem( R.id.search );
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ProcessSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ProcessSearch(s);
                return false;
            }
        } );
        return super.onCreateOptionsMenu( menu );
    }

    private void ProcessSearch(String s) {
        FirebaseRecyclerOptions<CustomModel> options =
                new FirebaseRecyclerOptions.Builder<CustomModel>()
                        .setQuery( FirebaseDatabase.getInstance().getReference( "Users" ).orderByChild( "Name").startAt( s ).endAt( s+"\uf8ff" ), CustomModel.class)
                        .build();
        adapter=new MyAdapter( options );
        adapter.startListening();
        recyclerView.setAdapter( adapter );

    }

}