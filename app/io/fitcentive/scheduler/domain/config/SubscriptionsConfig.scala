package io.fitcentive.scheduler.domain.config

import com.typesafe.config.Config
import io.fitcentive.sdk.config.PubSubSubscriptionConfig

case class SubscriptionsConfig(
  scheduleMeetupReminderForLaterSubscription: String,
  cancelPreviouslyScheduledMeetupReminderForLaterSubscription: String,
  scheduleMeetupStateTransitionForLaterSubscription: String,
  cancelPreviouslyScheduledMeetupStateTransitionForLaterSubscription: String
) extends PubSubSubscriptionConfig {

  val subscriptions: Seq[String] =
    Seq(
      scheduleMeetupReminderForLaterSubscription,
      cancelPreviouslyScheduledMeetupReminderForLaterSubscription,
      scheduleMeetupStateTransitionForLaterSubscription,
      cancelPreviouslyScheduledMeetupStateTransitionForLaterSubscription
    )
}

object SubscriptionsConfig {
  def fromConfig(config: Config): SubscriptionsConfig =
    SubscriptionsConfig(
      config.getString("schedule-meetup-reminder-for-later"),
      config.getString("cancel-scheduled-meetup-reminder-for-later"),
      config.getString("schedule-meetup-state-transition-for-later"),
      config.getString("cancel-scheduled-meetup-state-transition-for-later"),
    )
}
