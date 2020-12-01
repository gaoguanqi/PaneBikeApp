package net.hyntech.common.db.dao
import androidx.room.*
@Entity(tableName = "users")
class User {

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    @ColumnInfo(name = "username")
    var username:String? = ""

    @ColumnInfo(name = "user_type")
    var userType:String? = ""

    @ColumnInfo(name = "access_token")
    var accessToken:String? = ""

    @ColumnInfo(name = "build_type")
    var buildType:String? = ""

    @ColumnInfo(name = "org_id")
    var orgId:String? = ""

    @ColumnInfo(name = "org_name")
    var orgName:String? = ""

    @ColumnInfo(name = "api_url")
    var apiUrl:String? = ""

    @ColumnInfo(name = "appweb_url")
    var appwebUrl:String? = ""

    @ColumnInfo(name = "config")
    var config:String? = ""

    @ColumnInfo(name = "ext1")
    var ext1:String? = ""

    @ColumnInfo(name = "ext2")
    var ext2:String? = ""

    @ColumnInfo(name = "ext3")
    var ext3:String? = ""

}