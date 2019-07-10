package com.patronusstudio.kaydirkazan.Contract;

import com.patronusstudio.kaydirkazan.Model.soruModelJ;
import com.patronusstudio.kaydirkazan.Model.userModelJ;

import java.util.ArrayList;

public interface FirebaseContractJ {

    interface KullaniciIslemleri{
        void kullanici_cekildi(userModelJ kullanici);
        void kullanici_cekilemedi(String mesaj);
        void kullanici_cekilemedi_dbde_yok();
        void kullanici_verileri_dbye_yazildi(String mesaj);

        interface siralamasi{
            void siralamaCekildi(int seninSiran,int toplamSira);
        }
        interface bilgileri{
            void kullaniciSonuclari(String displayName,Long kayitOlmaTarihi);
        }
    }


    interface Sorun{
        void gelistiriciye_haber(String message);
    }

    interface SoruListesi{
        void soruListesiniDondur(ArrayList<soruModelJ> soruListesi);
    }


}
