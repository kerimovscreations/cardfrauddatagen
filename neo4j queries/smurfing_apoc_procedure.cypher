CALL apoc.trigger.add('detect_smurfing','UNWIND $createdRelationships AS tr
WITH tr
MATCH (:Users)-[tr]->(receiver:Users)
WITH receiver
MATCH (sender:Users)-[:TRANSACTION]->(pm:Users)-[:TRANSACTION]->(receiver)
WITH sender, receiver, collect(pm) AS pms
WHERE size(pms) >= 3
SET receiver.suspicious = true',
{phase:'before'})