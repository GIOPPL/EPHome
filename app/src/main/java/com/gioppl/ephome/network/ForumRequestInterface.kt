package com.gioppl.powergrid.netWork

import com.gioppl.ephome.network.entity.ForumEntity
import retrofit2.http.GET
import rx.Observable

/**
 * Created by GIOPPL on 2017/8/25.
 */
interface ForumRequestInterface {
    @GET("overView.do")fun getForumData(): Observable<ForumEntity>
}