package com.chenwuchao.strategyframework

import org.junit.Test

/**
 * Created by wuchao.cwc on 3/16/15.
 */
class SFConfigureTest {
    val base = this.getClass.getResource("/").getPath
    val conf = base+"../../src/test/resources/Strategy2.xml"

    @Test
    def sfConfTest(): Unit ={
        val sfConf = new SFConfigure(conf)
        System.out.println(sfConf.root.label)
        val itr = sfConf.workflows.get("test").workers.iterator()
        while(itr.hasNext){
            System.out.print(itr.next().name)
        }


    }

}
