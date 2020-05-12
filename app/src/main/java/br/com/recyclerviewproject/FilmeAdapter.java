package br.com.recyclerviewproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FilmeAdapter extends RecyclerView.Adapter {

    private List<Filme> listaFilmes;
    private int posicaoRemovidoRecentemente;
    private Filme filmeRemovidoRecentemente;
    AppCompatActivity activity;
    AppDatabase db;

    public FilmeAdapter(AppCompatActivity activity) {
        this.activity = activity;
        this.listaFilmes = new ArrayList<Filme>();
        db = AppDatabase.getDatabase(activity);
           AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Filme> lista = db.filmeDao().getAll();
                for(Filme f : lista)
                    listaFilmes.add(f);
            }
        });


        /*listaFilmes.add(new Filme(R.drawable.reservoir_dogs,"Reservoir Dogs","Crime",1992));
        listaFilmes.add(new Filme(R.drawable.pulp_fiction,"Pulp Fiction","Crime",1994));
        listaFilmes.add(new Filme(R.drawable.kill_bill,"Kill Bill","Action",2003));
        listaFilmes.add(new Filme(R.drawable.inglourious_basterds,"Inglourious Basterds","War",2009));
        listaFilmes.add(new Filme(R.drawable.django_unchained,"Django Unchained","Western",2012));
        listaFilmes.add(new Filme(R.drawable.the_hateful_eight,"The Hateful Eigh","Western",2015));
        listaFilmes.add(new Filme(R.drawable.once_upon_a_time_in_hollywood,"Once Upon a Time... in Hollywood","Comedy",2019));*/


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        FilmeViewHolder viewHolder = new FilmeViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        FilmeViewHolder viewHolder = (FilmeViewHolder) holder;
        viewHolder.capaImagemView.setImageResource(listaFilmes.get(position).getImagemCapa());
        viewHolder.tituloTextView.setText(listaFilmes.get(position).getTitulo());
        viewHolder.generoTextView.setText(listaFilmes.get(position).getGenero());
        viewHolder.anoTextView.setText(String.valueOf(listaFilmes.get(position).getAno()));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //utilizar getAdapterPosition e não position, isso evita que a mudança de posição do item na lista cause o acesso incorreto
                //somente uma activity pode iniciar outra activity, para resolver isso recebemos por parametro a MainActivity
                Intent intent = new Intent(activity.getBaseContext(), EditarFilmeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("filme",listaFilmes.get(holder.getAdapterPosition()));
                bundle.putInt("position",holder.getAdapterPosition());
                bundle.putInt("request_code",MainActivity.REQUEST_EDITAR_FILME);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, MainActivity.REQUEST_EDITAR_FILME);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public void remover(int position){
        posicaoRemovidoRecentemente = position;
        filmeRemovidoRecentemente = listaFilmes.get(position);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.filmeDao().deleteFilme(filmeRemovidoRecentemente);
            }
        });
        listaFilmes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,this.getItemCount());

        //precisamos de uma activity para inserir conteúdos graficos, para isso recebemos a MainActivity por parametro
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.constraintLayout), "Item deletado",Snackbar.LENGTH_LONG);
        snackbar.setAction("Desfazer?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.filmeDao().insertFilme(filmeRemovidoRecentemente);
                    }
                });
                listaFilmes.add(posicaoRemovidoRecentemente,filmeRemovidoRecentemente);
                notifyItemInserted(posicaoRemovidoRecentemente);
            }
        });
        snackbar.show();
    }

    public void restaurar(){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.filmeDao().insertFilme(filmeRemovidoRecentemente);
            }
        });
        listaFilmes.add(posicaoRemovidoRecentemente,filmeRemovidoRecentemente);
        notifyItemInserted(posicaoRemovidoRecentemente);
    }

    public void inserir(final Filme filme){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.filmeDao().insertFilme(filme);
            }
        });
        listaFilmes.add(filme);
        notifyItemInserted(getItemCount());
    }

    public void mover(int fromPosition, int toPosition){
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(listaFilmes, i, i+1);
        else
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(listaFilmes, i, i-1);
        notifyItemMoved(fromPosition,toPosition);
    }

    public void editar(final Filme filme, int position){
        listaFilmes.get(position).setTitulo(filme.getTitulo());
        listaFilmes.get(position).setGenero(filme.getGenero());
        listaFilmes.get(position).setAno(filme.getAno());
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.filmeDao().updateFilme(filme);
            }
        });
        notifyItemChanged(position);
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.filmeDao().deleteAll();
            }
        });
        while (!listaFilmes.isEmpty()){
            listaFilmes.remove(0);
            notifyItemRemoved(0);
            notifyItemRangeChanged(0,this.getItemCount());
        }
    }

    public static class FilmeViewHolder extends RecyclerView.ViewHolder{

        ImageView capaImagemView;
        TextView tituloTextView;
        TextView generoTextView;
        TextView anoTextView;

        public FilmeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            capaImagemView = (ImageView) itemView.findViewById(R.id.capaImageView);
            tituloTextView = (TextView) itemView.findViewById(R.id.tituloTextView);
            generoTextView = (TextView) itemView.findViewById(R.id.generoTextView);
            anoTextView = (TextView) itemView.findViewById(R.id.anoTextView);
        }
    }
}
