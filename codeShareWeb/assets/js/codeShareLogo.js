window.requestAnimFrame = (function (callback) {
    return window.requestAnimationFrame ||
        window.webkitRequestAnimationFrame ||
        window.mozRequestAnimationFrame ||
        window.oRequestAnimationFrame ||
        window.msRequestAnimationFrame ||
        function (callback) {
            window.setTimeout(callback, 1000 / 60);
        };
})();

var canvas, c;
window.addEventListener('load', function () {
    canvas = document.getElementById('canvas3');
    c = canvas.getContext('2d');
    words = canvas.getContext('2d');
    window.requestAnimationFrame(drawLoop);

}, false);

function drawLoop() {
    console.log(cswLogo.x);
    cswLogo.x += 1.2;  //speed of everything
    c.clearRect(0, 0, canvas.width, canvas.height);

    if (cswLogo.x < 60){
        words.fillStyle = 'white';
        words.fillText("> _", 0, 50);
    }
    else if (cswLogo.x > 60 && cswLogo.x < 120){
        words.fillText(">  ", 0, 50);
    }
    else if (cswLogo.x > 120 && cswLogo.x < 180){
        words.fillText("> _", 0, 50);
    }
    else if (cswLogo.x > 180 && cswLogo.x < 240){
        words.fillText("> ", 0, 50);
    }
    else if (cswLogo.x > 240 && cswLogo.x < 255){ //240 boom off
        words.fillText("> c_", 0, 50);
    }
    else if (cswLogo.x > 255 && cswLogo.x < 277){
        words.fillText("> co_", 0, 50);
    }
    else if (cswLogo.x > 277 && cswLogo.x < 305){
        words.fillText("> cod_", 0, 50);
    }
    else if (cswLogo.x > 305 && cswLogo.x < 310){
        words.fillText("> code_", 0, 50);
    }
    else if (cswLogo.x > 310 && cswLogo.x < 330){
        words.fillText("> codeS_", 0, 50);
    }
    else if (cswLogo.x > 330 && cswLogo.x < 364){
        words.fillText("> codeSh_", 0, 50);
    }
    else if (cswLogo.x > 364 && cswLogo.x < 385){
        words.fillText("> codeSha_", 0, 50);
    }
    else if (cswLogo.x > 385 && cswLogo.x < 390){
        words.fillText("> codeShar_", 0, 50);
    }
    else if (cswLogo.x > 390 && cswLogo.x < 420){
        words.fillText("> codeShare_", 0, 50);
    }
    else if (cswLogo.x > 420 && cswLogo.x < 435){
        words.fillText("> codeShareW_", 0, 50);
    }
    else if (cswLogo.x > 435 && cswLogo.x < 465){
        words.fillText("> codeShareWe_", 0, 50);
    }
    else if (cswLogo.x > 465 && cswLogo.x < 470){
        words.fillText("> codeShareWev_", 0, 50);
    }
    else if (cswLogo.x > 470 && cswLogo.x < 490){
        words.fillText("> codeShareWev._", 0, 50);
    }
    else if (cswLogo.x > 490 && cswLogo.x < 520){
        words.fillText("> codeShareWev_", 0, 50);
    }
    else if (cswLogo.x > 520 && cswLogo.x < 540){
        words.fillText("> codeShareWe_", 0, 50);
    }
    else if (cswLogo.x > 540 && cswLogo.x < 560){
        words.fillText("> codeShareWeb_", 0, 50);
    }
    else if (cswLogo.x > 560 && cswLogo.x < 580){
        words.fillText("> codeShareWeb._", 0, 50);
    }
    else if (cswLogo.x > 580 && cswLogo.x < 600){
        words.fillText("> codeShareWeb.c_", 0, 50);
    }
    else if (cswLogo.x > 600 && cswLogo.x < 620){
        words.fillText("> codeShareWeb.co_", 0, 50);
    }
    else if (cswLogo.x > 620 && cswLogo.x < 640){
        words.fillText("> codeShareWeb.com_", 0, 50);
    }
    else if (cswLogo.x > 640 && cswLogo.x < 660){
        words.fillText("> codeShareWeb.com(_", 0, 50);
    }
    else if (cswLogo.x > 660 && cswLogo.x < 680){
        words.fillText("> codeShareWeb.com()_", 0, 50);
    }
    else if (cswLogo.x > 680 && cswLogo.x < 700){
        words.fillText("> codeShareWeb.com(){_", 0, 50);
    }
    else if (cswLogo.x > 700 && cswLogo.x < 720){
        words.fillText("> codeShareWeb.com(){}_", 0, 50);
    }
    else if (cswLogo.x > 720 && cswLogo.x < 740){
        words.fillText("> codeShareWeb.com(){} ", 0, 50);
    }
    else if (cswLogo.x > 740 && cswLogo.x < 800){
        words.fillText("> codeShareWeb.com(){}_", 0, 50);
    }
    else if (cswLogo.x > 800 && cswLogo.x < 850){
        words.fillText("> codeShareWeb.com(){} ", 0, 50);
    }
    else if (cswLogo.x > 850 && cswLogo.x < 900){
        words.fillText("> codeShareWeb.com(){}_", 0, 50);
    }
    else if (cswLogo.x > 900 && cswLogo.x < 950){
        words.fillText("> codeShareWeb.com(){} ", 0, 50);
    }
    else if (cswLogo.x > 950 && cswLogo.x < 1100){
        words.fillText("> codeShareWeb.com(){}_", 0, 50);
    }
    else if (cswLogo.x > 1100){
        cswLogo.x = 0;
    }
    cswLogo.draw();

    words.font = '40pt Calibri';

    window.requestAnimationFrame(drawLoop);
}
var cswLogo = {
    x: 0,
    draw: function () {
    }
};