CALL apoc.trigger.add('detect_smurfing','UNWIND $createdRelationships AS tr
WITH tr
MATCH (:User)-[tr]->(receiver:User)
WITH receiver
MATCH (sender:User)-[:SENT_TO]->(pm:User)-[:SENT_TO]->(receiver)
WITH sender, receiver, collect(pm) AS pms
WHERE size(pms) >= 3
SET receiver.smurfingSuspicious = true',
{phase:'before'})