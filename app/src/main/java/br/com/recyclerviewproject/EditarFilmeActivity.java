package br.com.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class EditarFilmeActivity extends AppCompatActivity {

    ImageView capaImageView;
    EditText tituloEditText;
    EditText generoEditText;
    EditText anoEditText;
    Filme filme;
    int position;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_filme);

        capaImageView = (ImageView) findViewById(R.id.capaImageView);
        tituloEditText = (EditText) findViewById(R.id.tituloEditText);
        generoEditText = (EditText) findViewById(R.id.generoEditText);
        anoEditText = (EditText) findViewById(R.id.anoEditText);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("request_code");

        if (requestCode==MainActivity.REQUEST_EDITAR_FILME){
            filme = (Filme) bundle.getSerializable("filme");
            position = bundle.getInt("position");

            capaImageView.setImageResource(filme.getImagemCapa());
            tituloEditText.setText(filme.getTitulo());
            generoEditText.setText(filme.getGenero());
            anoEditText.setText(String.valueOf(filme.getAno()));
        }else{
            filme = new Filme();
            filme.setImagemCapa(R.drawable.sem_capa);
            capaImageView.setImageResource(filme.getImagemCapa());
        }
    }

    public void concluir(View view){
        Bundle bundle = new Bundle();
        if (requestCode==1)
            bundle.putInt("position",position);

        filme.setTitulo(tituloEditText.getText().toString());
        filme.setGenero(generoEditText.getText().toString());
        filme.setAno(Integer.valueOf(anoEditText.getText().toString()));

        bundle.putSerializable("filme", filme);

        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
