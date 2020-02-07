CALL apoc.trigger.add('detect_money_laundering','UNWIND $createdRelationships AS tr
WITH tr
MATCH (m4:Users)-[tr]->(receiver:Users)
WITH receiver, m4
MATCH (sender:Users)-[:TRANSACTION]->(m1:Users)-[:TRANSACTION]->(m2:Users)-[:TRANSACTION]->(m3:Users)-[:TRANSACTION]->(m4)-[:TRANSACTION]->(receiver)-[c:CONNECTION]-(sender)
SET sender.suspicious = true
SET receiver.suspicious = true',
{phase:'before'})