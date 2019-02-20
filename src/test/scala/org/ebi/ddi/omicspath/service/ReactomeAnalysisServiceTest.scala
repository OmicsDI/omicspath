package org.ebi.ddi.omicspath.service

import junit.framework.TestCase
import org.ebi.ddi.omicspath.dbops.MongoOperation

/*
* @author Gaurhari
*
* */
class ReactomeAnalysisServiceTest extends TestCase{

  var analysisService:ReactomeAnalysisService = _

  /*
  * initialising analysis service
  * */
  override def setUp(): Unit = {
    analysisService = new ReactomeAnalysisService
  }

  /*
  * test method to get pathways for datasets
  * */
  def testAnalysis(): Unit = {

    for(i <- 0 to MongoOperation.getDatasetsCounts.toInt/100) {
      val associatedPathways = MongoOperation.getDatasets(i, 100).map(dt => {
        val pathwaysId = dt.crossReferences.
          map(cr => analysisService.getPathwaysAnalysis(cr._2.mkString(","), 100, 1))
          .flatMap(dtr => dtr.pathways.map(dtc => dtc.stId)).toSet
        println(dt.accession)
        println(dt.database)
        MongoOperation.setDatasetPathways(dt.accession, dt.database, pathwaysId)
      }
      ).toList
    }
    //println(associatedPathways.flatMap(dt => dt.flatMap(dtr => dtr.pathways.map(dtc => dtc.stId))).size)


  }

}
