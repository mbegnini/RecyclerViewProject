package br.com.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FilmeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Filme> listaFilmes = new ArrayList<Filme>();
        listaFilmes.add(new Filme(R.drawable.reservoir_dogs,"Reservoir Dogs","Crime",1992));
        listaFilmes.add(new Filme(R.drawable.pulp_fiction,"Pulp Fiction","Crime",1994));
        listaFilmes.add(new Filme(R.drawable.kill_bill,"Kill Bill","Action",2003));
        listaFilmes.add(new Filme(R.drawable.inglourious_basterds,"Inglourious Basterds","War",2009));
        listaFilmes.add(new Filme(R.drawable.django_unchained,"Django Unchained","Western",2012));
        listaFilmes.add(new Filme(R.drawable.the_hateful_eight,"The Hateful Eigh","Western",2015));
        listaFilmes.add(new Filme(R.drawable.once_upon_a_time_in_hollywood,"Once Upon a Time... in Hollywood","Comedy",2019));        adapter = new FilmeAdapter(listaFilmes);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
