package ru.otus.otuskotlin.smartoffice.common.repo

interface IRepoBooking {
    suspend fun createAd(rq: DbAdRequest): IDbAdResponse
    suspend fun readAd(rq: DbAdIdRequest): IDbAdResponse
    suspend fun updateAd(rq: DbAdRequest): IDbAdResponse
    suspend fun deleteAd(rq: DbAdIdRequest): IDbAdResponse
    suspend fun searchAd(rq: DbAdFilterRequest): IDbAdsResponse
    companion object {
        val NONE = object : IRepoBooking {
            override suspend fun createAd(rq: DbAdRequest): IDbAdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readAd(rq: DbAdIdRequest): IDbAdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateAd(rq: DbAdRequest): IDbAdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteAd(rq: DbAdIdRequest): IDbAdResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchAd(rq: DbAdFilterRequest): IDbAdsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
