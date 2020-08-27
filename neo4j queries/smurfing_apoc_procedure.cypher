CALL apoc.trigger.add('detect_smurfing',
'UNWIND $createdRelationships AS tr
WITH endNode(tr) AS receiver
MATCH (sender:Users)-[:SENT_TO]->(pm:Users)-[:SENT_TO]->(receiver)
WITH sender, receiver, count(pm) AS pms
WHERE pms >= 3
SET receiver.smurfingSuspicious = true',
{phase:'before'})