package com.hfad.mycosmetologist.data.source.remote.appointment

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppointmentFirestoreDataSource
@Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    private val appointmentsCollection = firestore.collection("appointments")

    suspend fun saveAppointment(appointment: AppointmentRemoteEntity) {
        appointmentsCollection.document(appointment.id).set(appointment).await()
    }
}
