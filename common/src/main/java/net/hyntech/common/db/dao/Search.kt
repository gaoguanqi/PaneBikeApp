package net.hyntech.common.db.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchs")
class Search {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    @ColumnInfo(name = "type")
    var type:Int = 0

    @ColumnInfo(name = "content")
    var content:String? = ""

    @ColumnInfo(name = "text")
    var text:String? = ""
}