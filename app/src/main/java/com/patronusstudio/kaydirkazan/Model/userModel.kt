package com.patronusstudio.kaydirkazan.Model

import java.io.Serializable


class userModel():Serializable {

    var email:String=""
    var uuid:String=""
    var yuksekPuan:String="0"

    constructor(email:String,uuid:String,yuksekPuan:String) : this(){
        this.email=email
        this.uuid=uuid
        this.yuksekPuan=yuksekPuan
    }
}