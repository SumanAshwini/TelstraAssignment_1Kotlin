package com.infy.telstraassignment_1kotlin

import com.infy.telstraassignment_1kotlin.model.Canada
import com.infy.telstraassignment_1kotlin.network.APIUtils
import com.infy.telstraassignment_1kotlin.roomdb.RoomEntity
import org.junit.Test

import org.junit.Assert.*
import java.util.ArrayList

class CanadaPresenter {

    @Test
    fun list() {
        val canadasModelArrayList = ArrayList<Canada>()
        for (i in 0..4) {
            val canada = Canada()
            canada.title = "Test"
            canada.description = "Test"
            canada.imageHref = "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg"
            canadasModelArrayList.add(canada)
        }
        assertEquals(5, canadasModelArrayList.size.toLong())
    }

    @Test
    fun getCanadasList() {
        val call = APIUtils.getAPI("s/2iodh4vg0eortkl/facts.json")
        try {
            val response = call.execute()
            assertTrue(response.isSuccessful())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Test
    fun prepareLocalDbList() {
        val titlesArrayList = ArrayList<RoomEntity>()
        for (i in 0..2) {
            val titles = RoomEntity(
                "Test",
                "Test",
                "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg"
            )
            titlesArrayList.add(titles)
        }
        assertEquals(3, titlesArrayList.size.toLong())
    }


}