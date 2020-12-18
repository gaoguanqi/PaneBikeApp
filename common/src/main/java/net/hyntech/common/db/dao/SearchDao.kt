package net.hyntech.common.db.dao


import androidx.room.*

@Dao
interface SearchDao {
    @Query("SELECT * FROM searchs")
    fun getAllSearch():List<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(vararg search: Search)

    @Query("SELECT * FROM searchs WHERE id = :id")
    fun getSearchById(id:Long):Search

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSearch(search: Search)

    @Delete
    fun deleteSearch(search: Search)

    @Query("DELETE FROM searchs")
    fun deleteAll()
}