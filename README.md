# Tugas Besar PBO KOM4B2025

<img src="src/assets/sibengkel.png" alt="logo" width="500">

SIBENGKEL adalah aplikasi desktop berbasis desktop Java yang digunakan untuk pengelolaan data servis kendaraan di bengkel.

## Fitur
* Login
* Manajemen data servis kendaraan (mobil & motor)
* Menyimpan dan menampilkan data spare part
* Menyimpan dan menampilkan data customer
* Setiap detail servis dapat menggunakan spare part (opsional)
* Export invoice ke PDF yang berisi:
  * Detail servis
  * Informasi kendaraan
  * Daftar spare part yang digunakan (jika ada)
  * Total biaya dengan format mata uang Rupiah

## Teknologi yang Digunakan
* Java SE 8+
* Swing (untuk GUI)
* iText PDF (versi 5.5 ke atas)
* Flatlaf (untuk library UI)
* JDBC (koneksi database)
* MySQL (atau RDBMS lain)
* Pola desain MVC (Model-View-Controller)

## Struktur Folder
```
src/
├── interfaces/     # Interface untuk logika bisnis: ServiceRecordInterface, SparePartInterface, VehicleInterface, dsb.
├── models/         # Kelas-kelas model: ServiceRecord, SparePart, Vehicle, dsb.
├── utils/          # Kelas utilitas: Formatter, InvoiceGenerator, Database, dsb.
├── services/       # Logika bisnis: SparePartService, VehicleService, dsb.
├── views/          # Form dan panel GUI
└── Main.java       # Titik masuk aplikasi
```

## Cara Menjalankan
1. Clone repositori:

```bash
git clone https://github.com/rakaiskandar/bengkel-desktop.git
```

2. Buka proyek menggunakan IDE Java seperti NetBeans atau IntelliJ

3. Atur koneksi database Anda di file Database.java

5. Jalankan Main.java atau gunakan tampilan GUI yang telah disediakan

6. Login dengan username default
```bash
username = admin
password = ADMIN1#3
```

## Dependensi
Pastikan library berikut tersedia di classpath:
* itextpdf-5.x.jar
* mysql-connector-java.jar
* flatlaf-3.x.jar

## Catatan
* Jika tidak ada spare part yang digunakan dalam servis, tabel spare part tidak akan ditampilkan di PDF.
* Format harga dan biaya menggunakan utils.Formatter yang menyesuaikan ke format mata uang Indonesia (Rp).

## Credit
Dosen Pengampu: Erlangga, S.Kom., M.T. \
Kelompok 5: 
* Ellyazar Swastiko (2309749)
* Ma'rifatu Ambiya (2300822)
* Muhammad Rivalby (2307068)
* Raka Iskandar (2306068)
* Sandy Achmad Nugraha (2309844)
