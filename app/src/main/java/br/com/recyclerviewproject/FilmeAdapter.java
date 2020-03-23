package br.com.recyclerviewproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FilmeAdapter extends RecyclerView.Adapter {

    List<Filme> listaFilmes;

    public FilmeAdapter(List<Filme> listaFilmes) {
        this.listaFilmes = listaFilmes;
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
                Toast.makeText(v.getContext(),"Você clicou no filme "+listaFilmes.get(holder.getAdapterPosition()).getTitulo(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
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
