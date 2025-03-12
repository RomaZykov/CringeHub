package com.example.database.test

import com.example.database.GuideDatabase
import com.example.database.dao.GuideDao
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class GuideDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    private lateinit var guideDao: GuideDao

    @Inject
    @Named("test_guide_db")
    private lateinit var db: GuideDatabase

    @Before
    fun createDb() {
        guideDao = db.getGuideDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveGuideAsDraft() {
        val guide: User = TestUtil.createUser(3).apply {
            setName("george")
        }
        guideDao.insert(guide)
//        val byName = userDao.findUsersByName("george")
//        assertThat(byName.get(0), equalTo(user))
    }

    @Test
    @Throws(Exception::class)
    fun deleteGuideWhenDraft() {
//        val guide: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
//        guideDao.insert(guide)
//        val byName = userDao.findUsersByName("george")
//        assertThat(byName.get(0), equalTo(user))
    }
}