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

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("PRAGMA foreign_keys=OFF")

            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `workers_new` (
                    `id` TEXT NOT NULL,
                    `name` TEXT NOT NULL,
                    `phone` TEXT NOT NULL,
                    `about` TEXT NOT NULL,
                    `is_actual` INTEGER NOT NULL DEFAULT 1,
                    `specialization` TEXT NOT NULL DEFAULT '',
                    `experience` INTEGER NOT NULL DEFAULT 0,
                    `email` TEXT NOT NULL DEFAULT '',
                    `updated_at` INTEGER NOT NULL DEFAULT 0,
                    `is_synced` INTEGER NOT NULL DEFAULT 0,
                    PRIMARY KEY(`id`)
                )
                """.trimIndent(),
            )
            database.execSQL(
                """
                INSERT INTO `workers_new` (
                    `id`, `name`, `phone`, `about`, `is_actual`, `specialization`, `experience`, `email`, `updated_at`, `is_synced`
                )
                SELECT
                    `id`, `name`, `phone`, `about`, 1, '', 0, '', 0, 0
                FROM `workers`
                """.trimIndent(),
            )
            database.execSQL("DROP TABLE `workers`")
            database.execSQL("ALTER TABLE `workers_new` RENAME TO `workers`")

            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `clients_new` (
                    `id` TEXT NOT NULL,
                    `worker_id` TEXT NOT NULL,
                    `name` TEXT NOT NULL,
                    `phone` TEXT NOT NULL,
                    `about` TEXT NOT NULL,
                    `updated_at` INTEGER NOT NULL DEFAULT 0,
                    `is_synced` INTEGER NOT NULL DEFAULT 0,
                    PRIMARY KEY(`id`),
                    FOREIGN KEY(`worker_id`) REFERENCES `workers`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT
                )
                """.trimIndent(),
            )
            database.execSQL(
                """
                INSERT INTO `clients_new` (`id`, `worker_id`, `name`, `phone`, `about`, `updated_at`, `is_synced`)
                SELECT `id`, `worker_id`, `name`, `phone`, `about`, 0, 0
                FROM `clients`
                """.trimIndent(),
            )
            database.execSQL("DROP TABLE `clients`")
            database.execSQL("ALTER TABLE `clients_new` RENAME TO `clients`")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_clients_worker_id` ON `clients` (`worker_id`)")

            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `services_new` (
                    `id` TEXT NOT NULL,
                    `worker_id` TEXT NOT NULL,
                    `name` TEXT NOT NULL,
                    `price` INTEGER NOT NULL,
                    `duration_minutes` INTEGER NOT NULL,
                    `description` TEXT NOT NULL,
                    `is_archived` INTEGER NOT NULL DEFAULT 0,
                    `updated_at` INTEGER NOT NULL DEFAULT 0,
                    `is_synced` INTEGER NOT NULL DEFAULT 0,
                    PRIMARY KEY(`id`),
                    FOREIGN KEY(`worker_id`) REFERENCES `workers`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT
                )
                """.trimIndent(),
            )
            database.execSQL(
                """
                INSERT INTO `services_new` (
                    `id`, `worker_id`, `name`, `price`, `duration_minutes`, `description`, `is_archived`, `updated_at`, `is_synced`
                )
                SELECT
                    `id`, `worker_id`, `name`, `price`, `duration_minutes`, `description`, 0, 0, 0
                FROM `services`
                """.trimIndent(),
            )
            database.execSQL("DROP TABLE `services`")
            database.execSQL("ALTER TABLE `services_new` RENAME TO `services`")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_services_worker_id` ON `services` (`worker_id`)")

            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `appointments_new` (
                    `id` TEXT NOT NULL,
                    `worker_id` TEXT NOT NULL,
                    `client_id` TEXT NOT NULL,
                    `description` TEXT NOT NULL,
                    `start_time` INTEGER NOT NULL,
                    `end_time` INTEGER NOT NULL,
                    `cancelled` INTEGER NOT NULL DEFAULT 0,
                    `updated_at` INTEGER NOT NULL DEFAULT 0,
                    `is_synced` INTEGER NOT NULL DEFAULT 0,
                    PRIMARY KEY(`id`),
                    FOREIGN KEY(`worker_id`) REFERENCES `workers`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT,
                    FOREIGN KEY(`client_id`) REFERENCES `clients`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT
                )
                """.trimIndent(),
            )
            database.execSQL(
                """
                INSERT INTO `appointments_new` (
                    `id`, `worker_id`, `client_id`, `description`, `start_time`, `end_time`, `cancelled`, `updated_at`, `is_synced`
                )
                SELECT
                    `id`,
                    `worker_id`,
                    `client_id`,
                    `description`,
                    `start_time`,
                    `end_time`,
                    CASE WHEN lower(`status`) IN ('cancelled', 'canceled') THEN 1 ELSE 0 END,
                    0,
                    0
                FROM `appointments`
                """.trimIndent(),
            )
            database.execSQL("DROP TABLE `appointments`")
            database.execSQL("ALTER TABLE `appointments_new` RENAME TO `appointments`")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_appointments_worker_id` ON `appointments` (`worker_id`)")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_appointments_worker_id_start_time` ON `appointments` (`worker_id`, `start_time`)")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_appointments_client_id` ON `appointments` (`client_id`)")

            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `appointment_service_new` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `appointment_id` TEXT NOT NULL,
                    `service_id` TEXT NOT NULL,
                    `updated_at` INTEGER NOT NULL DEFAULT 0,
                    `is_synced` INTEGER NOT NULL DEFAULT 0,
                    FOREIGN KEY(`appointment_id`) REFERENCES `appointments`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                    FOREIGN KEY(`service_id`) REFERENCES `services`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT
                )
                """.trimIndent(),
            )
            database.execSQL(
                """
                INSERT INTO `appointment_service_new` (`appointment_id`, `service_id`, `updated_at`, `is_synced`)
                SELECT `appointment_id`, `service_id`, 0, 0
                FROM `appointment_service`
                """.trimIndent(),
            )
            database.execSQL("DROP TABLE `appointment_service`")
            database.execSQL("ALTER TABLE `appointment_service_new` RENAME TO `appointment_service`")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_appointment_service_appointment_id` ON `appointment_service` (`appointment_id`)")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_appointment_service_service_id` ON `appointment_service` (`service_id`)")
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_appointment_service_appointment_id_service_id` ON `appointment_service` (`appointment_id`, `service_id`)")

            database.execSQL("PRAGMA foreign_keys=ON")
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
