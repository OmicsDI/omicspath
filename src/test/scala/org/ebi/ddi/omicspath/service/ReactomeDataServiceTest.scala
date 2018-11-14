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
  def testCount(): Unit ={
    assertEquals(reactomeDataService.getPathwaysCount(), "23520")
  }


  /*
  * test case to get data from pathways ids
  *
  * */
  def testPathwaysPage(): Unit = {
    println(reactomeDataService.getPathwaysPagesId("0","25"))
    assertNotNull(reactomeDataService.getPathwaysPagesId("0","25"))
  }
}
