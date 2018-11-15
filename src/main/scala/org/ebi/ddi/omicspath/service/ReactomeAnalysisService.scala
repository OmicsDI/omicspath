package org.ebi.ddi.omicspath.service

import java.text.SimpleDateFormat

import net.liftweb.json.{DefaultFormats, parse}
import org.ebi.ddi.omicspath.model.PathwaysAnalysis
import org.ebi.ddi.omicspath.utils.Constants

/*
* @author Gaurhari
*
* Pathways analysis service to get pathways using molecules
*
* */
class ReactomeAnalysisService extends AnalysisService {

  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd")
  }

  /*
  * get pathways analysis of molecules
  * */
  def getPathwaysAnalysis(moleculesList:String, pageSize:Int, pageNumber:Int): Unit ={

    var pathway:PathwaysAnalysis = null

    val pathwayUrl ="https://reactome.org/AnalysisService/identifiers/"

    val pathwayData = requests.post(
      pathwayUrl,data = Map("input" -> moleculesList, "pageSize" -> pageSize.toString, "page" -> pageNumber.toString)
    )

    // create a Pathways object from the string
    if(pathwayData.statusCode.equals(Constants.SUCCESS_CODE)){
      pathway = parse(pathwayData.text).extract[PathwaysAnalysis]
    }

    pathway
  }
}
