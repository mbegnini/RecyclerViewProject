package br.com.recyclerviewproject;

public class Filme {
    private int imagemCapa;
    private String titulo;
    private String genero;
    private int ano;

    public Filme(int imagemCapa, String titulo, String genero, int ano) {
        this.imagemCapa = imagemCapa;
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
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
}
