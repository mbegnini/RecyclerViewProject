package br.com.recyclerviewproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FilmeAdapter adapter;

    private DBHelper mydb;

    public final static int REQUEST_EDITAR_FILME = 1;
    public final static int REQUEST_INSERIR_FILME = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);



        adapter = new FilmeAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ArrayList<Filme> listaFilmes = mydb.getAllMovies();
        for(Filme f:listaFilmes)
            adapter.inserir(f);

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

            mydb.updateFilme(filme);
            adapter.editar(filme, position);
        }
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_INSERIR_FILME){
            Bundle bundle = data.getExtras();
            Filme filme = (Filme) bundle.getParcelable("filme");
            long id = mydb.insertFilme(filme);
            filme.setId(id);
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
        if (id == R.id.deletarMenu){
            adapter.removerTodos();
        }
        if (id == R.id.carregarMenu){
            Filme f = new Filme(-1,R.drawable.reservoir_dogs,"Reservoir Dogs","Crime",1992);
            f.setId(mydb.insertFilme(f));
            adapter.inserir(f);
            f = new Filme(-1, R.drawable.pulp_fiction,"Pulp Fiction","Crime",1994);
            f.setId(mydb.insertFilme(f));
            adapter.inserir(f);
            f = new Filme(-1,R.drawable.kill_bill,"Kill Bill","Action",2003);
            f.setId(mydb.insertFilme(f));
            adapter.inserir(f);
            f = new Filme(-1,R.drawable.inglourious_basterds,"Inglourious Basterds","War",2009);
            f.setId(mydb.insertFilme(f));
            adapter.inserir(f);
            f = new Filme(-1,R.drawable.django_unchained,"Django Unchained","Western",2012);
            f.setId(mydb.insertFilme(f));
            adapter.inserir(f);
            f = new Filme(-1,R.drawable.the_hateful_eight,"The Hateful Eigh","Western",2015);
            f.setId(mydb.insertFilme(f));
            adapter.inserir(f);
            f = new Filme(-1,R.drawable.once_upon_a_time_in_hollywood,"Once Upon a Time... in Hollywood","Comedy",2019);
            f.setId(mydb.insertFilme(f));
            adapter.inserir(f);
        }
        return super.onOptionsItemSelected(item);
    }
}
