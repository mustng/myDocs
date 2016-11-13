<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
    <title>Expert Appliance Repair Services, LLC Contact</title>
    <meta name="description" content="Contact Expert Appliance Repair. Your source for appliance parts and service in Jacksonville
    ,Fl and the surrounding area. We strive to providing service to people, not just machines" />
    <meta name="keywords" content="appliance service Jacksonville, appliance service St. Augustine, appliance service Jacksonville Beach, appliance service Orange Park, appliance service keystone heights, appliance service Middleburg, appliance service St. Johns,  appliance service Ponte Vedra beach,   appliance service Fernandina beach, appliance service green cove springs, appliance repair service Jacksonville, appliance repair service St. Augustine, appliance repair service Jacksonville Beach, appliance repair service Orange Park, appliance repair service keystone heights, appliance repair service Middleburg, appliance repair service St. Johns,  appliance repair service Ponte Vedra beach,   appliance repair service Fernandina beach, appliance repair service green cove springs, whirlpool repairs for local area, Frigidaire repairs for local area, Bosch repairs for local area, LG repairs for local area, Samsung repairs for local area, GE repairs for local area, expert appliances service, whirlpool appliance repair service near me, GE appliance repair service near me, Frigidaire appliance repair service near me, LG appliance repair service near me, Samsung appliance repair service near me, sears appliance service repair Kenmore appliances, appliances repairs, appliance parts Jacksonville, appliance parts St. Augustine, appliance parts Jacksonville Beach, appliance parts Orange Park, appliance parts keystone heights, appliance parts Middleburg, appliance parts St. Johns,  appliance parts Ponte Vedra beach, appliance parts Fernandina beach, appliance parts green cove springs, appliance repair in Jacksonville, appliance repair in St. Augustine, appliance repair in Jacksonville Beach, appliance repair in Orange Park, appliance repair in keystone heights, appliance repair in Middleburg, appliance repair in St. Johns, appliance repair in Ponte Vedra beach, appliance repair in Fernandina beach, appliance repair in green cove springs, sears, appliances, sears appliance repair service jacksonville, sears repairs appliances, kenmore refrigerator repair service, sears parts and repair center, sears appliances repair service phone number, sears dryer service, kenmore appliance repair jacksonville, kenmore part, kenmore washing machine parts, sears parts direct, kenmore dishwasher parts, sears refrigerator, sale kenmore,  refrigerator parts" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easing.min.js"></script>
    <script type="text/javascript" src="js/jquery.lavalamp.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#lava_menu").lavaLamp({
                fx: "backout",
                speed: 700
            });
        });
    </script>
    <script type="text/javascript" src="js/jquery.nivo.slider.pack.js"></script>
    <script type="text/javascript">
        $(window).load(function() {
            $('#slider').nivoSlider();
        });
    </script>

</head>

