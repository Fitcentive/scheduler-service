package io.fitcentive.scheduler.modules

import com.github.kagkarlsson.scheduler.Scheduler
import com.github.kagkarlsson.scheduler.task.helper.Tasks
import com.google.inject.{AbstractModule, Provides}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import io.fitcentive.scheduler.domain.config.DatabaseConfig
import io.fitcentive.scheduler.infrastructure.scheduler.{DbScheduler, SchedulerExecutionHandler}
import io.fitcentive.scheduler.services.SettingsService

import javax.inject.Singleton
import scala.concurrent.ExecutionContext
import scala.util.chaining.scalaUtilChainingOps

class DbSchedulerModule extends AbstractModule {

  private def makeSource(config: DatabaseConfig): HikariDataSource =
    new HikariConfig()
      .tap(_.setJdbcUrl(config.url))
      .tap(_.setUsername(config.username))
      .tap(_.setPassword(config.password))
      .tap(_.addDataSourceProperty("cachePrepStmts", "true"))
      .tap(_.addDataSourceProperty("prepStmtCacheSize", "250"))
      .tap(_.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"))
      .pipe(new HikariDataSource(_))

  @Provides
  @Singleton
  def provideDbScheduler(settingsService: SettingsService, handler: SchedulerExecutionHandler)(implicit
    ec: ExecutionContext
  ): DbScheduler = {
    val task = Tasks
      .oneTime[String](DbScheduler.ScheduleOneTimePubSubMessageTaskName, classOf[String])
      .execute(handler)

    val source = makeSource(settingsService.databaseConfig)

    val scheduler = Scheduler
      .create(source, task)
      .threads(5)
      .build()

    new DbScheduler(scheduler, task)
  }
}
