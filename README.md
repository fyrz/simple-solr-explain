# simple-solr-explain
[![Build Status](https://travis-ci.org/fyrz/simple-solr-explain.png)](https://travis-ci.org/fyrz/simple-solr-explain) [![Coverage Status](https://coveralls.io/repos/fyrz/simple-solr-explain/badge.svg?branch=master)](https://coveralls.io/r/fyrz/simple-solr-explain?branch=master)

## Motivation
This project aims to provide Solr query explanations to a broader audience. In former and current projects as well as by experience debug information available via standard Solr facilities are too detailed and too technical to be easily understandable.

## Limitations
Currently this is only working and tested for explain output produced by EDismaxQParser.

## Usage
Simple solr explanations can be integrated by configuring it as a custom Solr QueryResponseParserPlugin or by using standalone with API calls.

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
      "score": 0.4868159,
      "fieldMatches": [
        {
          "score": 0.47311547,
          "matchDescription": "title:quick^10.0"
        },
        {
          "score": 0.009133612,
          "matchDescription": "description:brown^0.2"
        },
        {
          "score": 0.009133612,
          "matchDescription": "description:quick^0.2"
        }
      ]
    },
    {
      "documentId": "4",
      "score": 0.1892462,
      "fieldMatches": [
        {
          "score": 0.3784924,
          "matchDescription": "title:quick^10.0"
        }
      ]
    }
  ]
}
```
