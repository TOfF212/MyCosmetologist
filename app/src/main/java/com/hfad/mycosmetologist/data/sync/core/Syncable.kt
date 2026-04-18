package com.hfad.mycosmetologist.data.sync.core

fun interface Syncable {
    suspend fun sync()
}
