package org.ebi.ddi.omicspath.dbops

import java.text.SimpleDateFormat

import com.mongodb.DBObject
import com.mongodb.casbah.{Cursor, MongoClient, MongoClientURI, MongoConnection}
import com.mongodb.casbah.commons.MongoDBObject
import net.liftweb.json.{DefaultFormats, parse}
import org.ebi.ddi.omicspath.model.{Crossreferences, Omicsdi, PathwaySchemaDataModel}
import collection.JavaConverters._
/*
* class to perform mongo crud operations
* */
object MongoOperation extends Operation {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd")
  }

  private val SERVER = "localhost"
  private val PORT   = 27017
  private val DATABASE = "ddi_db"
  private val COLLECTION = "datasets.dataset"

  val uri = MongoClientURI("mongodb://ddi_user:tDzDd81J@mongos-hxvm-001.ebi.ac.uk:27017/ddi_db?authSource=admin")
  val mongoClient = MongoClient(uri)

/*  val connection = MongoConnection("mongodb://ddi_user:tDzDd81J@mongos-hxvm-001.ebi.ac.uk:27017/admin")
  val collection = connection(DATABASE)(COLLECTION)*/

  val connection = mongoClient.getDB(DATABASE)
  val collection = connection.getCollection(COLLECTION)

  def buildMongoDbObject(omicsdi: Omicsdi): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "accession" -> omicsdi.accession
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
  def getDatasets():List[Crossreferences] ={

    val moleculesDatasets = MongoDBObject("$or" -> Seq(
      MongoDBObject("crossReferences.unipprot" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.ChEBI" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.Ensembl" -> MongoDBObject("$exists" -> true)),
      MongoDBObject("crossReferences.UniProt" -> MongoDBObject("$exists" -> true)))
    )

    val fields = MongoDBObject("accession" -> 1,"database"->1, "crossReferences"-> 1)

    //println(collection.findOne(moleculesDatasets,fields).get("crossReferences").toString)
    val moleculeDataset = collection.find(moleculesDatasets,fields).limit(10)

    val testData = moleculeDataset.iterator().asScala.map(dt =>
    {println(dt);
       //println(parse(dt.get("crossReferences").toString));
      Crossreferences(dt.get("accession").toString, dt.get("database").toString,
        parse(dt.get("crossReferences").toString).values.asInstanceOf[Map[String, Set[String]]]) }).toList

    //val omicsdi:Iterator[Crossreferences] = (for (u <- moleculeDataset.iterator.asScala) yield parse(u.toString).extract[Crossreferences])
    testData
    //omicsdi
/*    val omicsdi = parse(moleculeDataset.toString).extract[Crossreferences]
    omicsdi*/
  }



}
