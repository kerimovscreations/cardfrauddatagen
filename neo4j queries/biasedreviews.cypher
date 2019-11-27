MATCH (u:User)-[r:Review]->(g:Good)-[o:Ownership]->(m:Merchant)
WITH u, m, collect(r) as reviews
WHERE size(reviews) >= 4
UNWIND reviews as revList
WITH avg(revList.rating) as avgRating, u, m, reviews
WHERE avgRating >= 4
RETURN u, m, reviews