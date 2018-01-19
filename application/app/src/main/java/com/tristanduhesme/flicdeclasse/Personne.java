package com.tristanduhesme.flicdeclasse;

import android.graphics.drawable.Drawable;

/**
 * Created by Tristan on 20/12/2017.
 */

public class Personne {
    String prenom;
    String nom;
    String statut;
    int image;

    public Personne() {
        prenom = "Prénom";
        nom = "Nom";
        statut = "Statut";
        image = R.drawable.unknown;
    }
    public Personne(String p,String n,String s,int i) {
        prenom = p;
        nom = n;
        statut = s;
        image = i;
    }
}

