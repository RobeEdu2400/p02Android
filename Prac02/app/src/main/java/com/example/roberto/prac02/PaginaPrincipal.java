package com.example.roberto.prac02;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class PaginaPrincipal extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mArenasTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        mTitle = mDrawerTitle = getTitle();
        mArenasTitles = getResources().getStringArray(R.array.arenas_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mArenasTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new ArenasFragment();
        Bundle args = new Bundle();
        args.putInt(ArenasFragment.ARG_Arenas_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mArenasTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class ArenasFragment extends Fragment implements View.OnClickListener {
        public static final String ARG_Arenas_NUMBER = "Arenas_number";
        private int contador=0;

        public ArenasFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)  {
            int i = getArguments().getInt(ARG_Arenas_NUMBER);
            View rootView;
            if (i!=5) {
                rootView = inflater.inflate(R.layout.fragment_arenas, container, false);
            }
            else{
                rootView = inflater.inflate(R.layout.fragment_juego, container, false);
            }

            ImageButton button = (ImageButton) rootView.findViewById(R.id.imageButton14);
            ImageButton button1 = (ImageButton) rootView.findViewById(R.id.imageButton13);
            ImageButton button2 = (ImageButton) rootView.findViewById(R.id.imageButton12);
            ImageButton button3 = (ImageButton) rootView.findViewById(R.id.imageButton11);
            ImageButton button4 = (ImageButton) rootView.findViewById(R.id.imageButton10);
            ImageButton button5 = (ImageButton) rootView.findViewById(R.id.imageButton9);
            ImageButton button6 = (ImageButton) rootView.findViewById(R.id.imageButton8);
            ImageButton button7 = (ImageButton) rootView.findViewById(R.id.imageButton7);
            final TextView pregunta = (TextView) rootView.findViewById(R.id.textView8);
            Button buttonpregunta = (Button) rootView.findViewById(R.id.button);
            final EditText editText = (EditText) rootView.findViewById(R.id.editText);




            if (i != 5){
                button.setOnClickListener(this); // calling onClick() method
                button1.setOnClickListener(this); // calling onClick() method
                button2.setOnClickListener(this); // calling onClick() method
                button3.setOnClickListener(this); // calling onClick() method
                button4.setOnClickListener(this); // calling onClick() method
                button5.setOnClickListener(this); // calling onClick() method
                button6.setOnClickListener(this); // calling onClick() method
                button7.setOnClickListener(this); // calling onClick() method
            }
           switch (i){
               case 0:
                   Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.duendesconlanzas);
                   button.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.duendes);
                   button1.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.chozaduendes);
                   button2.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.valky);
                   button3.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.rayo);
                   button4.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.barril);
                   button5.setImageBitmap(bmp);

                   break;
               case 1:
                   bmp = BitmapFactory.decodeResource(getResources(), R.drawable.esqueletos);
                   button.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.esbirros);
                   button1.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.lapida);
                   button2.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.torrebombardera);
                   button3.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.esqueletogigante);
                   button4.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.globo);
                   button5.setImageBitmap(bmp);

                   break;
               case 2:
                   bmp = BitmapFactory.decodeResource(getResources(), R.drawable.canon);
                   button.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.barbaros);
                   button1.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.cohete);
                   button2.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.chozabarbaros);
                   button3.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.furia);
                   button4.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.ballesta);
                   button5.setImageBitmap(bmp);

                   break;
               case 3:
                   bmp = BitmapFactory.decodeResource(getResources(), R.drawable.tesla);
                   button.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.hordaesbirros);
                   button1.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.torreinfernal);
                   button2.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.monta);
                   button3.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.hielo);
                   button4.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.pekka);
                   button5.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.dragoninfernal);
                   button6.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.sabueso);
                   button7.setImageBitmap(bmp);
                   break;
               case 4:
                   bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zap);
                   button.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.espiritufuego);
                   button1.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.chozaespiritus);
                   button2.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.mago);
                   button3.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.veneno);
                   button4.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.espejo);
                   button5.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.duendesconlanzas);
                   button6.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.magodehielo);
                   button7.setImageBitmap(bmp);
                   break;
               case 5:
                   bmp = BitmapFactory.decodeResource(getResources(), R.drawable.duendes);
                   button.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.sabueso);
                   button1.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.canon);
                   button2.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.torreinfernal);
                   button3.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.princesa);
                   button4.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.minero);
                   button5.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.monta);
                   button6.setImageBitmap(bmp);

                   bmp= BitmapFactory.decodeResource(getResources(),R.drawable.principeoscuro);
                   button7.setImageBitmap(bmp);
                   Resources res = getActivity().getResources();
                   final String strings[] = res.getStringArray(R.array.pregunta);
                   final String strings1[] = res.getStringArray(R.array.respuesta);
                   pregunta.setText(strings[0]);

                   buttonpregunta.setOnClickListener(new View.OnClickListener() {

                       public int l=1;
                       public int l2=0;
                       public boolean terminar=false;
                       public int aciertos=0;
                       public int fallos=0;

                       public void onClick(View v) {
                           if (l<11){
                               editText.getText().toString();
                               if (editText.getText().toString().equalsIgnoreCase(strings1[l2])){
                                   Toast toast = Toast.makeText(getActivity(), "Has acertado", Toast.LENGTH_SHORT);
                                   toast.show();
                                   aciertos++;
                               }
                               else{
                                   Toast toast = Toast.makeText(getActivity(), "Has fallado", Toast.LENGTH_SHORT);
                                   toast.show();
                                   fallos++;
                               }
                           }

                           if(terminar!=true){
                               pregunta.setText(strings[l]);
                               l++;
                               l2++;
                               if (l==10){
                                   terminar=true;
                               }
                           }
                           else{
                               Toast toast = Toast.makeText(getActivity(), "Aciertos: "+aciertos+" Fallos: "+fallos, Toast.LENGTH_SHORT);
                               toast.show();
                           }

                       }
                   });
                   break;


           }
            return rootView;
        }

        @Override
        public void onClick(View v) {
            int i = getArguments().getInt(ARG_Arenas_NUMBER);
            Bundle b = new Bundle();
            switch (i){
                case 0:
                    switch (v.getId()) {
                        case R.id.imageButton14:
                            b.putString("Nom","Duendes con lanza");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","52");
                            b.putString("Velocidad","Muy Alta");
                            b.putString("Daño","24");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Duende_con_lanza");
                            break;
                        case R.id.imageButton13:
                            b.putString("Nom","Duendes");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","80");
                            b.putString("Velocidad","Muy Alta");
                            b.putString("Daño","50");
                            b.putString("Link","http://clashroyale.wikia.com/wiki/Goblins");
                            break;
                        case R.id.imageButton12:
                            b.putString("Nom","Choza de duendes");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","700");
                            b.putString("Velocidad","Estructura");
                            b.putString("Daño","29");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Choza_de_duendes");
                            break;
                        case R.id.imageButton11:
                            b.putString("Nom","Valquiria");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","880");
                            b.putString("Velocidad","Media");
                            b.putString("Daño","120");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Valquiria");
                            break;
                        case R.id.imageButton10:
                            b.putString("Nom","Rayo");
                            b.putString("Calidad","Èpica");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","650");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Rayo");
                            break;
                        case R.id.imageButton9:
                            b.putString("Nom","Duende Con Lanzas");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","186");
                            b.putString("Velocidad","Muy Alta");
                            b.putString("Daño","116");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Barril_de_duendes");
                            break;
                    }
                    break;
                case 1:
                    switch (v.getId()) {
                        case R.id.imageButton14:
                            b.putString("Nom","Esqueletos");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","32");
                            b.putString("Velocidad","Alta");
                            b.putString("Daño","32");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Esqueletos");
                            break;
                        case R.id.imageButton13:
                            b.putString("Nom","Esbirros");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","90");
                            b.putString("Velocidad","Alta");
                            b.putString("Daño","40");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Esbirros");
                            break;
                        case R.id.imageButton12:
                            b.putString("Nom","Lapida");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","240");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","Nula");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/L%C3%A1pida");
                            break;
                        case R.id.imageButton11:
                            b.putString("Nom","Torre Bombardera");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","950");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","100");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Torre_bombardera");
                            break;
                        case R.id.imageButton10:
                            b.putString("Nom","Esqueleto Gigante");
                            b.putString("Calidad","Èpica");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","2000");
                            b.putString("Velocidad","Media");
                            b.putString("Daño","720");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Esqueleto_gigante");
                            break;
                        case R.id.imageButton9:
                            b.putString("Nom","Globo Bombástico");
                            b.putString("Calidad","Èpica");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","1050");
                            b.putString("Velocidad","Media");
                            b.putString("Daño","600");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Globo_Bomb%C3%A1stico");
                            break;
                    }
                    break;
                case 2:
                    switch (v.getId()) {
                        case R.id.imageButton14:
                            b.putString("Nom","Cañon");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","350");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","60");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Ca%C3%B1%C3%B3n");
                            break;
                        case R.id.imageButton13:
                            b.putString("Nom","Bárbaros");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","300");
                            b.putString("Velocidad","Media");
                            b.putString("Daño","75");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/B%C3%A1rbaros");
                            break;
                        case R.id.imageButton12:
                            b.putString("Nom","Cohete");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","700");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Cohete");
                            break;
                        case R.id.imageButton11:
                            b.putString("Nom","Choza de bárbaros");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","1100");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","Nula");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Choza_de_b%C3%A1rbaros");
                            break;
                        case R.id.imageButton10:
                            b.putString("Nom","Furia");
                            b.putString("Calidad","Épica");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","Nula");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Furia");
                            break;
                        case R.id.imageButton9:
                            b.putString("Nom","Ballesta");
                            b.putString("Calidad","Épica");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","1000");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","20");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Ballesta");
                            break;

                    }
                    break;
                case 3:
                    switch (v.getId()) {
                        case R.id.imageButton14:
                            b.putString("Nom","Tesla");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","450");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","64");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Torre_Tesla");
                            break;
                        case R.id.imageButton13:
                            b.putString("Nom","Horda de esbirros");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","90");
                            b.putString("Velocidad","Alta");
                            b.putString("Daño","40");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Horda_de_Esbirros");
                            break;
                        case R.id.imageButton12:
                            b.putString("Nom","Torre infernal");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","800");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","20-400");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Torre_Infernal");
                            break;
                        case R.id.imageButton11:
                            b.putString("Nom","Montapuercos");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","800");
                            b.putString("Velocidad","Muy Alta");
                            b.putString("Daño","150");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Montapuercos");
                            break;
                        case R.id.imageButton10:
                            b.putString("Nom","Hielo");
                            b.putString("Calidad","Épica");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","Nula");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Hielo");
                            break;
                        case R.id.imageButton9:
                            b.putString("Nom","Pekka");
                            b.putString("Calidad","Épica");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","2600");
                            b.putString("Velocidad","Baja");
                            b.putString("Daño","510");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/P.E.K.K.A");
                            break;
                        case R.id.imageButton8:
                            b.putString("Nom","Dragon Infernal");
                            b.putString("Calidad","Legendaria");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","950");
                            b.putString("Velocidad","Media");
                            b.putString("Daño","30-350");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Drag%C3%B3n_Infernal");
                            break;
                        case R.id.imageButton7:
                            b.putString("Nom","Sabueso de lava");
                            b.putString("Calidad","Legendaria");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","3000");
                            b.putString("Velocidad","Baja");
                            b.putString("Daño","45");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Sabueso_de_lava");
                            break;
                    }
                    break;
                case 4:
                    switch (v.getId()) {
                        case R.id.imageButton14:
                            b.putString("Nom","Descarga");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","80");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Descarga");
                            break;
                        case R.id.imageButton13:
                            b.putString("Nom","Espíritus de fuego");
                            b.putString("Calidad","Común");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","43");
                            b.putString("Velocidad","Muy Alta");
                            b.putString("Daño","80");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Esp%C3%ADritus_de_fuego");
                            break;
                        case R.id.imageButton12:
                            b.putString("Nom","Horno");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","600");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","Nula");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Horno");
                            break;
                        case R.id.imageButton11:
                            b.putString("Nom","Mago");
                            b.putString("Calidad","Especial");
                            b.putString("Tipo","Estructura");
                            b.putString("Vida","340");
                            b.putString("Velocidad","Media");
                            b.putString("Daño","130");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Mago");
                            break;
                        case R.id.imageButton10:
                            b.putString("Nom","Veneno");
                            b.putString("Calidad","Épica");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","43");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Veneno");
                            break;
                        case R.id.imageButton9:
                            b.putString("Nom","Espejo");
                            b.putString("Calidad","Épica");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","Nula");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Espejo");
                            break;
                        case R.id.imageButton8:
                            b.putString("Nom","Cementerio");
                            b.putString("Calidad","Legendaria");
                            b.putString("Tipo","Hechizo");
                            b.putString("Vida","Nula");
                            b.putString("Velocidad","Nula");
                            b.putString("Daño","Nula");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Cementerio");
                            break;
                        case R.id.imageButton7:
                            b.putString("Nom","Mago de hielo");
                            b.putString("Calidad","Legendaria");
                            b.putString("Tipo","Tropa");
                            b.putString("Vida","665");
                            b.putString("Velocidad","Media");
                            b.putString("Daño","63");
                            b.putString("Link","http://es.clash-royale.wikia.com/wiki/Mago_de_hielo");
                            break;
                    }
                    break;

            }
            Intent intencion = new Intent(getActivity(),SegundaActividad.class);
            intencion.putExtras(b);

            startActivity(intencion);
        }
    }
}