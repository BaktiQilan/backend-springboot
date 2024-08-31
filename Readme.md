# Soal test maggang Backend engineer dengan Springboot

Berikut adalah soal/pertanyaan yang perlu dijawab oleh peserta maggang

## knowledge base

1. Apa yang anda ketahui tentang Rest API?
2. Apa yang anda ketahui tentang Server side and Client side processing?
3. Apa yang anda ketahui tentang Monolith dan Microservices, berikan contohnya?
4. Apa yang anda ketahui tentang Design pattern inversion of Control serta Dependency Injection?
5. Apa yang anda ketahui tentang Java programming dan Spring framework khususnya spring-boot?

## Design modules

Dalam suatu schenario ada requirement membuat aplikasi e-commerse seperti Tokopedia seperti berikut:

1. Catalog, pelanggan mencari product di toko
   ![catalog](src/main/resources/static/cart.png)
2. Item, bisa melihat detail informasi produk
   ![items](src/main/resources/static/item.png)
3. Cart, pelanggan bisa menambahkan produk yang ingin di beli ke keranjang
   ![cart](src/main/resources/static/cart.png)
4. Setelah di checkout, masuk ke list transaction
   ![list-transaction](src/main/resources/static/list-transaction.png)
5. Kita juga bisa liat detail transactionya
   ![detail-transaction](src/main/resources/static/detail-transaction.png)

Kemudian temen-temen buat design database, module (monolith/microservices) berdasarkan gambar atau schenario tersebut. Serta jelakan mengapa menggunakan design tersebut.

## Praktek

Berdasarkan analisa tersebut, buat project monorepo (pada repository ini) dengan menggunakan framework springboot seperti berikut specifikasinya:

- Database: `PostgreSQL 15`
- JDK version: `Oracle JDK 17 or later`
- Springboot version: `3.0.x`

terkait design system Toko, Barang, Pembelian pada ecommerse tersebut.


## Jawaban ada pada [Jawaban.md](Jawaban.md)
### Backup database ada pada [backup](src/main/resources/templates/db_test_backend_20240831.backup)
### dan script sql untuk contoh data [script.sql](src/main/resources/templates/script.sql)
### Postman Collection [postman](src/main/resources/templates/Backend Spring Boot.postman_collection.json)