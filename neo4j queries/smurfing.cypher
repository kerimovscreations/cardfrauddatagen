MATCH (u1:Users)-[t1:SENT_TO]->(pm:Users)-[t2:SENT_TO]->(u2:Users)
WITH u1 AS sender, u2 AS receiver, collect(pm) AS pms
  WHERE size(pms) >= 3
RETURN sender, receiver, pms
  LIMIT 10