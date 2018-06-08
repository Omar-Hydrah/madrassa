var router     = require("express").Router();
// var middleware = require("../middleware/index.js"); 

router.get("/", (req, res)=>{
	res.render("home/index");
});

module.exports = router;