var router     = require("express").Router();
// var middleware = require("../middleware/index.js"); 

router.get("/", (req, res)=>{
	res.send(`Authenticated User<br />

			<a href="/profile">View Profile</a>
			<br />
			
			<a href="/auth/logout">Logout</a>

		`);
});

module.exports = router;