package br.com.recyclerviewproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FilmeDao {
    @Query("SELECT * FROM filme")
    List<Filme> getAll();

    @Query("SELECT * FROM filme WHERE id IN (:filmeIds)")
    List<Filme> loadAllByIds(int[] filmeIds);

    @Query("SELECT * FROM filme WHERE id = :id")
    Filme findById(int id);


    @Query("SELECT * FROM filme WHERE titulo LIKE :titulo LIMIT 1")
    Filme findByName(String titulo);

    @Insert
    void insertFilme(Filme filme);

    @Insert
    void insertAll(List<Filme> filmes);

    @Update
    void updateFilme(Filme filme);

    @Delete
    void deleteFilme(Filme filme);

    @Query("DELETE FROM filme")
    void deleteAll();
}
