rm -rf data/ && \
bin/neo4j-admin import \
--nodes=Users=import/users.csv \
--nodes=Merchants=import/merchants.csv \
--nodes=Goods=import/goods.csv \
--relationships=SENT_TO="import/transactions.csv,import/smurfingTransactions.csv,import/cycleTransactions.csv" \
--relationships=CONNECTED_TO="import/connections.csv,import/cycleTransactionsConnections.csv" \
--relationships=OWNED_BY="import/goodsownership.csv,import/biasedgoodownership.csv" \
--relationships=REVIEWED_TO="import/goodreviews.csv,import/biasedgoodreviews.csv" \
--skip-bad-relationships \
&& ./bin/neo4j console