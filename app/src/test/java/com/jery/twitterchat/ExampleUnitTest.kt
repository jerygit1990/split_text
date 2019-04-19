package com.jery.twitterchat

import com.jery.twitterchat.utils.ChatUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSplitMsg() {
        //"I can't believe Tweeter now supports chunking my messages, so I
        //don't have to do it myself."
        var expected = ArrayList<String>()
        expected.add("1/2 I can't believe Tweeter now supports chunking")
        expected.add("2/2 my messages, so I don't have to do it myself.")

        assertArrayEquals(expected.toArray(), ChatUtils.splitMsg("I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself.")?.toTypedArray())
    }
}