<body>
<div id="main">
    <div id="site_content">
        <div id="menubar">
            <ul class="lavaLampWithImage" id="lava_menu">
                <li><a href="index.html">Home&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a href="aboutus.html">About Us&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a href="http://expertappliancerepairparts.partsquik.com" target="_blank">Parts&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a href="service.html">Service&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a href="testimonials.html">Testimonials&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li class="current"><a href="contact.php">Contact Us&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
            </ul>
        </div><!--close menubar-->
        <div id="header">
            <h1>Expert <span>Appliance Repair Services, LLC</span></h1>
        </div><!--close header-->
        <div id="banner_image">
            <div id="slider-wrapper">
                <div id="slider" class="nivoSlider">
                    <img src="images/slide1.jpg" alt="" />
                    <img src="images/slide2.jpg" alt="" />
                    <img src="images/slide3.jpg" alt="" />
                </div><!--close slider-->
            </div><!--close slider-wrapper-->
        </div><!--close banner_image-->
        <?php
        $num1 = rand(0,5);
        $num2 = rand(0,5);
        $final = $num1 + $num2;
        ?>
        <div id="content">
            <div class="clear"></div>
            <div class="content_item">
                <?php
                $message = $_GET['message'];
                if ($message==1){
                    echo "<font size=\"5\" color=\"red\"><br><p style=\"padding: 10px 0 10px 0;\"><b>Your message has been sent!</b></p></font>";
                }
                if ($message==2){
                    echo "<font size=\"5\" color=\"red\"><br><p style=\"padding: 10px 0 10px 0;\"><b>Please add numbers again</b></p></font>";
                }
                ?>
                <h2>Contact Us</h2>
                <form method="post" action="php/sendMessage.php">
                    <div style="width:170px; float:left;"><p>Name</p></div>
                    <div style="width:430px; float:right;"><p><input type="text" name="your_name" value="" required/></p></div>
                    <br style="clear:both;" />
                    <div style="width:170px; float:left;"><p>Email Address</p></div>
                    <div style="width:430px; float:right;"><p><input type="email" name="your_email" value="" required/></p></div>
                    <br style="clear:both;" />
                    <div style="width:170px; float:left;"><p>Message</p></div>
                    <div style="width:430px; float:right;"><p><textarea rows="8" cols="50" name='your_message' required></textarea></p></div>
                    <br style="clear:both;" />
                    <p style="padding: 10px 0 10px 0;">Please enter the answer to this simple math question (to prevent spam)</p>
                    <div style="width:170px; float:left;"><strong><p>Math Question:<?php echo "$num1 + $num2 = ?";?></p></strong></div>
                    <div style="width:430px; float:right;"><p><input type="number" name="user_answer" required><input type="hidden" name="answer" value="<?php echo "$final";?>" /></p></div>
                    <div style="width:430px; float:right;"><p style="padding-top: 15px"><input class="submit" type="submit" value="Send" /></p></div>
                </form>
            </div><!--close content_item-->

            <div class="sidebar_container">
                <div class="sidebar">
                    <div class="sidebar_item">
                        <p><strong><a href="mailto:contact@ExpertAppliance.org">contact@ExpertAppliance.org</a></p><p>Phone- (904)213-9401</p><p>Fax- (904)213-9402
                        </strong></p>
                    </div><!--close sidebar_item-->
                </div><!--close sidebar-->
                <div class="sidebar">
                    <div class="sidebar_item">
                        <div style="width:170px; float:left;"><strong><p>Location: 1031 Blanding Blvd. Unit 404 Orange Park, Fl 32065<iframe
                                    width="300"
                                    height="160"
                                    frameborder="0" style="border:0"
                                    src="https://www.google.com/maps/embed/v1/place?1031 Blanding Blvd Unit 404, Florida, Orange Park" allowfullscreen>
                                </iframe></p></strong>
                        </div>
                    </div><!--close content_item-->
                </div><!--close sidebar-->
                <div class="sidebar">
                    <div class="sidebar_item">
                        <p><!-- Facebook Badge START --><a href="http://www.facebook.com/pages/Expert-Appliance-Repair-Services-LLC/191366174239801" target="_TOP" style="font-family: &quot;lucida grande&quot;,tahoma,verdana,arial,sans-serif; font-size: 11px; font-variant: normal; font-style: normal; font-weight: normal; color: #3B5998; text-decoration: none;" title="Expert Appliance Repair Services, LLC">Expert Appliance Repair Services, LLC</a><br/><a href="http://www.facebook.com/pages/Expert-Appliance-Repair-Services-LLC/191366174239801" target="_TOP" title="Expert Appliance Repair Services, LLC"><img src="http://badge.facebook.com/badge/191366174239801.1814.648279936.png" width="120" height="227" style="border: 0px;" /></a><br/><!-- Facebook Badge END -->
                        </p>
                    </div><!--close sidebar_item-->
                </div><!--close sidebar-->
            </div><!--close sidebar_container-->
            <br style="clear:both;" />
        </div><!--close content-->
    </div><!--close site_content-->
    <div id="footer">
        Expert Appliance Repair Service LLC | <a href="http://maps.google.com/?q=1031 Blanding Blvd, Florida, Orange Park">1031 Blanding Blvd. Unit 404 Orange Park, Fl 32065</a> | <a href="mailto:contact@ExpertAppliance.org">contact@ExpertAppliance.org</a> | (904)213-9401
    </div><!--close footer-->
</div><!--close main-->
</body>
</html>