# 💊 Pengingat Obat

Aplikasi Android sederhana untuk membantu mengingat jadwal minum **obat** dan **vitamin** setiap hari. Data pengingat disimpan di perangkat memakai **SharedPreferences**, jadi tetap tersimpan walaupun aplikasi ditutup.

> Studi kasus SharedPreferences (Tugas Pemrograman Mobile, UMKO) - Selpi Pebriani, 2459201053, 5A.

---

## ✨ Fitur

- 📋 **Daftar pengingat** obat dan vitamin lengkap dengan jam minum
- 💊🍊 **Ikon pembeda**: kapsul untuk obat, jeruk untuk vitamin
- ✅ **Penanda status**: tampil *Sudah diminum* (hijau, nama dicoret) atau *Belum diminum* (pink)
- 💾 **Data tersimpan otomatis** dengan SharedPreferences
- 🎨 Tampilan bersih dengan lingkaran warna sebagai indikator status

---

## 🧩 Struktur Kode

| File | Fungsi |
|------|--------|
| `MainActivity` | Layar utama yang menampilkan daftar pengingat |
| `Pengingat` | Model data (nama, jenis obat/vitamin, jam, status minum) |
| `PengingatAdapter` | Adapter untuk menampilkan setiap item pengingat di ListView |
| `PrefsManager` | Mengelola penyimpanan dan pembacaan data lewat SharedPreferences |

---

## 💾 Pembagian Data

**SharedPreferences (`PrefsManager`)** menyimpan:
- Daftar pengingat (nama, jenis, jam)
- Status sudah/belum diminum untuk setiap pengingat

---

## 🛠️ Teknologi

- **Bahasa:** Java
- **IDE:** Android Studio
- **Penyimpanan lokal:** SharedPreferences
- **Komponen UI:** ListView, ArrayAdapter, Custom Layout XML

---

## 🚀 Cara Menjalankan

1. Clone repositori ini:
   ```bash
   git clone https://github.com/selpi-pebriani/pengingat-obat-android.git
   ```
2. Buka folder proyek dengan **Android Studio**.
3. Tunggu proses **Gradle Sync** selesai.
4. Hubungkan perangkat Android atau jalankan emulator.
5. Klik tombol **Run ▶️**.

---

## 👩‍💻 Pembuat

| | |
|---|---|
| **Nama** | Selpi Pebriani |
| **NIM** | 2459201053 |
| **Kelas** | 5A |
| **Mata Kuliah** | Pemrograman Mobile |
| **Kampus** | UNIVERSITAS MUHAMMADIYAH KOTABUMI |

---

⭐ Kalau proyek ini bermanfaat, jangan lupa beri bintang!
