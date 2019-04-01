<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>Lost in the Clouds - Error ${status}</title>
    <meta name="description" content="Simple and aesthetic template with animated background">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" type="text/css" href="http://47.107.38.165:8081/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="http://47.107.38.165:8081/css/error.css" />
    <link rel="stylesheet" type="text/css" href="http://47.107.38.165:8081/css/Inconsolata.css" />
    <link rel="stylesheet" type="text/css" href="http://47.107.38.165:8081/css/Candal.css" />
</head>

<body>
<div id="container">
    <div id="stage" class="stage">
        <div id="clouds" class="stage"></div>
    </div>

    <div id="ticket">
        <section id="ticket_left">
            <p class="text1_a">Lost in the clouds</p>
            <p class="text2_a">${error}</p>
            <p class="text3_a">Error ${status}</p>
            <p class="text4_a">Sorry!</p>
            <p class="text5_a">From</p>
            <p class="text6_a">Somewhere</p>
            <p class="text7_a">To</p>
            <p class="text8_a">Nowhere</p>
            <p class="text9_a">Seat</p>
            <p class="text10_a">${status}</p>
            <p class="text11_a">Try another flight</p>
            <nav class="text12_a">
                <ul>
                    <li><a href="/">Home</a></li>
                    <li><a href="#">About</a></li>
                    <li><a href="#">Services</a></li>
                    <li><a href="#">Blog</a></li>
                    <li><a href="#">Portfolio</a></li>
                </ul>
            </nav>
        </section>

        <section id="ticket_right">
            <p class="text1_b">First class</p>
            <p class="text2_b">Lost in the clouds</p>
            <p class="text3_b">From</p>
            <p class="text4_b">Somewhere</p>
            <p class="text5_b">To</p>
            <p class="text6_b">Nowhere</p>
            <p class="text7_b">Seat</p>
            <p class="text8_b">${status}</p>
            <p class="text9_b">1</p>
            <p class="text10_b">103076498</p>
        </section>
    </div>
</div>

<script src="http://47.107.38.165:8081/js/jquery183min.js" type="text/javascript"></script>
<script src="http://47.107.38.165:8081/js/spritely05.js" type="text/javascript"></script>

<script type="text/javascript">
    (function($) {
        $(document).ready(function() {
            $('#clouds').pan({fps: 40, speed: 0.7, dir: 'right', depth: 10});
        });
    })(jQuery);
</script>
</body>
</html>