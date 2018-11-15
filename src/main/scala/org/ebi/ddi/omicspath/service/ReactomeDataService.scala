package org.ebi.ddi.omicspath.service

import java.text.SimpleDateFormat

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import net.liftweb.json._
import org.ebi.ddi.omicspath.dbops.MongoOperation
import org.ebi.ddi.omicspath.model.{Omicsdi, PathwayDataModel, PathwaySchemaDataModel, PathwaysAnalysis}
import org.ebi.ddi.omicspath.utils.Constants


/*
* @author Gaurhari
*
* Service to get reactome pathways
* */
class ReactomeDataService extends DataService  {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd")
  }

  /*
  * get number of pathways in reactome as integer
  *
  * */
  def getPathwaysCount()=  {
    scala.io.Source.fromURL(Constants.REACTOME_ROOT_URL + "data/schema/Pathway/count").mkString
  }

  /*
  *get pathways id data from pages using start page number and page size
  *
  * @param pageNumber
  * @param pageSize
  * */
  def getPathwaysPagesId(pageNumber:String, pageSize: String): Array[PathwayDataModel] ={

    var json:Array[PathwayDataModel] = new Array[PathwayDataModel](pageSize.toInt);

    val pathwayPage = Constants.PATHWAY_PAGE_URL

    val pathwaysIdList = requests.get(
      pathwayPage,
      params = Map("page" -> pageNumber, "offset" -> pageSize)
    )

    if(pathwaysIdList.statusCode.equals(200)) {

      val jValue = parse(pathwaysIdList.text)

      // create a Pathways object from the string
      json = jValue.extract[Array[PathwayDataModel]]

    }

    json
  }

  /*
  * get single pathway data by using its id
  *
  * @param pathwayId
  * */
  def getPathwaysData(pathwayId:String):PathwaySchemaDataModel={

    var pathway:PathwaySchemaDataModel = null

    val pathwayUrl = Constants.PATHWAY_ID_URL + pathwayId

    val pathwayData = requests.get(
      pathwayUrl
    )

    // create a Pathways object from the string
    if(pathwayData.statusCode.equals(Constants.SUCCESS_CODE)){
      pathway = parse(pathwayData.text).extract[PathwaySchemaDataModel]
    }

    pathway
  }

  /*
  * generate xml from list of objects
  *
  * @param pathwaysList
  * */
  def getPathwaysXml(pathwaysList:Array[Omicsdi]): String ={
    val xstream = new XStream(new DomDriver());
    val xml = xstream.toXML(pathwaysList)
    xml
  }

  def getOmicsdiFormat(pathwaySchemaDataModel: PathwaySchemaDataModel): Omicsdi ={

    val omicsdiData = new Omicsdi(pathwaySchemaDataModel.stId,Constants.REACTOME_DATABASE, pathwaySchemaDataModel.displayName
    ,pathwaySchemaDataModel.summation.text,Map("publication" -> Set(pathwaySchemaDataModel.releaseDate.toString)),
      Map("omics_type"-> Set("Pathways"),"disease" -> Set(pathwaySchemaDataModel.disease.displayName),
        "species" -> Set(pathwaySchemaDataModel.species.displayName))
    )
    omicsdiData
  }

  /*
  * save omicsdi format data into mongodb
  * */
  def saveOmics(omicsdi: Omicsdi): Unit ={
    MongoOperation.save(MongoOperation.buildMongoDbObject(omicsdi))
  }


}
