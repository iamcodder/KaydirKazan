package com.patronusstudio.kaydirkazan.Mode

import com.patronusstudio.kaydirkazan.Model.soruModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.patronusstudio.kaydirkazan.Constant.FirebaseKey
import com.patronusstudio.kaydirkazan.Contract.GameOverContract
import com.patronusstudio.kaydirkazan.Contract.HomeContract
import com.patronusstudio.kaydirkazan.Contract.SoruContract
import com.patronusstudio.kaydirkazan.Model.userModel
import java.util.*



class IFirebaseDatabase {

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()

    val firebaseDatabase_kullanicilar: DatabaseReference =
        firebaseDatabase.reference.child(FirebaseKey.OYUN).child(FirebaseKey.KULLANICILAR)

    val firebaseDatabase_sorular: DatabaseReference =
        firebaseDatabase.reference.child(FirebaseKey.OYUN).child(FirebaseKey.SORULAR).child(FirebaseKey.TAHMIN_ET)

    val kullanicinin_uuidsi = firebaseAuth.currentUser?.uid ?: (Math.random() * 10000).toString()


    fun dbyeProfiliYaz(sonuc:HomeContract.FirebaseSonucu) {

        if(firebaseAuth.currentUser!=null){
            val uuid: String = firebaseAuth.currentUser?.uid ?: (Math.random() * 10000).toString()

            firebaseDatabase.reference.child(FirebaseKey.OYUN).child(FirebaseKey.KULLANICILAR).child(uuid)
                .child(FirebaseKey.EMAIL).setValue(firebaseAuth.currentUser?.email.toString())
            firebaseDatabase.reference.child(FirebaseKey.OYUN).child(FirebaseKey.KULLANICILAR).child(uuid)
                .child(FirebaseKey.YUKSEK_PUAN).setValue("0")
            firebaseDatabase.reference.child(FirebaseKey.OYUN).child(FirebaseKey.KULLANICILAR).child(uuid)
                .child(FirebaseKey.CEVAPLANAN_SORU_SAYISI).setValue("0")
            firebaseDatabase.reference.child(FirebaseKey.OYUN).child(FirebaseKey.KULLANICILAR).child(uuid)
                .child(FirebaseKey.UUID).setValue(uuid)

            sonuc.kullanici_verileri_dbye_yazildi("Tebrikler.Kullanıcı oluşturuldu.")
        }
    }

    fun kullaniciVerileriniCek(sonuc:HomeContract.FirebaseSonucu) {

        var kullanici_bulundu_mu: Boolean = false
        val siralanacak_liste: ArrayList<Int> = ArrayList()
        var kullanicinin_puani:Int=0


        val postTListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val newUser: userModel? = ds.getValue(userModel::class.java)

                    if (newUser != null) {
                        siralanacak_liste.add(newUser.yuksekPuan.toInt())

                        if (newUser.uuid == firebaseAuth.uid) {
                            sonuc.kullanici_cekildi(newUser)
                            kullanicinin_puani=newUser.yuksekPuan.toInt()
                            kullanici_bulundu_mu = true
                        }
                    }
                }
                if(kullanici_bulundu_mu){
                    kullanicilari_sirala(siralanacak_liste, kullanicinin_puani,sonuc)
                }

                if (!kullanici_bulundu_mu) {
                   sonuc.kullanici_cekilemedi_dbde_yok()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                sonuc.kullanici_cekilemedi(p0.message)
            }


        }

        firebaseDatabase_kullanicilar.addValueEventListener(postTListener)
    }

    fun kullanicilari_sirala(liste: ArrayList<Int>, kullanicinin_puani: Int, sonuc:HomeContract.FirebaseSonucu) {

        var siralama: Int = 0

        for (i in 0 until liste.size) {

            if (liste[i] >= kullanicinin_puani) {
                siralama++
            }
        }
        sonuc.siralamaCekildi(siralama,liste.size)
        liste.clear()
    }

    fun sorulari_cek(sonuc:SoruContract.FirebaseSonuc) {

        try {
            val soruListesi: ArrayList<soruModel> = ArrayList()

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (ds in dataSnapshot.children) {

                        val soruNesnesi = ds.getValue(soruModel::class.java)

                        if (soruNesnesi != null)
                            soruListesi.add(soruNesnesi)

                    }
                    soruListesiRandom(soruListesi,sonuc)

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }
            firebaseDatabase_sorular.addValueEventListener(postListener)
        } catch (e: Exception) {
        }

    }

    fun soruListesiRandom(soruListesi: ArrayList<soruModel>, sonuc: SoruContract.FirebaseSonuc) {

        val dondurulecekListe: ArrayList<soruModel> = ArrayList()
        dondurulecekListe.clear()

        var soruListesi_child_sayisi: Int = (soruListesi.size) - 1

        for (i in 0..soruListesi_child_sayisi) {

            val randomSayi = (0..soruListesi_child_sayisi).random()

            dondurulecekListe.add(soruListesi[randomSayi])
            soruListesi.removeAt(randomSayi)

            soruListesi_child_sayisi = (soruListesi.size) - 1

        }
        sonuc.soruListesiniDondur(dondurulecekListe)

    }

    fun rekoruYaz(dogruSayisi: Int) {

        firebaseDatabase_kullanicilar.child(kullanicinin_uuidsi).child(FirebaseKey.YUKSEK_PUAN).setValue("$dogruSayisi")
    }

    fun cevaplananSoruSayisiniArttir(soruSayisi: Int) {

        firebaseDatabase_kullanicilar.child(kullanicinin_uuidsi).child(FirebaseKey.CEVAPLANAN_SORU_SAYISI)
            .setValue("$soruSayisi")
    }

    fun soruHataliysa(soru: String,sonuc:GameOverContract.FirebaseSonucu) {

        val random:Random= Random()
        val randomSayi: String = random.nextInt(1000).toString()


        firebaseDatabase.reference.child(FirebaseKey.OYUN).child(FirebaseKey.HATALI_SORU).child(kullanicinin_uuidsi).child(randomSayi).setValue(soru)

        sonuc.gelistiriciye_haber("Geliştiriciye haber verildi.Teşekkürler")


    }

}