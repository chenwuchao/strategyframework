package com.chenwuchao.strategyframework

import java.util

import scala.collection.mutable.ListBuffer

/**
 * Created by wuchao.cwc on 3/13/15.
 */
class StrategyWorker(config:String,name:String,var pc:util.HashMap[String,AnyRef])
    extends BaseStrategy(pc){
    val configure = config
    val workerName = name
    val workflow = Util.createSFConfigure(configure).getWorkFlowByName(workerName)
    val strategyList = new ListBuffer[Strategy]


    /**
     *
     * @return true:init succeed;false:init failed
     */
    override def init(args: String):Boolean={
        val it = workflow.workers.iterator()
        while(it.hasNext){
            val w = it.next()
            val p = Util.getObjectByName(w.className).asInstanceOf[BaseStrategy]
            p.setPropertyCenter(pc)
            if(false.equals(p.init(w.initPatam))){
                System.err.println(p.getClass.getName + " init failed!")
                return  false
            }
            strategyList += p
        }
        true
    }

    /**
     *
     * @return true:run succeed;false:run failed
     */

    def clean()={
        for(s <- strategyList) {
            s.clean()
        }
    }

    override def execute(): Boolean = {
        for(s <- strategyList){
            if(false == s.execute() ){
                System.err.println(s.getClass.getName + "execute failed!")
                return  false
            }
        }
        true
    }
}
