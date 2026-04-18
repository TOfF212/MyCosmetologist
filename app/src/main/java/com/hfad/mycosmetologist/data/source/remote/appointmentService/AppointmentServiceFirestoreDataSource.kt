package com.hfad.mycosmetologist.data.source.remote.appointmentService

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppointmentServiceFirestoreDataSource
@Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    private val appointmentServiceCollection = firestore.collection("appointment_service")

    suspend fun saveAppointmentService(entity: AppointmentServiceRemoteEntity) {
        appointmentServiceCollection.document(entity.id).set(entity).await()
    }
}
