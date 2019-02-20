package org.ebi.ddi.omicspath.service

import junit.framework.TestCase
import org.ebi.ddi.omicspath.dbops.MongoOperation

/*
* @author Gaurhari
* */
class MongoOpServiceTest extends TestCase{

    def testMongoRetrieval(): Unit ={
      println(MongoOperation.getDatasets(0, 100).head.crossReferences)
    }

}
