package com.example.gardening

import com.google.firebase.database.ServerValue

class PostClass(
    private var postKey: String? = null,
    var description: String? = null,
    var picture: String? = null,
    var userId: String? = null,
    var timeStamp: Any? = null
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        ServerValue.TIMESTAMP
    )

    fun updatePostKey(key: String) {
        this.postKey = key
    }
}
