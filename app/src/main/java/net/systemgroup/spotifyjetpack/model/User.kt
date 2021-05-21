package net.systemgroup.spotifyjetpack.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class User(
    @PrimaryKey val id :String,
    val type :String,
    val uri :String,
    val href :String,
    val display_name : String,
    @Embedded(prefix = "followers_")
    val followers : Followers,
    @Embedded
    val external_urls : ExternalUrl,
    var refreshTime : Long? = null
) {
    @Ignore
    var images : MutableList<Image> = mutableListOf()
}
