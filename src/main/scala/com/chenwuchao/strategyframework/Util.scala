package com.chenwuchao.strategyframework

import java.util

import scala.collection.mutable


/**
 * Created by wuchao.cwc on 3/14/15.
 */
object Util {

    val classMap:mutable.HashMap[String,Class[_]] = new mutable.HashMap[String,Class[_]]()
    def getObjectByName[T](name:String):T= {
        var clzz:Class[T] = null
        if(classMap.contains(name)){
            clzz = classMap.getOrElse(name,null).asInstanceOf[Class[T]]
        }else {
            clzz = Class.forName(name).asInstanceOf[Class[T]]
            classMap.put(name, clzz)
        }
        val obj = clzz.newInstance()
        obj
    }

    val sfConfMap = new util.HashMap[Long,SFConfigure]()
    def createSFConfigure(conf:String):SFConfigure={
        if(sfConfMap.containsKey(conf)){
            sfConfMap.get(conf)
        }else{
            new SFConfigure(conf)
        }
    }
}
