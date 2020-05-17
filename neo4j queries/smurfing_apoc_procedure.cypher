CALL apoc.trigger.add('detect_smurfing',
'UNWIND $createdRelationships AS tr
WITH endNode(tr) AS receiver
MATCH (sender:User)-[:SENT_TO]->(pm:User)-[:SENT_TO]->(receiver)
WITH sender, receiver, count(pm) AS pms
WHERE pms >= 3
SET receiver.smurfingSuspicious = true',
{phase:'before'})