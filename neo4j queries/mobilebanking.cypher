MATCH (u1:Users)-[:TRANSACTION]->(m1:Users)-[:TRANSACTION]->(m2:Users)-[:TRANSACTION]->(m3:Users)-[:TRANSACTION]->(m4:Users)-[:TRANSACTION]->(u2:Users)-[c:CONNECTION]-(u1)
RETURN u1, m1, m2, m3, m4, u2, c
  LIMIT 10