MATCH (u1:User)-[:SENT_TO]->(m1:User)-[:SENT_TO]->(m2:User)-[:SENT_TO]->(m3:User)-[:SENT_TO]->(m4:User)-[:SENT_TO]->(u2:User)-[c:CONNECTED_TO]-(u1)
RETURN u1, m1, m2, m3, m4, u2, c
  LIMIT 10