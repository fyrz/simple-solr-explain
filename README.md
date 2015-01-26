# simple-solr-explain
[![Build Status](https://travis-ci.org/fyrz/simple-solr-explain.png)](https://travis-ci.org/fyrz/simple-solr-explain) [![Coverage Status](https://coveralls.io/repos/fyrz/simple-solr-explain/badge.svg?branch=master)](https://coveralls.io/r/fyrz/simple-solr-explain?branch=master)

## Motivation
This project aims to provide Solr query explanations to a broader audience. In former and current projects as well as by experience debug information available via standard Solr facilities are too detailed and too technical to be easily understandable.

## Limitations
- Currently this is only working and tested for explain output produced by EDismaxQParser.
- The state of this project can be considered as prototype.

## Usage
Simple-solr-explain can be configured as custom query response writer (see: https://github.com/fyrz/simple-solr-explain/blob/master/src/test/resources/solr/collection1/conf/solrconfig.xml#L588). Alternatively the API can be called from Java.

## Sample
The following output describes a JSON response of simple-solr-explain. The JSON response includes all fields searched in and every document match including field hits.

```
{
  "searchFields": [
    "description",
    "title"
  ],
  "documentMatches": [
    {
      "documentId": "1",
      "score": 0.95330393,
      "fieldMatches": [
        {
          "score": 0.94419,
          "matchDescription": "title:quick^10.0"
        },
        {
          "score": 0.018227823,
          "matchDescription": "description:quick^0.2"
        }
      ]
    },
    {
      "documentId": "4",
      "score": 0.755352,
      "fieldMatches": [
        {
          "score": 0.755352,
          "matchDescription": "title:quick^10.0"
        }
      ]
    }
  ]
}
```
