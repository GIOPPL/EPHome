package com.gioppl.ephome

import android.content.Context
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.gioppl.ephome.ep.GoodsPriceEntity
import com.google.gson.Gson

/**
 * Created by GIOPPL on 2017/9/23.
 */
class FinalValue {
    companion object {
        //不需要刷新的数据
//        internal var policyList=MyArrayList<PolicyEntity>()
        internal var goodsPriseList=ArrayList<GoodsPriceEntity>()


        //所有接口 "http://116.196.91.8:8080/webtest/ServletPPLinsert"
         var BASE_URL="http://192.168.42.234:8080";
         var INTERFACE_REGISTER="/Register"
         var INTERFACE_POLICY="/ServletZhengceLimit"
         var INTERFACE_ADDFORUM="/ServletPPLinsert"
         var INTERFACE_GOODS_PRICE="/ServletCountGet"
         var INTERFACE_EP="/ServletChange";
         var INTERFACE_FORUM="/ServletDxlFindAll";
         var INTERFACE_FORUM_LOAD_MORE="/ServletPPLLimit";
         var INTERFACE_UPDATE_PASSWORD="/ServletPPLuserupdate";
         var INTERFACE_SEARCH_FORUM="/ServletPPLvague";
         var INTERFACE_USER_UPDATE="/ServletPPLuserupdate";
         var INTERFACE_POLLUTION="/ServletEnvGetall";
         var INTERFACE_ServletNongyeHuanbao="/ServletNongyeHuanbao";
         var INTERFACE_ServletResourceLimitTo="/ServletResourceLimitTo";
         var INTERFACE_ServletNewLimitTo="/ServletNewLimitTo";

        //数据第一次加载
        internal var FORUM_IS_FIRST_LOAD=true//论坛是不是第一次加载
        internal var POLICY_IS_FIRST_LOAD=true//法律法规第一次加载

        //数据加载标识
        private var FORUM_REFRESH_FLAG=0;
        private var POLICY_REFRESH_FLAG=0;


        //个人信息
        internal var ID="12345"
        internal var PHONE="15620520750"
        internal var LOAD_STA=false//登陆状态
        internal var USER_NAME="GIOPPL"//登陆名
        internal var USER_PASSWORD="GIOPPL"//登陆密码
        internal var ADDRESS="天津"
        internal var HEAD_PHOTO_ADD=""
        internal var HEAD_PHOTO_URL="http://ac-rxsnxjjw.clouddn.com/LBT7wbbfWF0bIvzwbH7kmF0J62OpeQlaNTx7bSp3.jpg"
        internal var ADMIN=false
        internal var MAIL="1766468447@qq.com"

        //其他
        internal var FORUM_COUNT=0



        //刷新返回数字
        public fun getForumRefreshFlag():Int{//论坛返回
            FORUM_REFRESH_FLAG+=5
            return FORUM_REFRESH_FLAG-5
        }
        public fun clearForumRefreshFlag(){//论坛清除
            FORUM_REFRESH_FLAG=0;
        }

        public fun getPolicyRefreshFlag():Int{//法规返回
            POLICY_REFRESH_FLAG+=10
            return POLICY_REFRESH_FLAG-10
        }




        //其他方法
        public fun toast(context: Context,msg:String=context.packageName){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }
        public fun errorMessage(s:String="ERROR"){
            Log.i("ERROR",s)
        }
        public fun successMessage(msg: String="SUCCESS",s:String="SUCCESS"){
            Log.i(msg,s)
        }


        public fun getMessage(obj:Any,arg1:Int=0xff):Message{
            var msg=Message()
            msg.obj=obj
            msg.arg1=arg1
            return msg
        }

        public fun JsonToEntityInObject(json: String): com.gioppl.ephome.sliding.login.LoginEntity {
            val gson = Gson()
            return gson.fromJson(json, com.gioppl.ephome.sliding.login.LoginEntity::class.java)
        }




    }
//
//    public fun toast(context: Context,msg:String=context.packageName){
//        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
//    }
//    public fun errorMessage(s:String="ERROR"){
//        Log.i("ERROR",s)
//    }
//    public fun successMessage(s:String="SUCCESS"){
//        Log.i("SUCCESS",s)
//    }
}