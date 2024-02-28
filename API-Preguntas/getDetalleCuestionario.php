<?php 
	header("Access-Control-Allow-Origin: * "); // Permite el acceso desde cualquier origen, o usa "http://localhost" si solo quieres permitirlo desde localhost.
	header("Access-Control-Allow-Methods: GET, POST");
	header("Access-Control-Allow-Headers: Content-Type");

    include 'Conexion.php';

    if (!empty($_GET['id_cuestionario'])) {
	    $consulta = $base_de_datos->query("SELECT preguntas.id, preguntas.descripcion, preguntas.id_correcta, preguntas.url_imagen, respuestas.respuesta, respuestas.estado
            FROM preguntas
            JOIN respuestas ON preguntas.id = respuestas.id_pregunta
            WHERE id_cuestionario = ".$_GET['id_cuestionario']);
        $consulta_opciones = $base_de_datos->query("SELECT *
        FROM opciones
        JOIN preguntas ON opciones.id_pregunta = preguntas.id
        JOIN respuestas ON respuestas.id_pregunta = preguntas.id
        WHERE id_cuestionario = ".$_GET['id_cuestionario']);
	    $datos = $consulta->fetchAll(PDO::FETCH_ASSOC);
        $datos_opciones = $consulta_opciones->fetchAll(PDO::FETCH_ASSOC);
		$cantidadRespuestas = count($datos);

        $respuestas = [
            'pregunta' => [
                'prueba' => [],
                'opciones' => [],
            ],
            
        ];

        for ($i = 0; $i < $cantidadRespuestas; $i++) {
            array_push($respuestas["pregunta"]["prueba"], $datos[$i]);
            for ($j = 0; $j < 2; $j++) {
                array_push($respuestas["pregunta"]["opciones"], $datos_opciones[$j]);
            }
        }

        $respuesta = [
            'status' => true,
            'respuestas' => $respuestas,
            
        ];
        echo json_encode($respuesta);
	}else{
        $respuesta = [
                        'status' => false,
                        'mesagge' => "ERROR##DATOS##GET",
                        '$_GET' => $_GET,
                        '$_POST' => $_POST
                    ];
        echo json_encode($respuesta);
    }
?>