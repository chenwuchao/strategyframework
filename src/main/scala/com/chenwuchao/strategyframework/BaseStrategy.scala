package com.chenwuchao.strategyframework

import java.util


/**
 * Created by wuchao.cwc on 3/14/15.
 */
abstract class BaseStrategy(var propertyCenter: util.Map[String,AnyRef] ) extends  Strategy{
    def this()={
        this(null)
    }
    override def get(key: String): AnyRef = {
        propertyCenter.get(key)
    }

    override def put(key: String, value: AnyRef): Unit = {
        propertyCenter.put(key,value)
    }

    def setPropertyCenter(pc:util.Map[String,AnyRef]): Unit ={
        propertyCenter = pc
    }
}
