package com.example.resumenev2.recyclerview;

public class ItemLista {

    private int imagen;
    private String textoSup;
    private String textoInf;

    public ItemLista(int imagen, String textoSup, String textoInf) {
        this.imagen = imagen;
        this.textoSup = textoSup;
        this.textoInf = textoInf;
    }



    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTextoSup() {
        return textoSup;
    }

    public void setTextoSup(String textoSup) {
        this.textoSup = textoSup;
    }

    public String getTextoInf() {
        return textoInf;
    }

    public void setTextoInf(String textoInf) {
        this.textoInf = textoInf;
    }
}
