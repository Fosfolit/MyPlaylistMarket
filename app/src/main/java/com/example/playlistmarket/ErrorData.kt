package com.example.playlistmarket

data class ErrorData(
    val imageError: Int,
    val nameError: String,
    val commentError: String,
    val buttonErrorVisibility: Int,
    val buttonErrorText: String,
)

data class ListErrorData(
    val resultCount: Int,
    val results: String,
    val commentError: String,
    val buttonErrorVisibility: Int,
    val buttonErrorText: String,
)


