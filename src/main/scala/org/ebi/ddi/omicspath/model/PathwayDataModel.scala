package org.ebi.ddi.omicspath.model

import java.util.Date

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject


/*
* case class to load model for pathways to get ids and minimal information
* */
case class PathwayDataModel(dbId:String, displayName: String, stId: String, stIdVersion: String,
  isInDisease: Boolean, isInferred: Boolean, name: Array[String], releaseDate:Date, speciesName: String,
  hasDiagram: Boolean, schemaClass: String, className: String
)

/*
* model for complete pathways
* */
case class PathwaySchemaDataModel(dbId: Int, displayName: String, stId: String, stIdVersion: String,
  isInDisease: Boolean, isInferred: Boolean, name: Array[String], releaseDate: Date, speciesName: String,
  disease: Disease, species: Species,summation: Summation
                                 )

/*
* associated pathways to molecules
* */
case class Pathways(stId: String, dbId:Int, name: String, species: AnalysisSpecies)

/*
* model for disease in pathways
* */
case class Disease(displayName:String)

/*
* model for species in pathways
* */
case class Species(displayName:String)

/*
* species model for pathways of analysis service
*
* */
case class AnalysisSpecies(name: String)

/*
* model for summation in pathways
* */
case class Summation(displayName:String, text:String)


/*
* model for omicsdi format to get data from pathways
* */
case class Omicsdi(accession:String, database:String, name:String, description:String, dates:Map[String, Set[String]],
                  additional:Map[String, Set[String]]
                  )

/*
* model for get pathways analysis
* */
case class PathwaysAnalysis(identifiersNotFound:Int, pathwaysFound: Int, pathways: List[Pathways] )

/*
* model for crossreferences omicsdi
* */
case class Crossreferences(accession:String, database:String, crossReferences:Map[String, Set[String]] )

