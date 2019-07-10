package com.patronusstudio.kaydirkazan.Contract;

import com.patronusstudio.kaydirkazan.Model.userModelJ;

public interface HomeContractJ {

    interface View{

        void bindViews();

        void clickControl();

        void showPuan(userModelJ user);

        void showSort(int seninSiran,int toplamSira);

        void loadingShow();

        void hideLoadingShow();

        void startGame();

        void startLogin();

        void profileYok();

        void mesajGoster(String mesaj);

    }

    interface Presenter{

        void setView(View view);

        void created();

        void fetchData();

        void startGameButton();

        void logineGit();

    }


}
