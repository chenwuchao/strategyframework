package com.chenwuchao.strategyframework.examples

import com.chenwuchao.strategyframework.BaseStrategy
import java.util

/**
 * Created by wuchao.cwc on 3/14/15.
 */
class StringStrategyExample(var pro:util.Map[String,AnyRef])
    extends BaseStrategy(pro) {
    var key:String = null
    var value:String = null
    def this()={
        this(null)
    }

    override def init(args:String): Boolean = {
        val kv = args.split(",")
        key = kv(0)
        value = kv(1)
        true
    }

    override def execute(): Boolean = {
        val value = get(key)
        value.asInstanceOf[StringBuffer].append(this.value)
        true
    }

    override def clean(): Unit = {}
}
