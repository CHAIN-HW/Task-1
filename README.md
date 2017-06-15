# Task-1

Running CHAIN

1) Start off by navigating to spsm/s-match-source and running the following four files: 

	0cloneSMatch
	2buildSMatch
	3createBinary
	4extractBinary

Doing this should create the following folders in that directory: s-match-parent, s-match-core, s-match-io, s-match-logic, s-match-nlp, s-match-nlp-annotation, s-match-nlp-opennlp, s-match-spsm, s-match-wordnet, s-match-examples, s-match-examples, s-match-utils, s-match-spsm-prolog.


2) In the spsm-files-needed folder, there are two .java files: PrologMappingRenderer.java & Context.java, copy over these files over to the following directories (replacing old Pversions),

PrologMappingRenderer.java ->
spsm/s-match-source/s-match-spsm-prolog/src/main/java/uk/ac/hw/smatch/renderers/mapping

Context.java ->
spsm/s-match-source/s-match-core/src/main/java/it/unitn/disi/smatch/data/trees


3) Again, in the spsm-files-needed folder, there are two .jar files: s-match-core-2.0.0-SNAPSHOT.jar & s-match-spsm-prolog-2.0.0-SNAPSHOT.jar, these two files should be copied over to the following directory (replacing old versions),

spsm/s-match/lib/


4) Open up Eclipse and select File->New->Project->General->Project->Enter name for project and uncheck the ‘Use default location’ button and set the location to point to where this project was downloaded to
