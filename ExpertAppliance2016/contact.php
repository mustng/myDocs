<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Expert Appliance Repair Services, LLC</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,400italic">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cookie">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/Carousel-Hero.css">
    <link rel="stylesheet" href="assets/css/Features-Blue.css">
    <link rel="stylesheet" href="assets/css/Header-Blue.css">
    <link rel="stylesheet" href="assets/css/Pretty-Footer.css">
    <link rel="stylesheet" href="assets/css/Pretty-Header.css">
    <link rel="stylesheet" href="assets/css/user.css">
    <link rel="stylesheet" href="assets/css/Pricing-Table-Style-01.css">
    <link rel="stylesheet" href="assets/css/Testimonials.css">

    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
<nav class="navbar navbar-default custom-header">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand navbar-link" href="#"> <span>Expert </span> Appliance Repair </a>
            <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav links">
                <li role="presentation"><a href="index.html">Home </a></li>
                <li role="presentation"><a href="about.html">About Us</a></li>
                <li role="presentation"><a href="service.html">Service </a></li>
                <li role="presentation"><a href="http://expertappliancerepairparts.partsquik.com/" target="_blank">Order Parts</a></li>
                <li role="presentation"><a href="testimonials.html" class="custom-navbar">Testimonials </a></li>
                <li role="presentation"><a href="contact.php" class="custom-navbar">Contact Us</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right"></ul>
        </div>
    </div>
</nav>

<?php
$num1 = rand(0,5);
$num2 = rand(0,5);
$final = $num1 + $num2;
?>

<div class="highlight-phone">
    <div class="container">
        <div class="row">
            <div class="col-lg-5 col-lg-offset-1 col-md-6 col-md-offset-0">
                <h1><strong>Contact Us</strong></h1>
                <div></div>
                <form class="well form-horizontal" method="post" action="assets/php/sendMessage.php"  id="contact_form">
                    <fieldset>
                        <h4 class="text-left">1031 Blanding Blvd. Unit 404 Orange Park, Fl 32065</h4>
                        <h4 class="text-left"> <a href="contact@ExpertAppliance.org" target="_blank">contact@ExpertAppliance.org </a></h4>
                        <h4 class="text-left"><strong>Phone (904)213-9401 Fax (904)213-9402&nbsp;</strong></h4>
                        <!-- Text input-->

                        <div class="form-group">
                            <label class="col-md-2 control-label">Name</label>
                            <div class="col-md-10 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input  name="your_name" placeholder="Name" class="form-control"  type="text">
                                </div>
                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-2 control-label">E-Mail</label>
                            <div class="col-md-10 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                                    <input name="your_email" placeholder="E-Mail Address" class="form-control"  type="text">
                                </div>
                            </div>
                        </div>

                        <!-- Text input-->
                        <p class col-md-6> To prevent spam please add the two numbers</p>
                        <div class="form-group">
                            <label class="col-md-2 control-label"><?php echo "$num1 + $num2 =";?></label>
                            <div class="col-md-10 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-apple"></i></span>
                                    <input  name="user_answer" placeholder="" class="form-control"  type="number" required>
                                </div>

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">Message</label>
                            <div class="col-md-10 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                                    <textarea rows="4" cols="50" class="form-control" name="your_message" placeholder="Message"></textarea>
                                </div>
                            </div>
                        </div>

                        <div class="form-group has-warning">
                            <p class="form-static-control"><?php
                                $message = $_GET['message'];
                                if ($message==1){
                                    echo "<p style=\"padding: 10px 0 10px 0;\"><b>Your message has been sent!</b></p>";
                                }
                                if ($message==2){
                                    echo "<p style=\"padding: 10px 0 10px 0;\"><font color=\"red\"><b>Please add numbers again</b></font></p>";
                                }
                                ?></p>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label"></label>
                            <div class="col-md-10">
                                <button type="submit" class="btn btn-primary" >Next <span class="glyphicon glyphicon-send"></span></button>
                            </div>
                        </div>

                <p><strong> </strong></p>
            </div>
            <div class="col-lg-5 col-lg-offset-0 col-md-5 col-md-offset-1 hidden-xs hidden-sm phone-holder">
                <div class="iphone-mockup"><img src="assets/img/iphone.svg" class="device">
                    <div class="screen"></div>
                </div>
            </div>
        </div>

        <div class="form-group">

            <input type="hidden" name="answer" value="<?php echo "$final";?>" /></p></div>
    </div>
    </fieldset>
    </form>
    </div>
</div>
<iframe allowfullscreen="" frameborder="0" width="100%" height="400" src=""></iframe>
<footer>
    <div class="row">
        <div class="col-md-4 col-sm-6 footer-navigation">
            <h3> <a href="#"><span>Expert </span> Appliance Repair Services, LLC</a></h3>
            <p class="links"><a href="index.html">Home</a><strong> · </strong><a href="about.html">About </a><strong> · </strong><a href="service.html">Service </a><strong> · </strong><a href="http://expertappliancerepairparts.partsquik.com/" target="_blank">Order Parts</a><strong> · </strong>
                <a
                    href="testimonials.html">Testimonials </a><strong> · </strong><a href="contact.php">Contact Us </a></p>
            <p class="company-name">Expert Appliance Repair© 2016 </p>
        </div>
        <div class="col-md-4 col-sm-6 footer-contacts">
            <div><span class="fa fa-map-marker footer-contacts-icon"> </span>
                <p><span class="new-line-span">1031 Blanding Blvd. Unit 404&nbsp; </span> Orange Park, Fl&nbsp; 32065</p>
            </div>
            <div><i class="fa fa-phone footer-contacts-icon"></i>
                <p class="footer-center-info email text-left"> 904-213-9401</p>
            </div>
            <div><i class="fa fa-envelope footer-contacts-icon"></i>
                <p> <a href="mailto:contact@ExpertAppliance.org"><strong>contact@ExpertAppliance.org</strong></a></p>
            </div>
        </div>
        <div class="clearfix visible-sm-block"></div>
        <div class="col-md-4 footer-about">
            <h4>“Providing Service to People Not Just Machines” </h4>
            <p>Check us out on Facebook!</p>
            <div class="social-links social-icons"><a href="https://www.facebook.com/pages/Expert-Appliance-Repair-Services-LLC/191366174239801"><i class="fa fa-facebook"></i></a></div>
        </div>
    </div>
</footer>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/index.js"></script>

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

</body>

</html>