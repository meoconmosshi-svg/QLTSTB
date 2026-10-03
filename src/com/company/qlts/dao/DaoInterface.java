package com.company.qlts.dao;

import java.util.ArrayList;

public interface DaoInterface<T> {

    public int insert(T t);

    public int update(T t);

    public int delete(T t);

    public ArrayList<T> getAll();

    public ArrayList<T> getAll(String condition);

    public T getByID(int id);
}