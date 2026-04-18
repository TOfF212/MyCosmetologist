package com.hfad.mycosmetologist.data.source.remote.core

fun interface Syncable {
    suspend fun sync()
}
