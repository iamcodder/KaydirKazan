package com.patronusstudio.kaydirkazan.Presenter;

import com.google.firebase.database.annotations.NotNull;
import com.patronusstudio.kaydirkazan.Contract.FirebaseContractJ;
import com.patronusstudio.kaydirkazan.Contract.SoruContractJ;
import com.patronusstudio.kaydirkazan.Model.IFirebaseDatabaseJ;
import com.patronusstudio.kaydirkazan.Model.soruModelJ;

import java.util.ArrayList;

public class SoruActivityPresenterJ implements SoruContractJ.Presenter, FirebaseContractJ.SoruListesi {

    private IFirebaseDatabaseJ firebaseDatabase;
    private SoruContractJ.View mView;

    public SoruActivityPresenterJ(IFirebaseDatabaseJ firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    public void setView(@NotNull SoruContractJ.View view) {
        this.mView = view;
    }

    @Override
    public void created() {
        mView.bindViews();
        firebaseDatabase.sorulari_cek(this);
        mView.progressShow();
        mView.stopTimer();
    }

    @Override
    public void soruListesiniDondur(@NotNull ArrayList<soruModelJ> soruListesi) {
        mView.recyclerSetle(soruListesi);
        mView.cardTasarimi();
        mView.progressHide();
        mView.startTimer();
    }

    @Override
    public void dogruCevap(int dogruSayisi) {
        mView.startTrueAnim();
        mView.trueAnswerNumber(dogruSayisi);
        mView.resetTimer();
    }

    @Override
    public void yanlisCevap() {
        mView.startFalseAnim();
        mView.gameOver();
        mView.stopTimer();
    }

    @Override
    public void zamanTukendi() {
        mView.stopTimer();
        mView.finishTime();
    }

    @Override
    public void sesiOynat(int ses) {
        mView.sesiOynat(ses);
    }
}
