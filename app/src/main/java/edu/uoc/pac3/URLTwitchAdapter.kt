package edu.uoc.pac3

import android.util.Log

class URLTwitchAdapter
{
    val TAG:String= "URLTwitchAdapter"

    fun adaptURL(urlToAdapt: String?):String
    {
        var result : String = ""

        val widthWord = "{width}"
        val widthValue = "100"

        val heightWord = "{height}"
        val heightValue = "100"

        result = urlToAdapt.toString()
                            .replace(widthWord,widthValue)
                            .replace(heightWord,heightValue)

        Log.i(TAG,result)

        return result
    }
}