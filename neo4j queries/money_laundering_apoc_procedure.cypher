CALL apoc.trigger.add('detect_money_laundering','UNWIND $createdRelationships AS tr
WITH tr
WITH startNode(tr) as m4, endNode(tr) AS receiver
WITH receiver, m4
MATCH (sender:User)-[:SENT_TO]->(m1:User)-[:SENT_TO]->(m2:User)-[:SENT_TO]->(m3:User)-[:SENT_TO]->(m4)-[:SENT_TO]->(receiver)-[c:CONNECTED_TO]-(sender)
SET sender.moneyLaunderingSuspicious = true
SET receiver.moneyLaunderingSuspicious = true',
{phase:'before'})