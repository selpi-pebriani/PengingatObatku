package com.example.pengingatobat;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    private static final String NAMA_PREFS = "pengingat_prefs";
    private static final String KEY_NAMA = "nama_pengguna";
    private static final String KEY_JENIS = "jenis_obat";

    private final SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getSharedPreferences(NAMA_PREFS, Context.MODE_PRIVATE);
    }

    public void simpanNama(String nama) {
        prefs.edit().putString(KEY_NAMA, nama).apply();
    }

    public String ambilNama() {
        return prefs.getString(KEY_NAMA, "Pengguna");
    }

    public void simpanJenisObat(boolean obat) {
        prefs.edit().putBoolean(KEY_JENIS, obat).apply();
    }

    public boolean ambilJenisObat() {
        return prefs.getBoolean(KEY_JENIS, true);
    }
}