package com.hfad.mycosmetologist.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hfad.mycosmetologist.data.source.local.db.AppDatabase
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ClientDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.WorkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {

            // --- workers (пересоздание из-за удаления password) ---
            db.execSQL("""
            CREATE TABLE workers_new (
                id TEXT NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                phone TEXT NOT NULL,
                about TEXT NOT NULL,
                is_actual INTEGER NOT NULL,
                specialization TEXT NOT NULL,
                experience INTEGER NOT NULL,
                email TEXT NOT NULL,
                updated_at INTEGER NOT NULL DEFAULT 0,
                is_synced INTEGER NOT NULL DEFAULT 0
            )
        """)

            db.execSQL("""
            INSERT INTO workers_new (
                id, name, phone, about, is_actual,
                specialization, experience, email
            )
            SELECT 
                id, name, phone, about, is_actual,
                specialization, experience, email
            FROM workers
        """)

            db.execSQL("DROP TABLE workers")
            db.execSQL("ALTER TABLE workers_new RENAME TO workers")

            // --- appointments ---
            db.execSQL("""
            ALTER TABLE appointments 
            ADD COLUMN updated_at INTEGER NOT NULL DEFAULT 0
        """)
            db.execSQL("""
            ALTER TABLE appointments 
            ADD COLUMN is_synced INTEGER NOT NULL DEFAULT 0
        """)

            // --- appointment_service ---
            db.execSQL("""
            ALTER TABLE appointment_service 
            ADD COLUMN updated_at INTEGER NOT NULL DEFAULT 0
        """)
            db.execSQL("""
            ALTER TABLE appointment_service 
            ADD COLUMN is_synced INTEGER NOT NULL DEFAULT 0
        """)

            // --- clients ---
            db.execSQL("""
            ALTER TABLE clients 
            ADD COLUMN updated_at INTEGER NOT NULL DEFAULT 0
        """)
            db.execSQL("""
            ALTER TABLE clients 
            ADD COLUMN is_synced INTEGER NOT NULL DEFAULT 0
        """)

            // --- services ---
            db.execSQL("""
            ALTER TABLE services 
            ADD COLUMN updated_at INTEGER NOT NULL DEFAULT 0
        """)
            db.execSQL("""
            ALTER TABLE services 
            ADD COLUMN is_synced INTEGER NOT NULL DEFAULT 0
        """)
        }
    }
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "my_cosmetologist.db",
            )
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideAppointmentDao(database: AppDatabase): AppointmentDao = database.appointmentDao()

    @Provides
    fun provideAppointmentServiceDao(database: AppDatabase): AppointmentServiceDao = database.appointmentServiceDao()

    @Provides
    fun provideClientDao(database: AppDatabase): ClientDao = database.clientDao()

    @Provides
    fun provideServiceDao(database: AppDatabase): ServiceDao = database.serviceDao()

    @Provides
    fun provideWorkerDao(database: AppDatabase): WorkerDao = database.workerDao()

}


