package org.ebi.ddi.omicspath.model

import java.util.{Date, Dictionary}

import upickle.default.Reader
import upickle.default._
import upickle.default.{ReadWriter => RW}

case class PathwayDataModel(dbId:String="", displayName: String = "", stId: String="", stIdVersion: String="",
  isInDisease: Boolean= true, isInferred: Boolean=true, name: Array[String]= null, releaseDate: Date= null, speciesName: String="",
  hasDiagram: Boolean=true, schemaClass: String="", className: String=""
)


