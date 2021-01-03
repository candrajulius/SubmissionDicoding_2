package com.dev_candra.myapplication.model

data class DetailUser (
    var id: Int,
    var name: String,
    var location: String,
    var followers: Int,
    var following: Int,
    var avatarUrl: String
)