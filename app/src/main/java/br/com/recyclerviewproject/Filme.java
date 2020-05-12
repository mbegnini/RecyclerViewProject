package br.com.recyclerviewproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "filme")
public class Filme implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "img_capa")
    private int imagemCapa;
    @ColumnInfo(name = "titulo")
    private String titulo;
    @ColumnInfo(name = "genero")
    private String genero;
    @ColumnInfo(name = "ano")
    private int ano;

    public Filme(){

    }

    public Filme(int imagemCapa, String titulo, String genero, int ano) {
        this.imagemCapa = imagemCapa;
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
    }

    public Filme(long id, int imagemCapa, String titulo, String genero, int ano) {
        this.id = id;
        this.imagemCapa = imagemCapa;
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
    }

    protected Filme(Parcel in) {
        id = in.readLong();
        imagemCapa = in.readInt();
        titulo = in.readString();
        genero = in.readString();
        ano = in.readInt();
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

    public static final Creator<Filme> CREATOR = new Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel in) {
            Filme f = new Filme();
            f.setId(in.readLong());
            f.setImagemCapa(in.readInt());
            f.setTitulo(in.readString());
            f.setGenero(in.readString());
            f.setAno(in.readInt());
            return f;
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };
}
