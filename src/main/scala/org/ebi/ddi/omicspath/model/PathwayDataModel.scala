package org.ebi.ddi.omicspath.model

import java.util.Date


/*
* case class to load model for pathways to get ids and minimal information
* */
case class PathwayDataModel(dbId:String, displayName: String  , stId: String, stIdVersion: String,
  isInDisease: Boolean , isInferred: Boolean, name: Array[String] , releaseDate:Date , speciesName: String,
  hasDiagram: Boolean, schemaClass: String, className: String
)

case class PathwaySchemaDataModel(dbId: Int, displayName: String, stId: String, stIdVersion: String,
  isInDisease: Boolean, isInferred: Boolean, name: Array[String], releaseDate: Date, speciesName: String,
  disease: Disease, species: Species
                                 )

case class Disease(displayName:String)

case class Species(displayName:String)