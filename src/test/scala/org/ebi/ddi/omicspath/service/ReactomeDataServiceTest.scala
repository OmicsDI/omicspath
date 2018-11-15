package org.ebi.ddi.omicspath.service


import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert._


/*
* @author Gaurhari
*
* class to test reactome data service function calls
*
* */
class ReactomeDataServiceTest extends TestCase {

  /*
  * variable with reactomedata service type
  *
  * */
  var reactomeDataService:ReactomeDataService = _


  /*
  * initializing reactomeDataservice variable
  * */
  override def setUp(): Unit = {
    reactomeDataService = new ReactomeDataService
  }


  /*
  * test case to check counts of pathways if right or not
  * */
  def testCount(): Unit = {
    assertEquals(reactomeDataService.getPathwaysCount(), "23520")
  }


  /*
  * test case to get data from pathways ids
  *
  * */
  def testPathwaysPage(): Unit = {
    assertNotNull(reactomeDataService.getPathwaysPagesId("0","25"))
  }

  /*
  *test case to get single pathway data
  * */
  def testPathwaysSchema(): Unit = {
    assertEquals(reactomeDataService.getPathwaysData("R-HSA-164843").stId,"R-HSA-164843")
  }

  /*
  * test xml from pathways
  * */
  def testXmlGeneration(): Unit = {
    println(reactomeDataService.getPathwaysXml(
      Array(reactomeDataService.getOmicsdiFormat(reactomeDataService.getPathwaysData("R-HSA-164843")))))
  }

  /*
  * test omicsdi insertion into mongodb
  * */
  def testOmicsInsertion(): Unit = {
    val omicsdi = reactomeDataService.getOmicsdiFormat(reactomeDataService.getPathwaysData("R-HSA-164843"))
    reactomeDataService.saveOmics(omicsdi)
  }

  /*
  * test mongodb retrieval of records
  * */
  def testMongoRetrieval(): Unit = {
    //reactomeDataService.
  }


}
