package com.rayviss.gofootball.data.models.leaguesResponse

data class Meta(
    val count: Int,
    val msg: Any,
    val page: Int,
    val pages: Int,
    val plan: String,
    val requests_left: Int,
    val total: Int,
    val user: String
)