package com.romabejar.garbelleratostenes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Integer> prime_numbers = new LinkedList<Integer>() {{
        add(2);
        add(3);
        add(5);
        add(7);
        add(11);
        add(13);
        add(17);
        add(19);
        add(23);
        add(29);
        add(31);
        add(37);
        add(41);
        add(43);
        add(47);
        add(53);
        add(59);
        add(61);
        add(67);
        add(71);
        add(73);
        add(79);
        add(83);
        add(89);
        add(97);
    }};

    boolean[] valids = new boolean[101];

    enum Modes {
        TROBAR_PRIMER,
        TATXAR_MULTIPLES
    }

    Modes mode;
    int primer_trobat;
    int index_seguent_primer;
    int seguent_multiple;
    int multiples_restants;
    int duration = 2000;
    TextView text;
    String informacio;
    boolean already_shown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mode = Modes.TROBAR_PRIMER;
        Button button001 = (Button)findViewById(R.id.button001);
        button001.setEnabled(false);
        index_seguent_primer = 0;
        Arrays.fill(valids, Boolean.TRUE);
        valids[0] = Boolean.FALSE;
        valids[1] = Boolean.FALSE;
        multiples_restants = -1;
        text = (TextView)findViewById(R.id.textView_primers_trobats);
        informacio = "Primers trobats: ";
        text.setText(informacio);
    }

    int count_valid_multiples(int n) {
        int count = 0;
        for (int i = 2 * n; i <= 100; i += n) {
            if (valids[i]) {
                count++;
            }
        }
        return count;
    }

    public void onFinalitzarClick(View view) {
        MainActivity.this.finish();
        System.exit(0);
    }

    public void onNumberClick(View view) {
        Button number = (Button)view;
        int n = Integer.parseInt(number.getText().toString());
        String s = "S'ha apretat el " + number.getText().toString();
        Log.d("", "Mode = " + mode.toString());

        switch (mode) {
            case TROBAR_PRIMER:
                if (n != prime_numbers.get(index_seguent_primer)) {
                    Snackbar.make(findViewById(android.R.id.content), "Aquest no ??s el seg??ent nombre primer a trobar", duration ).show();
                } else {
                    mode = Modes.TATXAR_MULTIPLES;
                    already_shown = false;
                    primer_trobat = prime_numbers.get(index_seguent_primer);
                    seguent_multiple = primer_trobat + primer_trobat;
                    number.setBackgroundColor(getResources().getColor(R.color.Green));
                    number.setEnabled(false);
                    multiples_restants = count_valid_multiples(primer_trobat);
                    if (multiples_restants != 0) {
                        Snackbar.make(findViewById(android.R.id.content), "Molt b??! El " + String.valueOf(n) + " ??s el seg??ent nombre primer, ara elimina tots els seus m??ltiples", duration ).show();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Molt b??! El " + String.valueOf(n) + " ??s el seg??ent nombre primer, per?? ja has eliminat tots els seus m??ltiples. Busca el seg??ent primer.", duration ).show();
                        already_shown = true;
                    }
                    informacio += String.valueOf(n);
                    if (index_seguent_primer != prime_numbers.size()-1) {
                        informacio += ", ";
                    } else {
                        informacio += ".";
                    }
                }
                break;
            case TATXAR_MULTIPLES:
                if (n % primer_trobat == 0) {
                    valids[n] = false;
                    number.setEnabled(false);
                    number.setBackgroundColor(getResources().getColor(R.color.Red));
                    multiples_restants--;
                    Snackbar.make(findViewById(android.R.id.content), "Et queden " + String.valueOf(multiples_restants) + " per eliminar", duration ).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "El " + String.valueOf(n) + " no ??s un m??ltiple de " + String.valueOf(primer_trobat), duration ).show();
                }
                break;
        }

        if (multiples_restants == 0 && mode != Modes.TROBAR_PRIMER) {
            mode = Modes.TROBAR_PRIMER;
            multiples_restants = -1;
            if (!already_shown) {
                Snackbar.make(findViewById(android.R.id.content), "Molt b??! Ja has eliminat tots els m??ltiples de " + String.valueOf(primer_trobat), duration).show();
            }
            ++index_seguent_primer;
        }

        if (index_seguent_primer == prime_numbers.size()) {
            Snackbar.make(findViewById(android.R.id.content), "Has trobat tots els nombres primers d'aquesta llista!", duration).show();
        }

        text.setText(informacio);
    }
}