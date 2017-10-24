package com.gioppl.ephome

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by GIOPPL on 2017/9/23.
 */
class FinalValue {
    companion object {
        internal var LOAD_STA=false//登陆状态
        internal var USER_NAME="GIOPPL"//登陆名
        internal var USER_PASSWORD="GIOPPL"//登陆名
        internal var ADDRESS="天津"
        public fun toast(context: Context,msg:String=context.packageName){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }
        public fun errorMessage(s:String="ERROR"){
            Log.i("ERROR",s)
        }
        public fun successMessage(s:String="SUCCESS"){
            Log.i("SUCCESS",s)
        }
    }

    public fun toast(context: Context,msg:String=context.packageName){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
    public fun errorMessage(s:String="ERROR"){
        Log.i("ERROR",s)
    }
    public fun successMessage(s:String="SUCCESS"){
        Log.i("SUCCESS",s)
    }
}