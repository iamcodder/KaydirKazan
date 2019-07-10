package com.patronusstudio.kaydirkazan.Contract;

import com.patronusstudio.kaydirkazan.Model.soruModelJ;

import java.util.ArrayList;

public interface SoruContractJ {

    interface View{

        void bindViews();

        void cardTasarimi();

        void recyclerSetle(ArrayList<soruModelJ> soruListesi);

        void trueAnswerNumber(int dogruSayisi);

        void progressShow();

        void progressHide();

        void gameOver();

        void finishTime();

        void startTimer();

        void resetTimer();

        void stopTimer();

        void startTrueAnim();

        void startFalseAnim();

        void sesiOynat(int ses);

    }

    interface Presenter{

        void setView(View view);

        void created();

        void dogruCevap(int dogruSayisi);

        void yanlisCevap();

        void zamanTukendi();

        void sesiOynat(int ses);

    }

}
