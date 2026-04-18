package com.hfad.mycosmetologist.data.source.remote.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ServiceFirestoreDataSource
@Inject constructor(private val firestore: FirebaseFirestore){

    private val servicesCollection = firestore.collection("services")


//    suspend fun getServicesByWorkerId(workerId: String): ServiceRemoteEntity {
//        val snap = servicesCollection.whereEqualTo("workerId", workerId).get().await()
//        return snap.toObject(ServiceRemoteEntity::class.java)
//    }


    suspend fun saveService(service: ServiceRemoteEntity) {
        servicesCollection.document(service.id).set(service).await()
    }
}
