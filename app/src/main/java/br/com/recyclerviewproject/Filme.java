package br.com.recyclerviewproject;

import android.os.Parcel;
import android.os.Parcelable;


public class Filme implements Parcelable {
    private long id;
    private int imagemCapa;
    private String titulo;
    private String genero;
    private int ano;

    public Filme(){
        id = -1;
    }

    public Filme(long id, int imagemCapa, String titulo, String genero, int ano) {
        this.id = id;
        this.imagemCapa = imagemCapa;
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(int imagemCapa) {
        this.imagemCapa = imagemCapa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(imagemCapa);
        dest.writeString(titulo);
        dest.writeString(genero);
        dest.writeInt(ano);
    }

    public void readFromParcel(Parcel parcel){
        this.id = parcel.readLong();
        this.imagemCapa = parcel.readInt();
        this.titulo = parcel.readString();
        this.genero = parcel.readString();
        this.ano = parcel.readInt();
    }

    public static final Parcelable.Creator<Filme> CREATOR = new Parcelable.Creator<Filme>(){
        @Override
        public Filme createFromParcel (Parcel p){
            Filme f = new Filme();
            f.readFromParcel(p);
            return f;
        }
        @Override
        public Filme[] newArray(int size){
            return new Filme[size];
        }
    };
}
