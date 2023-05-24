package io.fitcentive.scheduler.domain.config

import com.typesafe.config.Config
import io.fitcentive.sdk.config.PubSubTopicConfig

case class TopicsConfig(
  scheduleMeetupReminderForLaterTopic: String,
  cancelScheduledMeetupReminderForLaterTopic: String,
  scheduledMeetupReminderTopic: String,
  scheduledMeetupStateTransitionTopic: String,
  scheduleMeetupStateTransitionForLaterTopic: String,
  cancelScheduledMeetupStateTransitionForLaterTopic: String,
) extends PubSubTopicConfig {

  val topics: Seq[String] =
    Seq(
      scheduleMeetupReminderForLaterTopic,
      cancelScheduledMeetupReminderForLaterTopic,
      scheduledMeetupReminderTopic,
      scheduledMeetupStateTransitionTopic,
      scheduleMeetupStateTransitionForLaterTopic,
      cancelScheduledMeetupStateTransitionForLaterTopic
    )

}

object TopicsConfig {
  def fromConfig(config: Config): TopicsConfig =
    TopicsConfig(
      config.getString("schedule-meetup-reminder-for-later"),
      config.getString("cancel-scheduled-meetup-reminder-for-later"),
      config.getString("scheduled-meetup-reminder"),
      config.getString("scheduled-meetup-state-transition"),
      config.getString("schedule-meetup-state-transition-for-later"),
      config.getString("cancel-scheduled-meetup-state-transition-for-later"),
    )
}
