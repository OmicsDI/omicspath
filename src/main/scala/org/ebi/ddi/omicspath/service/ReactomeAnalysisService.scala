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
  def getPathwaysAnalysis(moleculesList:String, pageSize:Int, pageNumber:Int): PathwaysAnalysis ={

    val postData = moleculesList.replace("CHEBI:", "")

    var pathway:PathwaysAnalysis = null

    val pathwayUrl = Constants.PATHWAY_ANALYSIS_URL

    val pathwayData = requests.post(
      pathwayUrl,params = Map( "pageSize" -> pageSize.toString, "page" -> pageNumber.toString),
      headers = Map("content-type" -> "text/plain"),
      data = postData
    )

    // create a Pathways object from the string
    if(pathwayData.statusCode.equals(Constants.SUCCESS_CODE)){
      pathway = parse(pathwayData.text).extract[PathwaysAnalysis]
    }

    pathway
  }
}
