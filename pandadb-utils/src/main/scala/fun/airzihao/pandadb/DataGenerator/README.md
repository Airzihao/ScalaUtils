This util is used to generate


Run the main func of fun.airzihao.pandadb.DataGenerator.GraphGenerator

Note: Modify the nodeCount and target file path.

```
To run the GraphGenerator on your server, follow the below instructions:
1. mvn package -Dmaven.test.skip=true 
2. copy the $Root/pandadb-utils/target/pandadb-utils-1.0-SNAPSHOT.jar to your server path.
3. java -jar pandadb-utils-1.0-SNAPSHOT.jar $arg0 $arg1 $arg2
```
Note:
1. The arg0 is the total node count you want to get.
2. For example, if the arg0 is 10000, you will get 10000 nodes and 19996 relations.
3. The arg1 is the export node-file path, the arg2 is the export relation-file path.
4. No need to create the file before running the GraphGenerator.


The head files of node and relation are under `src/main/resources` path of this module.
If you want to import the data into neo4j, use `relHead-neo4j.csv` as the head file.