package com.rayviss.gofootball.data.models.leaguesResponse

data class Data(
    val continent: Continent,
    val country: Country,
    val id: String,
    val id_current_round: Any,
    val id_current_season: String,
    val id_current_stage: Any,
    val img: String,
    val is_amateur: String,
    val is_cup: String,
    val is_friendly: String,
    val name: String,
    val seasons: List<Season>
)