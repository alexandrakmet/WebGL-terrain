var renderer;
var scene;
var camera;

var img = new Image();
var soundSpeed;

var control;
var scale = chroma.scale(['sienna', 'sandybrown']).domain([0, 50]);

function init() {
    console.log("init");
    scene = new THREE.Scene();
    camera = new THREE.PerspectiveCamera(50, window.innerWidth / window.innerHeight, 0.1, 10000);

    renderer = new THREE.WebGLRenderer();
    renderer.setClearColor(0xffe4c4, 1.0);
    renderer.setSize(window.innerWidth, window.innerHeight);

    var light = new THREE.DirectionalLight();
    light.position.set(1200, 1200, 1200);
    scene.add(light);

    camera.position.x = 1200;
    camera.position.y = 1000;
    camera.position.z = 1200;
    camera.lookAt(scene.position);

    document.body.appendChild(renderer.domElement);

    createGeometryFromMap();
    render();
}

function createGeometryFromMap() {
    var depth = 512;
    var width = 512;

    var spacingX = 3;
    var spacingZ = 3;
    var heightOffset = 2;

    var canvas = document.createElement('canvas');
    canvas.width = 512;
    canvas.height = 512;
    var context = canvas.getContext('2d');

   // img.src = "image.png";
    img.onload = function () {
        // draw on canvas
        context.drawImage(img, 0, 0);
        var pixel = context.getImageData(0, 0, width, depth);

        var geom = new THREE.Geometry;
        for (var x = 0; x < depth; x++) {
            for (var z = 0; z < width; z++) {
                var yValue = pixel.data[z * 4 + (depth * x * 4)] / heightOffset;
                var vertex = new THREE.Vector3(x * spacingX, 4 * yValue, z * spacingZ);
                geom.vertices.push(vertex);
            }
        }

        // we create a rectangle between four vertices, and we do
        // that as two triangles.
        for (var z = 0; z < depth - 1; z++) {
            for (var x = 0; x < width - 1; x++) {
                // we need to point to the position in the array
                // a - - b
                // |  x  |
                // c - - d
                var a = x + z * width;
                var b = (x + 1) + (z * width);
                var c = x + ((z + 1) * width);
                var d = (x + 1) + ((z + 1) * width);

                var face1 = new THREE.Face3(a, b, d);
                var face2 = new THREE.Face3(d, c, a);

                face1.color = new THREE.Color(scale(getHighPoint(geom, face1)).hex());
                face2.color = new THREE.Color(scale(getHighPoint(geom, face2)).hex())

                geom.faces.push(face1);
                geom.faces.push(face2);
            }
        }

        geom.computeVertexNormals(true);
        geom.computeFaceNormals();
        geom.computeBoundingBox();

        var zMax = geom.boundingBox.max.z;
        var xMax = geom.boundingBox.max.x;

        var mesh = new THREE.Mesh(geom, new THREE.MeshLambertMaterial({
            vertexColors: THREE.FaceColors,
            color: 0xDDDDDD,
        }));
        mesh.translateX(-xMax / 2);
        mesh.translateZ(-zMax / 2);
        scene.add(mesh);
        mesh.name = 'valley';
    };

}

function getHighPoint(geometry, face) {
    var v1 = geometry.vertices[face.a].y;
    var v2 = geometry.vertices[face.b].y;
    var v3 = geometry.vertices[face.c].y;
    return Math.max(v1, v2, v3);
}

function render() {
    renderer.render(scene, camera);
    requestAnimationFrame(render);

}

window.addEventListener("keydown", (e) => {
    if (e.code === "ArrowUp") camera.position.y = camera.position.y + 10;
    else if (e.code === "ArrowDown") camera.position.y = camera.position.y - 10;
    else if (e.code === "ArrowLeft") camera.position.x = camera.position.x - 10;
    else if (e.code === "ArrowRight") camera.position.x = camera.position.x + 10;
});