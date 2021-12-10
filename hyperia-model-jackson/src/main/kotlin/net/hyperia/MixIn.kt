package net.hyperia

import com.fasterxml.jackson.annotation.JsonProperty

interface TimeNamesExclusion {
    @JsonProperty("startTime")
    fun getStartTime(): Int

    @JsonProperty("endTime")
    fun getEndTime(): Int
}

interface PointNamesExclusion {
    @JsonProperty("minX")
    fun getMinX(): Int
    @JsonProperty("minY")
    fun getMinY(): Int
    @JsonProperty("maxX")
    fun getMaxX(): Int
    @JsonProperty("maxY")
    fun getMaxY(): Int
}

interface ParticipantMixIn {
    @JsonProperty("longestMonologue")
    fun getLongestMonologue(): Float

    @JsonProperty("totalTalkTime")
    fun getTotalTalkTime(): Float

    @JsonProperty("totalWordCount")
    fun getTotalWordCount(): Int
}