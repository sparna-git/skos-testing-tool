# SKOS Testing Tool

The SKOS testing tool is live at [**http://labs.sparna.fr/skos-testting-tool**](http://labs.sparna.fr/skos-testting-tool).

The SKOS testing tool is a web frontend for [qSKOS](https://github.com/cmader/qSKOS). It allows to assess the quality of SKOS or SKOS-XL vocabularies, by submitting a file to be validated or by validating a SKOS file published at a given URL.

### Linking directly to a report from a SKOS file URL

- You can create a direct link to the validation report of an online SKOS URL :

  http://labs.sparna.fr/skos-testing-tool/test?url={url_of_the_SKOS_file}

  - uses the default rule-set

  - returns the result in a format according to the Accept header preference (Accept: text/html, text/plain, application/rdf+xml, text/turtle)

  - returns the result in a language according to the Accept-Language header preference (fr or en)

    e.g.  http://labs.sparna.fr/skos-testing-tool/test?url=http://publications.europa.eu/mdr/resource/authority/continent/skos/continents-skos.rdf

- To customise the ruleset, use the "rules" parameter :

  - use the value "default" for the defautl rule-set (all the rules enabled by default in the application); this excludes rules that take a long time to execute, and rules that are usually not needed;

  - otherwise, use a comma-separated list of rule ids, e.g. "el,ilc,ml";

  - use the value "all" to check all the rules in the application;

    e.g. [http://labs.sparna.fr/skos-testing-tool/test?url=http://publications.europa.eu/mdr/resource/authority/continent/skos/continents-skos.rdf&rules=ml,mri,ncl,oc](http://labs.sparna.fr/skos-testing-tool/test?url=http://publications.europa.eu/mdr/resource/authority/continent/skos/continents-skos.rdf&rules=ml,mri,ncl,oc)

- To customise the output report format, use either the "Accept" header, or the "format" URL parameter; if present, the "format" URL parameter takes precedence; they both can take the following values :

  - text/html for the HTML report format;

  - text/plain for the text report format;

  - application/rdf+xml for the RDF [DQV](https://www.w3.org/TR/vocab-dqv/) report format in RDF/XML;

  - text/turtle for the RDF [DQV](https://www.w3.org/TR/vocab-dqv/) report format in Turtle;

    e.g. http://labs.sparna.fr/skos-testing-tool/test?url=http://publications.europa.eu/mdr/resource/authority/continent/skos/continents-skos.rdf&format=text/turtle

- To customise the output report language, use either the "Accept-language" header or the "lang" URL parameter; if present, the "lang" URL parameter takes precedence; they can take the following valuee :

  - fr;

  - en;

  - "en" is assumed for any other value;

    e.g. http://labs.sparna.fr/skos-testing-tool/test?url=http://publications.europa.eu/mdr/resource/authority/continent/skos/continents-skos.rdf&lang=fr

### Contribution guidelines ###

* If you spot a problem or need a specific feature, please [file an issue](https://github.com/tfrancart/skos-validator/issues).

### Who are you ? ###

*  [qSKOS](https://github.com/cmader/qSKOS) is developped by Christian Mader.
*  The SKOS testing tool is developped by [Sparna](http://www.sparna.fr).

### Other

A [similar service](http://qskos.poolparty.biz) (although not as cool as this one :-) ) is operated by [PoolParty](https://www.poolparty.biz/).