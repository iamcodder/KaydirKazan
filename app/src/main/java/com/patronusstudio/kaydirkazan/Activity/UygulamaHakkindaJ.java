package com.patronusstudio.kaydirkazan.Activity;

import android.graphics.drawable.AnimationDrawable;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.patronusstudio.kaydirkazan.Contract.UygulamHakkindaContractJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;
import com.patronusstudio.kaydirkazan.Model.userModelJ;
import com.patronusstudio.kaydirkazan.Presenter.UygulamaHakkindaPresenterJ;
import com.patronusstudio.kaydirkazan.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UygulamaHakkindaJ extends AppCompatActivity implements UygulamHakkindaContractJ.View {

    private UygulamaHakkindaPresenterJ presenter;
    private Bundle bundle;
    private userModelJ kullanici;
    private AnimationDrawable animation_drawable;
    private ConstraintLayout constraintLayout;

    private TextView txt_hosgeldin,txt_tarih,txt_cevaplanan_soru_sayisi,txt_kullanici_skoru,txt_siralama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uygulama_hakkinda);

        presenter= new UygulamaHakkindaPresenterJ(new IFirebaseDatabaseJ());
        presenter.setView(this);
        presenter.onCreated();

    }

    @Override
    public void bindView() {

        bundle=getIntent().getExtras();

        if(bundle!=null) {
            kullanici = bundle.getParcelable("kullanıcı bilgisi");
        }

        constraintLayout=findViewById(R.id.scroolview);

        txt_hosgeldin=findViewById(R.id.activity_uygulama_hakkinda_hosgeldin_txt);
        txt_tarih=findViewById(R.id.activity_uygulama_hakkinda_tarih_txt);
        txt_cevaplanan_soru_sayisi=findViewById(R.id.activity_uygulama_hakkinda_cevaplanan_soru_sayisi_txt);
        txt_kullanici_skoru=findViewById(R.id.activity_uygulama_hakkinda_kullanici_skoru_txt);
        txt_siralama=findViewById(R.id.activity_uygulama_hakkinda_siralama);

        animation_drawable= (AnimationDrawable) constraintLayout.getBackground();
        animation_drawable.setEnterFadeDuration(1000);
        animation_drawable.setExitFadeDuration(2000);
        animation_drawable.start();
    }

    @Override
    public void yazilariGoster(String displayName, Long kayitOlmaTarihi) {

        String dateString= new SimpleDateFormat("dd/MM/yyyy").format(new Date(kayitOlmaTarihi));
        txt_hosgeldin.setText(getString(R.string.hosgeldiniz)+" "+displayName);
        txt_tarih.setText("Kayıt Olma Tarihiniz : "+dateString);
        txt_cevaplanan_soru_sayisi.setText("Cevapladığınız Toplam Soru Sayısı : "+kullanici.getCevaplananSoruSayisi());
        txt_kullanici_skoru.setText("Rekorunuz : "+kullanici.getYuksekPuan());


    }

    @Override
    public void siralamaGoster(int seninSiran, int toplamSira) {
        txt_siralama.setText("Sıralamanız : "+seninSiran+"/"+toplamSira);
    }


}
