package com.rayviss.gofootball.data.models.teamsResponse

data class Meta(
    val count: Int,
    val page: Int,
    val pages: Int,
    val plan: String,
    val requests_left: Int,
    val user: String
)