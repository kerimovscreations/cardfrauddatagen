MATCH (u1:User)-[:Transaction]->(m1:User)-[:Transaction]->(m2:User)-[:Transaction]->(m3:User)-[:Transaction]->(m4:User)-[:Transaction]->(u2:User)
WHERE (u1)-[:Connection]-(u2)
RETURN u1, m1, m2, m3, m4, u2