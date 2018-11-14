# Rules Rule!

source format -> source value -> **rules** -> application value

*NOTE*: In a real project, certain functions would have been further broken out as utils.

## Usage

**run**
```
%> lein run -m rules-rule.core "data/october.csv"
```

**test**
```
%> lein test
```

**docs**
```
%> lein codox
```

**lint**
```
%> lein eastwood
```

## Extract

In the extract phase, we attempt to take data in a source format and convert it to a source value.
E.g., we could take a specific XML document and convert it to a vector of maps.

We use [clojure.spec](https://clojure.org/guides/spec).

Our goal is to build up a library of re-usable functions to abtract away common operations like fetching a file from S3 or converting a CSV row to a map.

We have two somewhat related issues that we commonly have to tackle:

+ Disparate representation formats (csv, json, xml)
+ Representations that change over time

More interestingly, we can abstract away changes to representations over time.

We do so using [multimethods](https://clojure.org/reference/multimethods).

[Clara](http://www.clara-rules.org) uses records so we go from csv -> map -> record.

## Transform

In the transform phase, we ...

## Load

The load phase is when we store the data.
