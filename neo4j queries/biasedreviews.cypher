MATCH (u:Users)-[r:REVIEWED_TO]->(g:Goods)-[o:OWNED_BY]->(m:Merchants)
WITH u, m, collect(r) as reviews, collect(g) as goods
  WHERE size(reviews) >= 4
UNWIND reviews as revList
WITH avg(revList.Rating) as avgRating, u, m, goods, reviews
  WHERE avgRating >= 4
RETURN u, m, goods, reviews
  LIMIT 10