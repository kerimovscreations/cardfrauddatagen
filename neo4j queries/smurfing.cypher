MATCH (u1:Users)-[t1:TRANSACTION]->(pm:Users)-[t2:TRANSACTION]->(u2:Users)
WITH u1 AS sender, u2 AS receiver, collect(pm) AS pms
  WHERE size(pms) >= 3
RETURN sender, receiver, pms
  LIMIT 10