package com.example.gardening

import androidx.annotation.NonNull
import com.google.firebase.database.Exclude
import java.io.Serializable

open class PostId : Serializable {
    @Exclude
    open var postKey: String? = null

    /**
     * Associates a key with an object.
     *
     * @param id The unique identifier to associate with the object.
     * @return The object with the associated identifier.
     */
    fun <T : PostId> withId(@NonNull id: String): T {
        postKey = id
        // Safe cast because T is expected to be a subtype of PostId
        return this as T
    }
}
