package com.example.pengingatobat;

public class Pengingat {
    private final String nama;
    private final String jam;
    private final boolean obat; // true = obat, false = vitamin
    private boolean sudahMinum;

    public Pengingat(String nama, String jam, boolean obat) {
        this.nama = nama;
        this.jam = jam;
        this.obat = obat;
        this.sudahMinum = false;
    }

    public String getNama() {
        return nama;
    }

    public String getJam() {
        return jam;
    }

    public boolean isObat() {
        return obat;
    }

    public boolean isSudahMinum() {
        return sudahMinum;
    }

    public void setSudahMinum(boolean sudahMinum) {
        this.sudahMinum = sudahMinum;
    }
}