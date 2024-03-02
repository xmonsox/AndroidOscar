<?php 
    header("Access-Control-Allow-Origin: * "); // Permite el acceso desde cualquier origen, o usa "http://localhost" si solo quieres permitirlo desde localhost.
    header("Access-Control-Allow-Methods: GET, POST");
    header("Access-Control-Allow-Headers: Content-Type");
    include 'Conexion.php';

    if (!empty($_GET['id_cuestionario'])) {
        $id_cuestionario = $_GET['id_cuestionario'];

        // Obtener una pregunta aleatoria del cuestionario
        $consulta_pregunta_aleatoria = $base_de_datos->prepare("SELECT preguntas.id, preguntas.descripcion, preguntas.id_correcta, preguntas.url_imagen
                                                               FROM preguntas
                                                               JOIN respuestas ON preguntas.id = respuestas.id_pregunta
                                                               WHERE respuestas.id_cuestionario = :id_cuestionario
                                                               ORDER BY RAND()
                                                               LIMIT 1");
        $consulta_pregunta_aleatoria->bindParam(':id_cuestionario', $id_cuestionario);
        $consulta_pregunta_aleatoria->execute();
        $pregunta_aleatoria = $consulta_pregunta_aleatoria->fetch(PDO::FETCH_ASSOC);

        if($pregunta_aleatoria){
            // Obtener opciones de la pregunta aleatoria
            $opciones = $base_de_datos->prepare("SELECT * FROM opciones WHERE id_pregunta = :id_pregunta");
            $opciones->bindParam(':id_pregunta', $pregunta_aleatoria['id']);
            $opciones->execute();
            $opciones_pregunta = $opciones->fetchAll(PDO::FETCH_ASSOC);

            $respuesta = [
                'status' => true,
                'pregunta' => $pregunta_aleatoria,
                'opciones' => $opciones_pregunta
            ];

            echo json_encode($respuesta);
        } else {
            $respuesta = [
                'status' => false,
                'message' => "No se encontraron preguntas para este cuestionario."
            ];
            echo json_encode($respuesta);
        }
    } else {
        $respuesta = [
            'status' => false,
            'message' => "ERROR: Datos GET no proporcionados.",
            '$_GET' => $_GET,
            '$_POST' => $_POST
        ];
        echo json_encode($respuesta);
    }
?>
