<html>
<head>
    <title>Create terrain from heightmap</title>
    <meta charset="utf-8">
    <script src="js/three.js"></script>
    <script type="text/javascript" src="js/dat.gui.min.js"></script>
    <script type="text/javascript" src="js/chroma.min.js"></script>
    <script type="text/javascript" src="webGLTerrain.js"></script>
    <script type="text/javascript" src="websocket.js"></script>
    <style>
        body {
            background-color: bisque;
            margin: 0;
            overflow: hidden;
        }
    </style>
</head>

<body>
<input id="soundSpeed" type="number" name="t" value="1500" min="1000" max="3000" step="10">
<input type="button" value="create terrain" onclick="sendInput()">
<img id="image">

</body>
</html>
