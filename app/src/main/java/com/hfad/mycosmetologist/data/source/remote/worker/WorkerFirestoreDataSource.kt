package com.hfad.mycosmetologist.data.source.remote.worker

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkerFirestoreDataSource
@Inject constructor(
    private val firestore: FirebaseFirestore,
){

    private val workersCollection = firestore.collection("workers")

    suspend fun getWorker(email: String): WorkerRemoteEntity? {
        val snap = workersCollection.document(email).get().await()
        return snap.toObject(WorkerRemoteEntity::class.java)
    }

    suspend fun saveWorker(worker: WorkerRemoteEntity) {
        workersCollection.document(worker.id).set(worker).await()
    }
}
