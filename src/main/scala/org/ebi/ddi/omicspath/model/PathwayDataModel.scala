package org.ebi.ddi.omicspath.model

import java.util.{Date, Dictionary}

import upickle.default.Reader
import upickle.default._
import upickle.default.{ReadWriter => RW}

case class PathwayDataModel(dbId:String="", displayName: String = "", stId: String="", stIdVersion: String="",
  isInDisease: Boolean= true, isInferred: Boolean=true, name: Array[String]= null, releaseDate: Date= null, speciesName: String="",
  hasDiagram: Boolean=true, schemaClass: String="", className: String=""
)

/*case class PathwayDataModel(dbId:String="", displayName: String = "", stId: String="", stIdVersion: String="",
                            isInDisease: Boolean= true, isInferred: Boolean=true, name: Array[String]= null, releaseDate: String= "", speciesName: String="",
                            hasDiagram: Boolean=true, schemaClass: String="", className: String=""
                           )*/

/*object PathwayDataModel{

  implicit def rw: RW[PathwayDataModel] = macroRW

  /*implicit val rw = upickle.default.readwriter[Object].bimap[PathwayDataModel](
    x => Map(1->true, 2->true, 3->false),
    str => {
      print(str)
      val Array(i, s) = new Array(2)
      new PathwayDataModel(i, s)
    }
  )*/
}*/

