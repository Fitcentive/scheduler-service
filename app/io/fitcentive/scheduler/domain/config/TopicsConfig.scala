package io.fitcentive.scheduler.domain.config

import com.typesafe.config.Config
import io.fitcentive.sdk.config.PubSubTopicConfig

case class TopicsConfig(
  scheduleMeetupReminderForLaterTopic: String,
  cancelScheduledMeetupReminderForLaterTopic: String,
  scheduledMeetupReminderTopic: String
) extends PubSubTopicConfig {

  val topics: Seq[String] =
    Seq(scheduleMeetupReminderForLaterTopic, cancelScheduledMeetupReminderForLaterTopic, scheduledMeetupReminderTopic)

}

object TopicsConfig {
  def fromConfig(config: Config): TopicsConfig =
    TopicsConfig(
      config.getString("schedule-meetup-reminder-for-later"),
      config.getString("cancel-scheduled-meetup-reminder-for-later"),
      config.getString("scheduled-meetup-reminder")
    )
}
