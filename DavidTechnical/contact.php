<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>DavidTechnical.com</title>
    <meta name="keywords" content="Contact Jacksonville area landlord forum and appliance repair question and repairs area" />
    <meta name="description" content="Contact Jacksonville area landlord forum and appliance repair question and repairs area" />
    <script type="text/javascript" src="jquery/cufon-yui.js"></script>
    <script type="text/javascript" src="jquery/Book_Antiqua_400.font.js"></script>
    <script type="text/javascript">
        Cufon.replace ('h1')('h2')('h3')('h4')('#logo a')('#buttons a')('#box2_all .box_left');
    </script>
    <link href="styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="main">
    <!-- header begins -->
    <div id="header">
        <div id="logo">
            <a href="#">DavidTechnical.com</a>
            <h2><a href="#">To help move you forward!</a></h2>
        </div>
        <div id="buttons">
            <a href="index.html" class="but"  title="">Home</a><div class="but_razd"></div>
            <a href="forum/index.php"  class="but" title="">Forum</a><div class="but_razd"></div>
            <a href="about.html" class="but" title="">About Me</a><div class="but_razd"></div>
            <a href="contact.php" class="but" title="">Contact&nbsp;</a>
        </div>
    </div>
    <!-- header ends -->

    <?php
    $num1 = rand(0,5);
    $num2 = rand(0,5);
    $final = $num1 + $num2;
    ?>
    <!-- content -->
    <div class="content_top" ></div>
    <div id="content">
        <div id="contact">
            <div id="left">
                <h3>Contact</h3>
                <div id="box_form">
                    <form method="post" action="php/sendMessage.php">
                        <fieldset>
                            <div class="form_line"><input type="text" class="input" name="your_name" placeholder="Name:" required/></div>
                            <div class="form_line"><input class="input" type="email" name="your_email" placeholder="E-mail:" required/></div>
                            <div class="form_line"><input class="input" type="number" name="user_answer" placeholder="To prevent spam please add <?php echo "$num1 + $num2 = ?" ?>" required/></div>
                            <div class="form_line"><textarea placeholder="Message:"  cols="0" rows="0" name='your_message' required></textarea></div>
                            <input type="hidden" name="answer" value="<?php echo "$final";?>"/>
                            <div class="form_line" style="padding-top: 5px;">
                                <input type="submit" class="but_submit" value="Send" />
                            </div>
                        </fieldset>
                    </form>
                    <?php
                    $message = $_GET['message'];
                    if ($message==1){
                        echo "<font size=\"5\" color=\"red\"><br><p style=\"padding: 10px 0 10px 0;\"><b>Your message has been sent!</b></p></font>";
                    }
                    if ($message==2){
                        echo "<font size=\"5\" color=\"red\"><br><p style=\"padding: 10px 0 10px 0;\"><b>Please add numbers again</b></p></font>";
                    }
                    ?>
                </div>
            </div>
            <div id="right">
                <h3>E-mail</h3>
                <strong><a href="mailto:admin@davidtechnical.com">admin@davidtechnical.com</a></strong>
                <h3>My Partners</h3>
                <ul class="list_index">
                    <li><a href="http://expertappliance.org">Expert Appliance Repair Service</a></li>
                    <li><a href="http://codeShareWeb.com">codeShareWeb</a></li>
                </ul>
            </div>
            <div class="clear" style="height: 20px;"></div>
        </div>
    </div>
    <div class="content_bot" ></div>
    <div id="footer">
        <div id="footer_box2">
            <a href="https://facebook.com/davidhughes84"><img src="images/f1.png" class="footer_img" alt="" /></a>
        </div>
    </div>
</div>
</body>
</html>
