package io.fitcentive.scheduler.domain.config

import com.typesafe.config.Config
import io.fitcentive.sdk.config.PubSubSubscriptionConfig

case class SubscriptionsConfig(
  scheduledMeetupReminderSubscription: String,
  scheduleMeetupReminderForLaterSubscription: String,
  cancelPreviouslyScheduledMeetupReminderForLaterSubscription: String
) extends PubSubSubscriptionConfig {

  val subscriptions: Seq[String] = Seq(
    scheduledMeetupReminderSubscription,
    scheduleMeetupReminderForLaterSubscription,
    cancelPreviouslyScheduledMeetupReminderForLaterSubscription
  )
}

object SubscriptionsConfig {
  def fromConfig(config: Config): SubscriptionsConfig =
    SubscriptionsConfig(
      config.getString("scheduled-meetup-reminder"),
      config.getString("schedule-meetup-reminder-for-later"),
      config.getString("cancel-scheduled-meetup-reminder-for-later"),
    )
}
