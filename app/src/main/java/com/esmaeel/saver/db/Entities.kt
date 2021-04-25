package com.esmaeel.saver.db

import androidx.room.*

enum class CardItemType(val type_id: Int) {
    COUNTABLE(type_id = 1),
    TODO(type_id = 2),
    NOTE(type_id = 3),
}

@Entity
data class CardItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "subtitle") var subtitle: String = "",
    @ColumnInfo(name = "icon") var icon: Int,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "total_count") var total_count: Int = 0,
)

@Entity
data class TodoItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "card_id") var card_id: Int,
    @ColumnInfo(name = "text") var text: String? = "",
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "history_id") var history_id: Int = -1,
    @ColumnInfo(name = "done") var done: Boolean = false,
)

@Entity
data class UnitItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "card_id") var card_id: Int,
    @ColumnInfo(name = "text") var text: String? = "",
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "count") var count: Int = 0,

    /* only in : countable and note */
    @ColumnInfo(name = "history_id") var history_id: Int = -1,

    /* only in : todo_item */
    @ColumnInfo(name = "done") var done: Boolean = false,

    @ColumnInfo(name = "type") var type: Int = CardItemType.NOTE.type_id,
)
