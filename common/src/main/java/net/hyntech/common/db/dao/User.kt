package net.hyntech.common.db.dao
import android.util.Log
import androidx.room.*
@Entity(tableName = "users")
class User {

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    @ColumnInfo(name = "username")
    var username:String? = ""

    @ColumnInfo(name = "password")
    var password:String? = ""

    @ColumnInfo(name = "userId")
    var userId:String? = ""

    @ColumnInfo(name = "expiresIn")
    var expiresIn:Long? = 0L

    @ColumnInfo(name = "userType")
    var userType:String? = ""

    @ColumnInfo(name = "accessToken")
    var accessToken:String? = ""

    @ColumnInfo(name = "buildType")
    var buildType:String? = ""

    @ColumnInfo(name = "orgId")
    var orgId:String? = ""

    @ColumnInfo(name = "orgName")
    var orgName:String? = ""

    @ColumnInfo(name = "apiUrl")
    var apiUrl:String? = ""

    @ColumnInfo(name = "appWebUrl")
    var appwebUrl:String? = ""

    @ColumnInfo(name = "ebikeNo")
    var ebikeNo:String? = ""

    @ColumnInfo(name = "config")
    var config:String? = ""

    @ColumnInfo(name = "ext1")
    var ext1:String? = ""

    @ColumnInfo(name = "ext2")
    var ext2:String? = ""

    @ColumnInfo(name = "ext3")
    var ext3:String? = ""

}