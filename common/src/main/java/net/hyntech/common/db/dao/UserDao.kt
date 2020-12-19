package net.hyntech.common.db.dao


import androidx.room.*
import net.hyntech.baselib.app.BaseApp
import net.hyntech.baselib.utils.LogUtils
import net.hyntech.common.global.BuildType

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUser():List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user:User)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id:Long):User

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user:User)

    @Query("DELETE FROM users")
    fun deleteAll()


    fun getCurrentUser(): User?{
        val users = getAllUser()
        if(!users.isEmpty()){
            LogUtils.logGGQ("getCurrentUser->>>${users.size}->>type:${BaseApp.instance.getBuildType()}")
            return users.last()
        }
        return null
    }
}