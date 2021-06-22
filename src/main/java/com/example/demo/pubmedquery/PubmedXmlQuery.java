package com.example.demo.pubmedquery;


public class PubmedXmlQuery {

  public static final int DEFAULT_RETMAX = 20;

  public static final String ESEARCH_BASE_URL = "https://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi";
  protected static final String EFETCH_BASE_URL = "https://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi";

  private String db = "pubmed";


  private String apiKey = "9e867cf6a822a7458664fdd61fb0b2b84508";

  /**
   * Entrez text query. All special characters must be URL encoded. Spaces may be replaced by '+'
   * signs. For very long queries (more than several hundred characters long), consider using an
   * HTTP POST call. (Required parameter).
   */
  private String term;


  private int retMax = DEFAULT_RETMAX;


  private int retStart;

  /**
   * When {@link PubmedXmlQuery#useHistory} is set to {@code true}, ESearch will post the UIDs
   * resulting from the search operation onto the PubMed history server so that they can be used
   * directly in a subsequent E-utility call. Also {@link PubmedXmlQuery#useHistory} must be set to
   * {@code true} for ESearch to interpret query key values included in {@link PubmedXmlQuery#term}
   * or to accept a {@link PubmedXmlQuery#webEnv} as input.
   */
  private String useHistory = "y";

  /**
   * Web environment string returned from a previous ESearch, EPost or ELink call. When provided,
   * ESearch will post the results of the search operation to this pre-existing {@link
   * PubmedXmlQuery#webEnv}, thereby appending the results to the existing environment.
   */
  private String webEnv;


  private String retMode = "json";

  public PubmedXmlQuery() {
  }


  public PubmedXmlQuery(String term) {
    this.term = term;
  }

  /**
   * Constructs a ESearch query String.
   *
   * @return a String in the format http://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&retmax=1&usehistory=y&term=Kukafka%20R[au]
   */
  public String buildESearchQuery() {
    return ESEARCH_BASE_URL
        + "?api_key="
        + apiKey
        + "&db="
        + db
        + "&term="
        + term
        + "&retmax="
        + retMax
        + "&usehistory="
        + useHistory
        + "&retmode="
        + retMode;
  }


  public String buildEFetchQuery(String pmids) {
    return EFETCH_BASE_URL
        + "?api_key="
        + apiKey
        + "&db="
        + db
        + "&id="
        + pmids
        + "&rettype="
        + "abstract"
        + "&retmode="
        + "xml";
  }

}