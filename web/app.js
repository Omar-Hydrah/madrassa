var express = require("express");
var path    = require("path");
var app     = express();
var morgan  = require("morgan");
var mongoose     = require("mongoose");
var bodyParser = require("body-parser");
var cookieParser = require("cookie-parser");


app.set("view engine", "ejs");
app.use(express.static(path.resolve(__dirname, "public")));
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.use(cookieParser("happyflappycat"));
app.use(morgan("dev"));

app.get("/", (req, res)=>{
	res.send("Welcome to our website.");
});

var port = process.env.PORT || 80;

app.listen(port, ()=>{
	console.log(`Listening on port 80`);
});	
