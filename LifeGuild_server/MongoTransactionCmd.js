db.getCollection("transactions").find({})

db.transactions.insert({
	txnId: "0000001",
	txnType: "buy pet",
	petname:"Abyss",
	fromUserId: "00000001",
	fromPetsId: "00000001",
	amountSpent: 10,
	timestamp: "2020-09-23 00:00:00"
	});
	
db.transactions.deleteMany({
})