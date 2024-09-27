package com.example.marvelapp.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.core.data.DbConstants.FAVORITES_COLUMN_INFO_ID
import br.com.core.data.DbConstants.FAVORITES_COLUMN_INFO_IMAGE_URL
import br.com.core.data.DbConstants.FAVORITES_COLUMN_INFO_NAME
import br.com.core.data.DbConstants.FAVORITES_TABLE_NAME
import br.com.core.domain.model.Character

@Entity(tableName = FAVORITES_TABLE_NAME)
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_ID)
    val id: Int,
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = FAVORITES_COLUMN_INFO_IMAGE_URL)
    val imageUrl: String
)

fun List<FavoriteEntity>.toCharactersModel() = map { favoriteEntity ->
    Character(
        favoriteEntity.id, favoriteEntity.name, favoriteEntity.imageUrl
    )
}
