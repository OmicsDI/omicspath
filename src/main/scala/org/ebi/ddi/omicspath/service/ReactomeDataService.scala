package org.ebi.ddi.omicspath.service

import java.text.SimpleDateFormat

import jdk.nashorn.internal.parser.JSONParser
import org.ebi.ddi.omicspath.model.PathwayDataModel
import org.ebi.ddi.omicspath.utils.Constants

import scala.io._
import upickle.default._
import net.liftweb.json._
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

  def getPathwaysPagesId(pageNumber:String, pageSize: String): Array[PathwayDataModel] ={

    var json:Array[PathwayDataModel] = new Array[PathwayDataModel](pageSize.toInt);

    val pathwayPage = Constants.REACTOME_ROOT_URL + "data/schema/Pathway"

    val r = requests.get(
      pathwayPage,
      params = Map("page" -> pageNumber, "offset" -> pageSize)
    )

    if(r.statusCode.equals(200)) {

      val jValue = parse(r.text)

      // create a Pathways object from the string
      json = jValue.extract[Array[PathwayDataModel]]

    }

    json
  }

  def getPathwaysData(pathwayId:String)={
  ""
  }



}
