package io.fitcentive.scheduler.domain.config

import com.typesafe.config.Config
import io.fitcentive.sdk.config.PubSubTopicConfig

case class TopicsConfig(
  participantAddedToMeetupTopic: String,
  meetupDecisionTopic: String,
  meetupReminderTopic: String,
  scheduleMeetupReminderForLaterTopic: String,
  cancelScheduledMeetupReminderForLaterTopic: String,
  scheduledMeetupReminderTopic: String
) extends PubSubTopicConfig {

  val topics: Seq[String] =
    Seq(
      participantAddedToMeetupTopic,
      meetupDecisionTopic,
      meetupReminderTopic,
      scheduleMeetupReminderForLaterTopic,
      cancelScheduledMeetupReminderForLaterTopic,
      scheduledMeetupReminderTopic
    )

}

object TopicsConfig {
  def fromConfig(config: Config): TopicsConfig =
    TopicsConfig(
      config.getString("participant-added-to-meetup"),
      config.getString("meetup-decision"),
      config.getString("meetup-reminder"),
      config.getString("schedule-meetup-reminder-for-later"),
      config.getString("cancel-scheduled-meetup-reminder-for-later"),
      config.getString("scheduled-meetup-reminder")
    )
}
