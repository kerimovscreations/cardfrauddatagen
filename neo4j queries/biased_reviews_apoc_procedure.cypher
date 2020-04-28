CALL apoc.trigger.add('detect_biased_reviews','UNWIND $createdRelationships AS rev
WITH rev
MATCH (sender:User)-[rev:REVIEWED_TO]->(good:Good)
WITH good
MATCH (good)-[:OWNED_BY]->(m:Merchant)
WITH m
MATCH (u:User)-[r:REVIEWED_TO]->(g:Good)-[o:OWNED_BY]->(m)
WITH u, m, collect(r) as reviews, collect(g) as goods
  WHERE size(reviews) >= 4
UNWIND reviews as revList
WITH avg(revList.Rating) as avgRating, u, m, goods, reviews
  WHERE avgRating >= 4
SET m.biasedReviewsSuspicious = true
SET u.biasedReviewsSuspicious = true
',
{phase:'before'})