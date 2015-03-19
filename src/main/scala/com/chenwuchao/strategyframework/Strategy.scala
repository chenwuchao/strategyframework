package com.chenwuchao.strategyframework

import java.util

/**
 * Created by wuchao.cwc on 3/13/15.
 */
trait Strategy {

    def init(args:String):Boolean

    def execute():Boolean

    def clean()

    def get(key:String):AnyRef

    def put(key:String,value:AnyRef)

}
