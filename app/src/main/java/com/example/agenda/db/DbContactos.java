package com.example.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agenda.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper{
    private static final String TABLE_CONTACTO = "t_contactos";

    Context context;
    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String telefono, String correo_electronico){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);
            id = db.insert(TABLE_CONTACTO, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Contactos> mostrarContactos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTO, null);
        if (cursorContactos.moveToFirst()){
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setCorreo_electronico(cursorContactos.getString(3));

                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }
        cursorContactos.close();
        return listaContactos;
    }

    public Contactos verContacto(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTO + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorContactos.moveToFirst()){
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setTelefono(cursorContactos.getString(2));
            contacto.setCorreo_electronico(cursorContactos.getString(3));
        }
        cursorContactos.close();
        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String telefono, String correo_electronico){
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CONTACTO + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', correo_electronico = '" + correo_electronico + "' WHERE id='" + id + "'");
            correcto = true;

        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
}


