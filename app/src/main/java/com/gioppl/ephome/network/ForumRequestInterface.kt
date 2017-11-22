package com.gioppl.ephome.network

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by GIOPPL on 2017/11/21.
 */

interface ForumRequestInterface{
    @GET("ServletDiscuzLimit ")
    fun data(@Query("from") start: Int, @Query("to") end: Int): Observable<ForumEntity>
}
