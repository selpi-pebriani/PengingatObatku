package com.example.pengingatobat;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNama;
    private MaterialButton btnPilihJam;
    private MaterialButtonToggleGroup toggleJenis;
    private TextView tvSapaan, tvUbahNama, tvInfo, tvProgress, tvSudah, tvBelum, tvKosong;
    private ListView lvPengingat;

    private final ArrayList<Pengingat> daftarPengingat = new ArrayList<>();
    private PengingatAdapter adapter;
    private PrefsManager prefsManager;

    private int jamDipilih = 8;
    private int menitDipilih = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefsManager = new PrefsManager(this);

        tvSapaan = findViewById(R.id.tvSapaan);
        tvUbahNama = findViewById(R.id.tvUbahNama);
        tvInfo = findViewById(R.id.tvInfo);
        tvProgress = findViewById(R.id.tvProgress);
        tvSudah = findViewById(R.id.tvSudah);
        tvBelum = findViewById(R.id.tvBelum);
        tvKosong = findViewById(R.id.tvKosong);
        etNama = findViewById(R.id.etNama);
        btnPilihJam = findViewById(R.id.btnPilihJam);
        toggleJenis = findViewById(R.id.toggleJenis);
        lvPengingat = findViewById(R.id.lvPengingat);
        MaterialButton btnSimpan = findViewById(R.id.btnSimpan);

        adapter = new PengingatAdapter(this, daftarPengingat);
        lvPengingat.setAdapter(adapter);
        lvPengingat.setEmptyView(tvKosong);

        // Ubah nama pengguna
        tvUbahNama.setOnClickListener(v -> tampilDialogNama());
        tvSapaan.setOnClickListener(v -> tampilDialogNama());

        // Penjelasan aplikasi
        tvInfo.setOnClickListener(v -> tampilInfo());

        // Pilih jam minum obat
        btnPilihJam.setOnClickListener(v ->
                new TimePickerDialog(this, (view, jam, menit) -> {
                    jamDipilih = jam;
                    menitDipilih = menit;
                    btnPilihJam.setText("⏰ Jam: " + formatJam(jam, menit));
                }, jamDipilih, menitDipilih, true).show());

        // Simpan pilihan jenis (Obat/Vitamin) setiap kali berubah
        toggleJenis.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                prefsManager.simpanJenisObat(checkedId == R.id.btnObat);
            }
        });

        btnSimpan.setOnClickListener(v -> tambahPengingat());

        // Ketuk item: tandai sudah / belum diminum
        lvPengingat.setOnItemClickListener((parent, view, position, id) -> {
            Pengingat p = daftarPengingat.get(position);
            p.setSudahMinum(!p.isSudahMinum());
            refreshTampilan();
        });

        // Tekan lama: hapus jadwal
        lvPengingat.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Hapus jadwal?")
                    .setMessage(daftarPengingat.get(position).getNama())
                    .setPositiveButton("Hapus", (dialog, which) -> {
                        daftarPengingat.remove(position);
                        refreshTampilan();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
            return true;
        });

        refreshTampilan();
    }

    // Pengaturan dibaca dari SharedPreferences setiap layar tampil
    @Override
    protected void onResume() {
        super.onResume();
        tvSapaan.setText("Halo, " + prefsManager.ambilNama());
        toggleJenis.check(prefsManager.ambilJenisObat()
                ? R.id.btnObat
                : R.id.btnVitamin);
    }

    private String formatJam(int jam, int menit) {
        return String.format(Locale.getDefault(), "%02d:%02d", jam, menit);
    }

    private void tampilInfo() {
        String pesan =
                "1. Masalah yang diselesaikan\n"
                        + "Banyak orang lupa minum obat atau vitamin tepat waktu. "
                        + "Aplikasi ini mencatat jadwal dan menandai mana yang sudah dan belum diminum.\n\n"

                        + "2. Disimpan di SharedPreferences\n"
                        + "• Nama pengguna (tombol Ubah nama)\n"
                        + "• Jenis terakhir yang dipilih (Obat/Vitamin)\n"
                        + "Alasan: berupa pengaturan kecil berbentuk key-value, tidak perlu dicari atau difilter.\n\n"

                        + "3. Tidak disimpan permanen\n"
                        + "Daftar jadwal hanya di memori (ArrayList), jadi hilang saat aplikasi ditutup. "
                        + "Idealnya disimpan di database (Room/SQLite) karena jumlahnya terus bertambah.\n\n"

                        + "4. Cara kerja\n"
                        + "• PrefsManager membungkus SharedPreferences\n"
                        + "• Data ditulis dengan apply() (di latar belakang)\n"
                        + "• Data dibaca di onResume() setiap layar tampil\n\n"

                        + "5. Bukti\n"
                        + "Setelah force stop, nama dan pilihan jenis tetap ada, sedangkan daftar jadwal kosong kembali.";

        new AlertDialog.Builder(this)
                .setTitle("Tentang Aplikasi")
                .setMessage(pesan)
                .setPositiveButton("Mengerti", null)
                .show();
    }

    private void tampilDialogNama() {
        final EditText input = new EditText(this);
        input.setHint("Masukkan nama pengguna");
        input.setText(prefsManager.ambilNama());
        input.setSelection(input.getText().length());

        new AlertDialog.Builder(this)
                .setTitle("Nama pengguna")
                .setView(input)
                .setPositiveButton("Simpan", (dialog, which) -> {
                    String nama = input.getText().toString().trim();
                    if (!nama.isEmpty()) {
                        prefsManager.simpanNama(nama);
                        tvSapaan.setText("Halo, " + nama);
                        Toast.makeText(this,
                                "Nama tersimpan di SharedPreferences",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void tambahPengingat() {
        String nama = etNama.getText().toString().trim();

        if (nama.isEmpty()) {
            Toast.makeText(this, "Nama obat/vitamin harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean obat = toggleJenis.getCheckedButtonId() == R.id.btnObat;
        daftarPengingat.add(new Pengingat(nama, formatJam(jamDipilih, menitDipilih), obat));

        // Urutkan berdasarkan jam
        Collections.sort(daftarPengingat, (a, b) -> a.getJam().compareTo(b.getJam()));

        etNama.setText("");
        refreshTampilan();
    }

    private void refreshTampilan() {
        int sudah = 0;
        for (Pengingat p : daftarPengingat) {
            if (p.isSudahMinum()) {
                sudah++;
            }
        }
        int total = daftarPengingat.size();

        tvProgress.setText(sudah + " / " + total);
        tvSudah.setText(String.valueOf(sudah));
        tvBelum.setText(String.valueOf(total - sudah));

        adapter.notifyDataSetChanged();
    }
}