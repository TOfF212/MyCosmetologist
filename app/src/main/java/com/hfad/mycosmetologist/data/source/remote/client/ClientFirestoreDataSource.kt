package com.hfad.mycosmetologist.data.source.remote.client

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClientFirestoreDataSource
@Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    private val clientsCollection = firestore.collection("clients")

    suspend fun saveClient(client: ClientRemoteEntity) {
        clientsCollection.document(client.id).set(client).await()
    }
}
