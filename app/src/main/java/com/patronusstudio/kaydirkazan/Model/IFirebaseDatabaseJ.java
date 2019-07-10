package com.patronusstudio.kaydirkazan.Model;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.patronusstudio.kaydirkazan.Constant.FirebaseKeyJ;
import com.patronusstudio.kaydirkazan.Contract.FirebaseContractJ;
import com.patronusstudio.kaydirkazan.Presenter.GameOverPresenterJ;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class IFirebaseDatabaseJ {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference firebaseDatabase_kullanicilar;
    private DatabaseReference firebaseDatabase_sorular;
    private String kullanicinin_uuidsi;

    public IFirebaseDatabaseJ(){
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase_kullanicilar=firebaseDatabase.getReference().child(FirebaseKeyJ.OYUN).child(FirebaseKeyJ.KULLANICILAR);
        firebaseDatabase_sorular=firebaseDatabase.getReference().child(FirebaseKeyJ.OYUN).child(FirebaseKeyJ.SORULAR);

        if(firebaseAuth.getCurrentUser()!=null){
            kullanicinin_uuidsi=firebaseAuth.getCurrentUser().getUid();
        }
    }

    public void dbyeProfiliYaz(FirebaseContractJ.KullaniciIslemleri sonuc){

        if(firebaseAuth.getCurrentUser()!=null){
            firebaseDatabase.getReference().child(FirebaseKeyJ.OYUN).child(FirebaseKeyJ.KULLANICILAR).child(kullanicinin_uuidsi)
                    .child(FirebaseKeyJ.EMAIL).setValue(firebaseAuth.getCurrentUser().getEmail());
            firebaseDatabase.getReference().child(FirebaseKeyJ.OYUN).child(FirebaseKeyJ.KULLANICILAR).child(kullanicinin_uuidsi)
                    .child(FirebaseKeyJ.YUKSEK_PUAN).setValue("0");
            firebaseDatabase.getReference().child(FirebaseKeyJ.OYUN).child(FirebaseKeyJ.KULLANICILAR).child(kullanicinin_uuidsi)
                    .child(FirebaseKeyJ.CEVAPLANAN_SORU_SAYISI).setValue("0");
            firebaseDatabase.getReference().child(FirebaseKeyJ.OYUN).child(FirebaseKeyJ.KULLANICILAR).child(kullanicinin_uuidsi)
                    .child(FirebaseKeyJ.UUID).setValue(kullanicinin_uuidsi);

            sonuc.kullanici_verileri_dbye_yazildi("Tebrikler.Kullanıcı oluşturuldu.");

        }
    }

    public void kullaniciVerileriniCek(final FirebaseContractJ.KullaniciIslemleri sonuc, final FirebaseContractJ.KullaniciIslemleri.siralamasi siralamasi){

        final Boolean[] kullanici_bulundu_mu = {false};
        final ArrayList<Integer> siralanacak_liste=new ArrayList<>();
        final int[] kullanicinin_puani = {0};

        ValueEventListener postListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    userModelJ newUser=ds.getValue(userModelJ.class);

                    if(newUser!=null){
                        siralanacak_liste.add(Integer.parseInt(newUser.getYuksekPuan()));

                        if (newUser.getUuid().equals(firebaseAuth.getUid())){
                            sonuc.kullanici_cekildi(newUser);
                            kullanicinin_puani[0] =Integer.parseInt(newUser.getYuksekPuan());
                            kullanici_bulundu_mu[0] =true;
                        }

                    }

                }

                if (kullanici_bulundu_mu[0]){
                    kullanicilari_sirala(siralanacak_liste, kullanicinin_puani[0],siralamasi);
                }

                if(!kullanici_bulundu_mu[0]){
                    sonuc.kullanici_cekilemedi_dbde_yok();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                sonuc.kullanici_cekilemedi(databaseError.getMessage());
            }
        };

        firebaseDatabase_kullanicilar.addValueEventListener(postListener);
    }



    public void kullanicilariSirala(final FirebaseContractJ.KullaniciIslemleri.siralamasi siralamasi){

        final ArrayList<Integer> siralanacak_liste= new ArrayList<>();
        final int[] kullanicinin_puani = {0};

        ValueEventListener postListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userModelJ newUser= ds.getValue(userModelJ.class);

                    if (newUser != null) {
                        siralanacak_liste.add(Integer.parseInt(newUser.getYuksekPuan()));
                        if (newUser.getUuid().equals(firebaseAuth.getUid())) {
                            kullanicinin_puani[0] =Integer.parseInt(newUser.getYuksekPuan());
                        }

                    }
                }
                if(!siralanacak_liste.isEmpty()){
                    kullanicilari_sirala(siralanacak_liste, kullanicinin_puani[0],siralamasi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        firebaseDatabase_kullanicilar.addValueEventListener(postListener);
    }



    public void kullanicilari_sirala(ArrayList<Integer> liste, int kullanicinin_puani, FirebaseContractJ.KullaniciIslemleri.siralamasi sonuc) {

        int siralama = 0;

        for (int i=0;i<liste.size();i++) {

            if (liste.get(i) >= kullanicinin_puani) {
                siralama++;
            }
        }
        sonuc.siralamaCekildi(siralama,liste.size());
        liste.clear();
    }

    public void sorulari_cek(final FirebaseContractJ.SoruListesi sonuc) {

        Random random= new Random();
        int random_sayi=random.nextInt(7);

        switch (random_sayi){
            case 0 :firebaseDatabase_sorular=firebaseDatabase_sorular.child(FirebaseKeyJ.TAHMIN_ET);
            case 1 :firebaseDatabase_sorular=firebaseDatabase_sorular.child(FirebaseKeyJ.TAHMIN_ET);
            case 2 :firebaseDatabase_sorular=firebaseDatabase_sorular.child(FirebaseKeyJ.TAHMIN_ET);
            case 3 :firebaseDatabase_sorular=firebaseDatabase_sorular.child(FirebaseKeyJ.REKORLAR);
            case 4 :firebaseDatabase_sorular=firebaseDatabase_sorular.child(FirebaseKeyJ.GENEL_KULTUR);
            case 5 :firebaseDatabase_sorular=firebaseDatabase_sorular.child(FirebaseKeyJ.GENEL_KULTUR);
            case 6 :firebaseDatabase_sorular=firebaseDatabase_sorular.child(FirebaseKeyJ.NE_ZAMAN);
        }

        try {
            final ArrayList<soruModelJ> soruListesi =new ArrayList<>();

            ValueEventListener postListener =new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {

                        soruModelJ soruNesnesi = ds.getValue(soruModelJ.class);

                        if (soruNesnesi != null) {
                            soruListesi.add(soruNesnesi);
                        }

                    }
                    soruListesiRandom(soruListesi,sonuc);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            firebaseDatabase_sorular.addValueEventListener(postListener);
        }
        catch (Exception e) {
        }

    }



    public void soruListesiRandom(ArrayList<soruModelJ> soruListesi,FirebaseContractJ.SoruListesi sonuc) {

        ArrayList<soruModelJ> dondurulecekListe = new ArrayList<>();
        dondurulecekListe.clear();

        int soruListesi_child_sayisi = (soruListesi.size()) - 1;

        Random r=new Random();

        for (int i= 0;i<=soruListesi_child_sayisi;i++) {


            int randomSayi =r.nextInt(soruListesi_child_sayisi);

            dondurulecekListe.add(soruListesi.get(randomSayi));
            soruListesi.remove(randomSayi);

            soruListesi_child_sayisi = (soruListesi.size()) - 1;

        }
        sonuc.soruListesiniDondur(dondurulecekListe);

    }



    public void rekoruYaz(int dogruSayisi) {

        firebaseDatabase_kullanicilar.child(kullanicinin_uuidsi).child(FirebaseKeyJ.YUKSEK_PUAN).setValue(""+dogruSayisi);
    }

    public void cevaplananSoruSayisiniArttir(int soruSayisi) {

        firebaseDatabase_kullanicilar.child(kullanicinin_uuidsi).child(FirebaseKeyJ.CEVAPLANAN_SORU_SAYISI)
                .setValue(""+soruSayisi);
    }

    public void soruHataliysa(String soru, GameOverPresenterJ sonuc) {

        Random random= new Random();
        String randomSayi = String.valueOf(random.nextInt(1000));


        firebaseDatabase.getReference().child(FirebaseKeyJ.OYUN).child(FirebaseKeyJ.HATALI_SORU).child(kullanicinin_uuidsi).child(randomSayi).setValue(soru);

        sonuc.gelistiriciye_haber("Geliştiriciye haber verildi.Teşekkürler");


    }


      public void kullaniciBilgileri(FirebaseContractJ.KullaniciIslemleri.bilgileri sonuc){

        if(firebaseAuth.getCurrentUser()!=null) {
            String displayName=firebaseAuth.getCurrentUser().getDisplayName();
            Long kayitOlmaTarihi= Objects.requireNonNull(firebaseAuth.getCurrentUser().getMetadata()).getCreationTimestamp();
            sonuc.kullaniciSonuclari(displayName,kayitOlmaTarihi);
        }

    }
}

