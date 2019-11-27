MATCH (u1:User)-[t1:Transaction]->(pm:User)-[t2:Transaction]->(u2:User)
WITH u1 as sender, u2 as receiver, collect(pm) as pms
WHERE size(pms) >= 9
RETURN sender, receiver, pms