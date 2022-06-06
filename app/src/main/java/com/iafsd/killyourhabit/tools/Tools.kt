package com.iafsd.killyourhabit.tools

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Looper
import android.util.Log

object Tools{

   fun splitEmailGetFirstPart(email:String) = email.split("@")[0]

   fun showMeThread(tag: String) {

      val b = Looper.getMainLooper().thread == Thread.currentThread()
      val currenttread = Thread.currentThread()
      // Thread.sleep(5000)
      Log.wtf(tag, "Main its current thread: $b : ${currenttread.name}")

   }
}
fun Context.findActivity(): Activity? = when(this){
   is Activity ->this
   is ContextWrapper -> baseContext.findActivity()
   else ->null
}