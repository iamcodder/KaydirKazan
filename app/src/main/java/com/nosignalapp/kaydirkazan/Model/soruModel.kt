package com.nosignalapp.kaydirkazan.Model

class soruModel(){

    var solCevap:String=""
    var sagCevap:String=""
    var soru:String=""
    var dogruCevap:String=""

    constructor(solCevap:String,soru:String,sagCevap:String,dogruCevap:String) : this(){
        this.solCevap= solCevap
        this.sagCevap=sagCevap
        this.soru=soru
        this.dogruCevap=dogruCevap
    }


}