<?php session_start();
if ($_SESSION['username'] == ""){
	include '../assets/html/navbarNotLoggedIn.html';
}
else{
	include '../assets/html/navbar.html';
}

?>
<html>
<head>
	<title>About Us</title>

</head>
<body>
	<div class="container-fluid">
		<div class="col-md-12">
			<h3 class="color-gray">Vision Statement</h3>
			<p class="custom-font-size">The Vision of CodeShareWeb.com is to promote collaboration between university students in computer science related fields.</p>
		</div>
		<div class="col-md-12">
			<h3 class="color-gray">Mission Statement</h3>
			<p class="custom-font-size">Our mission is to create a collaborative online space for students to share coding projects with the purpose of enhancing programming understanding and promoting educational achievement.</p>
		</div>
		<div class="col-md-12">
			<h3 class="color-gray">Staff Biographies</h3>
		</div>
		<div class="row">
			<div class="col-md-4 pull-left">
				<img src="../assets/pictures/charlotte-3.jpg" alt="Image of Charlotte Morrison"><br><a href="mailto:admin@codeshareweb.com?Subject=Code%Share%Web" target="_top">Contact Charlotte</a>
			</div>
			<div class="col-md-8">
				<h3>Charlotte Morrison</h3>
				<p>
					Charlotte Morrison is the Career and Technical Education Department Head at Mandarin High school in charge of promoting vocational educational programs and organizing the department as a collaborative problem solving team.  Her instructional duties include teaching upper level computing classes in Web Design and User Interface to high school students leading to industry certifications in these areas.  In addition, she holds the school role of Professional Development Facilitator.  In this capacity she runs the new teacher induction program and manages the data for the Professional Learning Communities.  She holds a BA in Political Science from Florida International University, and is currently working on a BS in Information Systems from University of North Florida.
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 pull-right">
				<img src="../assets/pictures/griffinSuparto.png" alt="Image of Griffin Suparto" class="image"><br>
				<a href="mailto:admin99@codeshareweb.com?Subject=CodeShareWeb" target="_top">Contact Griffin</a><br>
				<a href="http://suparto.com" target="_top">View Website</a><br>
			</div>
			<div class="col-md-8">
				<h3>Griffin Suparto</h3>
				<p>
					Griffin Suparto has worked on multiple software development projects including extending the functionality of Adroid applications, improving database efficiency, web development and optimization, and e-commerce applications.  Griffin has created software that analyzes security logs and detects intrusions and unusual behavior with an interface that provides dynamically created charts, graphs, etc. to promote a user-friendly design.  He is fluent in multiple programming languages including Java, C#, SQL, PHP, HTML, CSS, JavaScript, C, and Python, and he loves learning new languages and frameworks.  Griffin is currently in his senior year at the University of North Florida studying Computer Science, and has an internship at Greenshades Software.
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 pull-left">
				<img src="../assets/pictures/davidHughes.jpg" alt="Image of David Hughes"><br><a href="mailto:david@codeshareweb.com?Subject=Code%Share%Web" target="_top">Contact David</a>
			</div>
			<div class="col-md-8">
				<h3>David Hughes</h3>
				<p>
					David is studying for his Bachelor's Degree in Computer Science and hopes to graduate by the end of Fall 2016. He enjoys all areas of computing and makes/solders devices to practice his hardware skills and then programs the devices to study the software end. David also has multiple rental properties that he owns. He manages the repairs, payments and tenant occupancy, which has taught him many life lessons. He has been engaged for over 8 years to a wonderful and understanding fiance and hopes to tie the knot at the completion of his schooling. For fun he scuba dives, rock climbs, plays his drum set, or goes for a leisure motorcycle ride.
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 pull-right">
				<img src="../assets/pictures/marco.jpg" alt="Image of Marco Del Castillo" class="image"><br><a href="mailto:marco@codeshareweb.com?Subject=Code%Share%Web" target="_top">Contact Marco</a>
			</div>
			<div class="col-md-8">
				<h3>Marco Del Castillo</h3>
				<p>
					Marco del Castillo was born and raised in the Philippines. In 2010, Marco with his family moved to Orange Park, Florida to pursue better opportunities. Marco is currently attending the University of North Florida studying Computer Science. He expects to graduate after Fall or Spring of 2017. Marco is currently working as a Help Desk technician at UNF. He is responsible for fixing university, faculty, and student computers as well as assisting students and faculty with any technical problems. He hopes to one day be able to travel the world to explore many different cultures.
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 pull-left">
				<img src="../assets/pictures/johnAlongi.jpg" alt="Image of John Alongi"><br><a href="mailto:john@codeshareweb.com?Subject=Code%Share%Web" target="_top">Contact John</a>
			</div>
			<div class="col-md-8">
				<h3>John Alongi</h3>
				<p>
					John is currently in his senior year pursuing a degree in Computing and Information Sciences with a concentration in Information Technology. During this summer, he will be attending an internship with Jabil Circuit loacted in St. Petersburg, Florida. This opportunity will allow him to gain real-world experience on how a worldwide business operates its Information Technology department.
				</p>
			</div>
		</div>
	</div>
</body>
</html>
