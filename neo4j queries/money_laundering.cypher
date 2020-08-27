MATCH (u1:Users)-[:SENT_TO]->(m1:Users)-[:SENT_TO]->(m2:Users)-[:SENT_TO]->(m3:Users)-[:SENT_TO]->(m4:Users)-[:SENT_TO]->(u2:Users)-[c:CONNECTED_TO]-(u1)
RETURN u1, m1, m2, m3, m4, u2, c
  LIMIT 10