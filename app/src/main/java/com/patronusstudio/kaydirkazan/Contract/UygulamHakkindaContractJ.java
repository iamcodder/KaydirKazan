package com.patronusstudio.kaydirkazan.Contract;

public interface UygulamHakkindaContractJ {

    interface View{

        void bindView();

        void yazilariGoster(String displayName,Long kayitOlmaTarihi);

        void siralamaGoster(int seninSiran,int toplamSira);

    }

    interface Presenter{

        void onCreated();

        void setView(View view);

    }

}
