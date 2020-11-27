package net.hyntech.common.model.entity

data class TestEntity(
    val appVersion: String,
    val mobileTye: String,
    val netType: String,
    val nonce: String,
    val phone: String,
    val pwd: String,
    val sig: String,
    val timestamp: String
)