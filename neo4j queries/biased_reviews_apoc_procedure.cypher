CALL apoc.trigger.add('detect_biased_reviews','UNWIND $createdRelationships AS rev
WITH rev
MATCH (sender:Users)-[rev:REVIEW_IN]->(good:Goods)
WITH good
MATCH (good)-[:OWNERSHIP]->(m:Merchants)
WITH m
MATCH (u:Users)-[r:REVIEW_IN]->(g:Goods)-[o:OWNERSHIP]->(m)
WITH u, m, collect(r) as reviews, collect(g) as goods
  WHERE size(reviews) >= 4
UNWIND reviews as revList
WITH avg(toInteger(revList.Rating)) as avgRating, u, m, goods, reviews
  WHERE avgRating >= 4
SET m.suspicious = true
SET u.suspicious = true
',
{phase:'before'})