package com.chenwuchao.strategyframework

import java.util

import org.junit.Test


/**
 * Created by wuchao.cwc on 3/15/15.
 */
class StrategyWorkerTest {
    val base = this.getClass.getResource("/").getPath
    var sConf = base+"../../src/test/resources/Strategy2.xml"


    @Test
    def StrategyTest() ={
        val sw = new StrategyWorker(sConf,"test",new util.HashMap[String,AnyRef])
        sw.init("")
        sw.put("kk",new StringBuffer().append("bb"))
        sw.execute()
        System.out.println(sw.get("kk"))
        sw.clean()
    }
}
