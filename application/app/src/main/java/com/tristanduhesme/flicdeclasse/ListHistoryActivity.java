package com.tristanduhesme.flicdeclasse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tristan on 20/12/2017.
 */

public class ListHistoryActivity extends Activity {
    ListView vue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_historique);
        vue = (ListView) findViewById(R.id.listHistory);
        Personne[] clum = new Personne[]{
                new Personne("André","Bonnelle", "12/01/2018 8:30", R.drawable.andre),
                new Personne("Quentin", "Fréminet","12/01/2018 8:23",R.drawable.quentin),
                new Personne("Rémi", "du Chalard", "12/01/2018 8:29",R.drawable.remi),
                new Personne("Tristan", "Duhesme","12/01/2018 18:30",R.drawable.tristan),
                new Personne("Jean-Baptiste", "Gaillot","12/01/2018 10:00",R.drawable.jb),
                new Personne("Frédérik", "de Moucheron","12/01/2018 8:30",R.drawable.frederik),
                new Personne("Sosthène", "d\'Hauthuille","12/01/2018 8:30",R.drawable.sosthene),
                new Personne("Jean", "Huré","12/01/2018 8:30",R.drawable.unknown),
                new Personne("Aymeric", "Doussau","12/01/2018 8:30",R.drawable.aymeric),
                new Personne("Thomas", "Jourdain","12/01/2018 8:30",R.drawable.thomas),
                new Personne("Jean-Vianney", "Turquan","12/01/2018 8:30",R.drawable.jv),
                new Personne("Amaury", "Duhesme", "12/01/2018 8:30",R.drawable.amaury)
        };

    /*
     * On doit donner à notre adaptateur une liste du type « List<Map<String, ?> » :
     *  - la clé doit forcément être une chaîne de caractères
     *  - en revanche, la valeur peut être n'importe quoi, un objet ou un entier par exemple,
     *  si c'est un objet, on affichera son contenu avec la méthode « toString() »
     *
     * Dans notre cas, la valeur sera une chaîne de caractères, puisque le nom et le numéro de téléphone
     * sont entreposés dans des chaînes de caractères
    */
        List<HashMap<String, Object>> liste = new ArrayList<>();

        HashMap<String,Object> element;
        //Pour chaque personne dans notre répertoire…
        for(int i = 0 ; i < clum.length ; i++) {
            //… on crée un élément pour la liste…
            element = new HashMap<String, Object>();
      /*
       * … on déclare que la clé est « text1 » (j'ai choisi ce mot au hasard, sans sens technique particulier)
       * pour le nom de la personne (première dimension du tableau de valeurs)…
      */
            element.put("prenom", clum[i].prenom);
      /*
       * … on déclare que la clé est « text2 »
       * pour le numéro de cette personne (seconde dimension du tableau de valeurs)
      */
            element.put("nom", clum[i].nom);
            element.put("statut", clum[i].statut);
            element.put("imageProfil", clum[i].image);
            //liste.add(element);
            liste.add(element);
        }

        ListAdapter adapter = new SimpleAdapter(this,
                //Valeurs à insérer
                liste,
      /*
       * Layout de chaque élément (là, il s'agit d'un layout par défaut
       * pour avoir deux textes l'un au-dessus de l'autre, c'est pourquoi on
       * n'affiche que le nom et le numéro d'une personne)
      */
                R.layout.item_liste_historique,
      /*
       * Les clés des informations à afficher pour chaque élément :
       *  - la valeur associée à la clé « text1 » sera la première information
       *  - la valeur associée à la clé « text2 » sera la seconde information
      */
                new String[] {"prenom", "nom","statut","imageProfil"},
      /*
       * Enfin, les layouts à appliquer à chaque widget de notre élément
       * (ce sont des layouts fournis par défaut) :
       *  - la première information appliquera le layout « android.R.id.text1 »
       *  - la seconde information appliquera le layout « android.R.id.text2 »
      */
                new int[] {R.id.prenom, R.id.nom, R.id.statut, R.id.imageProfil});
        //Pour finir, on donne à la ListView le SimpleAdapter
        vue.setAdapter(adapter);
    }
}
