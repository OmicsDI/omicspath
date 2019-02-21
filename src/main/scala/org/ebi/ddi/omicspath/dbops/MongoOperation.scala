package org.ebi.ddi.omicspath.dbops

import java.text.SimpleDateFormat

import com.mongodb.DBObject
import com.mongodb.casbah.{Cursor, MongoClient, MongoClientURI, MongoConnection}
import com.mongodb.casbah.commons.MongoDBObject
import net.liftweb.json.{DefaultFormats, parse}
import org.ebi.ddi.omicspath.model.{Crossreferences, Omicsdi, PathwaySchemaDataModel}
import org.ebi.ddi.omicspath.utils.Constants

import collection.JavaConverters._
/*
* class to perform mongo crud operations
* */
object MongoOperation extends Operation {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT)
  }



  //val uri = MongoClientURI(Constants.DB_CONNECTION_STRING)
  val uri = MongoClientURI(Constants.DB_CONNECTION_STRING)
  val mongoClient = MongoClient(uri)

  val connection = mongoClient.getDB(Constants.DATABASE)
  val collection = connection.getCollection(Constants.COLLECTION)

  def buildMongoDbObject(omicsdi: Omicsdi): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += Constants.ACCESSION_FIELD -> omicsdi.accession
    builder += "database" -> omicsdi.database
    builder += "name" -> omicsdi.name
    builder += "description" -> omicsdi.description
    builder += "dates" -> MongoDBObject("publication"-> omicsdi.dates.get("publication"))
    builder += "additional" -> MongoDBObject("omics_type"-> omicsdi.additional.get("omics_type"),
      "species"-> omicsdi.additional.get("species"),"disease"-> omicsdi.additional.get("disease"))
    builder.result
  }

  /*
  * save data into mongodb
  * */
  def save(mongoObject: DBObject)={
    collection.save(mongoObject)
  }

  /*
  * get datasets which have molecules
  * */
  def getDatasets(pageNumber:Long, pageSize:Long):Set[Crossreferences] ={

    val moleculesDatasets = MongoDBObject("$or" -> Seq(
      MongoDBObject("crossReferences.uniprot" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.ChEBI" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.Ensembl" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.UniProt" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.ensembl" -> MongoDBObject("$exists" -> true))
      )
    )

    val fields = MongoDBObject("accession" -> 1,"database"->1,
      "crossReferences.UniProt"-> 1,"crossReferences.ChEBI"-> 1, "crossReferences.Ensembl" -> 1,
      "crossReferences.uniprot"-> 1,"crossReferences.chebi"-> 1, "crossReferences.ensembl" -> 1
    )

    var skipRecords = 1L
    if(pageNumber != 0) skipRecords = pageNumber*pageSize
    val moleculeDataset = collection.find(moleculesDatasets,fields).limit(pageSize.toInt).skip(skipRecords.toInt)

    val crossReference = moleculeDataset.iterator().asScala.map(dt =>

      Crossreferences(dt.get(Constants.ACCESSION_FIELD).toString, dt.get("database").toString,
        parse(dt.get("crossReferences").toString).values.asInstanceOf[Map[String, Set[String]]]) ).toSet

    crossReference

  }

  /*
  * update datasets with pathways list
  * */
  def setDatasetPathways(accession: String, database: String, pathways: Set[String]): Unit ={
    collection.update(MongoDBObject("accession" -> accession, "database" -> database),
      MongoDBObject("$set" -> MongoDBObject("additional.pathways" -> pathways)))
  }


  /*
  * get all datasets counts
  * */
  def getDatasetsCounts(): Long ={
    val moleculesDatasets = MongoDBObject("$or" -> Seq(
      MongoDBObject("crossReferences.uniprot" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.ChEBI" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.Ensembl" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.UniProt" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.ensembl" -> MongoDBObject("$exists" -> true))
    )
    )
    collection.count(moleculesDatasets)
  }

}
