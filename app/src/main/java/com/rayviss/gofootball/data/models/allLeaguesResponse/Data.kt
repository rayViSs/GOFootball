package com.rayviss.gofootball.data.models.allLeaguesResponse

data class Data(
    val cc: String,
    val continent_id: String,
    val continent_name: String,
    val country_id: String,
    val country_name: String,
    val current_round_id: String,
    val current_season_id: String,
    val current_stage_id: Any,
    val id: String,
    val is_amateur: String,
    val is_cup: String,
    val is_friendly: String,
    val name: String
)