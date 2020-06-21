rm -rf data/databases/graph.db && \
bin/neo4j-admin import \
--nodes=Users=import/users.csv \
--nodes=Merchants=import/merchants.csv \
--nodes=Goods=import/goods.csv \
--relationships=Transaction="import/transactions.csv,import/smurfingTransactions.csv,import/cycleTransactions.csv" \
--relationships=Connection="import/connections.csv,import/cycleTransactionsConnections.csv" \
--relationships=Ownership="import/goodsownership.csv,import/biasedgoodownership.csv" \
--relationships=Review="import/goodreviews.csv,import/biasedgoodreviews.csv" \
--skip-bad-relationships \
&& ./bin/neo4j console