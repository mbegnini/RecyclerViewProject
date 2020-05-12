package br.com.recyclerviewproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FilmeAdapter adapter;
    public final static int REQUEST_EDITAR_FILME = 1;
    public final static int REQUEST_INSERIR_FILME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FilmeAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelp(adapter));
        touchHelper.attachToRecyclerView(recyclerView);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_EDITAR_FILME){
            Bundle bundle = data.getExtras();
            Filme filme = (Filme) bundle.getParcelable("filme");
            int position = bundle.getInt("position");
            adapter.editar(filme, position);
        }
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_INSERIR_FILME){
            Bundle bundle = data.getExtras();
            Filme filme = (Filme) bundle.getParcelable("filme");
            adapter.inserir(filme);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.inserirMenu){
            Bundle bundle = new Bundle();
            bundle.putInt("request_code",REQUEST_INSERIR_FILME);
            Intent intent = new Intent(this, EditarFilmeActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_INSERIR_FILME);
        }
        if (id == R.id.carregarMenu){
            ArrayList<Filme> listaFilmes = new ArrayList<Filme>();
            listaFilmes.add(new Filme(R.drawable.reservoir_dogs,"Reservoir Dogs","Crime",1992));
            listaFilmes.add(new Filme(R.drawable.pulp_fiction,"Pulp Fiction","Crime",1994));
            listaFilmes.add(new Filme(R.drawable.kill_bill,"Kill Bill","Action",2003));
            listaFilmes.add(new Filme(R.drawable.inglourious_basterds,"Inglourious Basterds","War",2009));
            listaFilmes.add(new Filme(R.drawable.django_unchained,"Django Unchained","Western",2012));
            listaFilmes.add(new Filme(R.drawable.the_hateful_eight,"The Hateful Eigh","Western",2015));
            listaFilmes.add(new Filme(R.drawable.once_upon_a_time_in_hollywood,"Once Upon a Time... in Hollywood","Comedy",2019));
            for(Filme f:listaFilmes)
                adapter.inserir(f);
        }
        if (id == R.id.deletarMenu)
            adapter.deleteAll();
        return super.onOptionsItemSelected(item);
    }
}
