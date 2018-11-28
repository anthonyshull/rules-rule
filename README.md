# Rules Rule!

Nearly all modern applications must ingest data from the outside world.
Ingestion's goal is to transform data in a source format (XML, CSV, DAT) into something meaningful so that we can store it for our application's use.
There are roughly three phases: Extract, Transform, Load.

```
source format -> source value -> **rules** -> application value
```

In the extraction phase we go from source format -> source value.
For example, we take an XML node representation and turn it into a [map](https://clojure.org/reference/data_structures#Maps).

In the transformation phase we apply **rules** to bring the values we extracted in line with our application logic.
We take a source value -> **rules** -> application value.
For example, we might transpose two values according to some business rule.

In the load phase we store the value in one or more datastores.

## Extract

In the extraction phase, we have two somewhat related issues that we commonly have to tackle:

+ Disparate representation formats (CSV, JSON, XML)
+ Representations that change over time

In addressing the first issue we want to:

+ Build a library of re-usable functions to abtract away common operations like fetching a file from S3 or converting a CSV row to a map
+ Write source-specific transformation functions

Extracting a single source is a functional pipeline composing our re-usable functions and our source-specific functions.

Representations change over time.
For example, the structure of a single source CSV can change header names.
We can overcome this issue with [polymorphic](https://en.wikipedia.org/wiki/Polymorphism_(computer_science)) functions.
In this project, we do so using [multimethods](https://clojure.org/reference/multimethods).
But, you can also use [protocols](https://clojure.org/reference/protocols).

The culmination of extraction is a source value.
We use [spec](https://clojure.org/guides/spec) to make sure that our value is in the correct shape.

## Transform

In the transform phase, we apply various rules to our source value to turn it into an application value.
We want to avoid a tangled web of switch and if/else statements throughout our code.
Therefore, we use a rules engine [Clara](http://www.clara-rules.org/) to separate business logic from the rest of the application.

Running a rules engine includes four steps:

1. Define rules `(def-rule switch-locations ...)`
2. Create a rules session `(mk-session 'rules-rule.transform)`
3. Insert facts `(insert (->Movement ...) (->Movement ...))` or `(insert-all movements)`
4. Fire the rules `(fire-rules)`

Firing rules is not a pure process; when each rule fires you have to mutate a value for later retrieval.
We model state with an [atom](https://clojure.org/reference/atoms), but there are other options in Clojure: vars, refs, agents.

The interesting thing is that by shunting the rules engine into it's own part of the application we can wall-off state manipulation.

In our transformation we want to expose a single function as an interface that takes our value(s) and returns us the transformed version(s).

## Load

Pretty self-explanatory.
Save the data to one or more datastores.
Here, we just print the results to stdout.
But, even something so simple can be used for great good.
Check out [kafkacat](https://github.com/edenhill/kafkacat), a Kafka command-line application that can read from stdin and write to a topic.

## Usage

**dependencies**

+ Install [Java](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-18-04)
+ Install [Clojure](https://clojure.org/guides/getting_started#_installation_on_linux)
+ Install [Leiningen](https://leiningen.org/)

**getting started**
```
%> git clone git@github.com:anthonyshull/rules-rule.git
%> cd rules-rule && lein deps
```
**run**
```
%> lein run "data/october.csv"
```

**test**
```
%> lein test
```

**generate documentation**
```
%> lein codox
```

**lint**
```
%> lein eastwood
```

**compile**
```
%> lein uberjar
```

**run compiled**
```
%> java -jar target/rules_rule-0.0.1-standalone.jar "data/november.csv"
```
