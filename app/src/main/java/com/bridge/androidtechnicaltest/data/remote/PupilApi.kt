package com.bridge.androidtechnicaltest.data.remote

import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.db.PupilList
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface PupilApi {
    /**
     * Returns a paged collection of pupils
     *
     * GET /pupils?page=1
     */
    @GET("pupils")
    suspend fun getPupils(
        @Query("page") page: Int = 1
    ): PupilList


    @POST("pupils")
    suspend fun createPupil(@Body newPupil: Pupil): Pupil

    @GET("pupils/{pupilId}")
    suspend fun getPupil(@Path("pupilId") pupilId: Int): Pupil

    @PUT("pupils/{pupilId}")
    suspend fun updatePupil(@Path("pupilId") pupilId: Int, @Body updated: Pupil): Pupil

    @DELETE("pupils/{pupilId}")
    suspend fun deletePupil(@Path("pupilId") pupilId: Int)
}
