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
            Filme filme = (Filme) bundle.getSerializable("filme");
            int position = bundle.getInt("position");
            adapter.editar(filme, position);
        }
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_INSERIR_FILME){
            Bundle bundle = data.getExtras();
            Filme filme = (Filme) bundle.getSerializable("filme");
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
        return super.onOptionsItemSelected(item);
    }
}
