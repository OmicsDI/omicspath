package org.ebi.ddi.omicspath.utils


/*
* @author Gaurhari
*
* class to keep constants values which can be used to make it more configurable
* */
object Constants  {

  val REACTOME_ROOT_URL = "https://reactome.org/ContentService/"

  val PATHWAY_ID_URL = REACTOME_ROOT_URL + "data/query/"

  val PATHWAY_PAGE_URL = REACTOME_ROOT_URL + "data/schema/Pathway"

  val SUCCESS_CODE = 200

  val REACTOME_DATABASE = "Reactome"

  val PATHWAY_ANALYSIS_URL = "https://reactome.org/AnalysisService/identifiers/"

  val CHEBI = "CHEBI:"

  val SERVER = "localhost"

  val PORT   = 27017

  val DATABASE = "ddi_db"

  val COLLECTION = "datasets.dataset"

  val DATE_FORMAT = "yyyy-MM-dd"

  val ACCESSION_FIELD = "accession"

  val DATABASE_FIELD = "database"

  val CROSSREFERENCES_FIELD = "crossReferences"

  


}
