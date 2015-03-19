package com.chenwuchao.strategyframework

import java.util

import scala.xml.{Node, XML}
import scala.xml.parsing.ConstructingParser
import java.io.File
/**
 * Created by wuchao.cwc on 3/16/15.
 */
class SFConfigure(confPath:String){
    val parser = ConstructingParser.fromFile(new File(confPath),true)
    val doc = parser.document()
    val root = doc.docElem
    val workflows = new util.HashMap[String,WorkFlow]()
    val strategys = new util.HashMap[String,SyItem]()
    //begin parser
    for(e <- root.child.toList){
        if(e.label.equals("workflow")){
            val wfName =e.attribute("name").getOrElse("").toString
            val wf = new WorkFlow(wfName)
            workflows.put(wfName,wf)
            for(w <- e.child){
                if(w.label.equals("worker")) {
                    val worker = new Worker(w.attribute("name")
                        .getOrElse("").toString)

                    for (d <- w.child) {
                        if(d.label.equals("depend")) {
                            worker.depends.add(d.text.toString)
                        }
                    }
                    wf.workers.add(worker)
                }
            }
        }
        if(e.label.equals("strategys")){
            for (s <- e.child){
                if(s.label.equals("strategy")){
                    val tmpSy = new SyItem(s.attribute("name").getOrElse("").toString)
                    for(si <- s.child){
                        if(si.label.equals("class")){
                            tmpSy.className = si.text
                        }
                        if(si.label.equals("params")){
                            tmpSy.params = si.text
                        }
                    }
                    strategys.put(tmpSy.name,tmpSy)
                }
            }
        }
    }//end for
    //end parser
    //begin compute execute Sequence
    val itr = workflows.values().iterator()
    while(itr.hasNext){
        val twf = itr.next()
        val tmp = new util.ArrayList[Worker]()
        var twf_it = twf.workers.iterator()
        val set = new util.HashSet[String]()
        while(twf_it.hasNext){
            val w = twf_it.next()
            w.className = strategys.get(w.name).className
            w.initPatam = strategys.get(w.name).params
            if(w.depends.size() == 0){
                tmp.add(w)
                set.add(w.name)
                twf.workers.remove(w)
                twf_it = twf.workers.iterator()

            }else{
                if(allIn(w.depends,set)){
                    tmp.add(w)
                    set.add(w.name)
                    twf.workers.remove(w)
                    twf_it = twf.workers.iterator()
                }
            }
        }//end while
        if(twf.workers.size() == 0){
            System.out.println("OK")
            twf.workers = tmp
            val itr = twf.workers.iterator()
            while(itr.hasNext) {
                val w = itr.next()
                System.out.println(w.name+"->"+w.className+"->"+w.initPatam)
            }
            System.out.println("")
        }else{
            val itr = twf.workers.iterator()
            System.out.println("IN circle:{")
            while(itr.hasNext) {
                System.out.println(itr.next().name)
            }
            System.out.println("}")
            twf.workers.clear()
        }

    }//end while workflows

    class Worker(_name:String){
        var className:String=""
        var initPatam:String=""
        val depends:util.ArrayList[String] = new util.ArrayList[String]()
        val name = _name
    }
    class WorkFlow(_name:String){
        val name = _name
        var workers:util.ArrayList[Worker] = new util.ArrayList[Worker]()
    }
    class SyItem(_name:String){
        val name = _name
        var className:String=""
        var params:String=""
    }
    def allIn(dep:util.ArrayList[String],set:util.HashSet[String]):Boolean={
        val it = dep.iterator()
        while(it.hasNext){
            if(set.contains(it.next())){
                //do nothing
            }else{
                return false
            }
        }
        true
    }

    def getWorkFlowByName(name:String):WorkFlow={
        workflows.get(name)
    }

}
