package com.interfija.masterposmultitenant.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Cliente HTTP genérico para realizar solicitudes a API REST que facilita la realización de peticiones
 * HTTP (GET, POST, PUT, DELETE) utilizando la librería Unirest. Está diseñada para trabajar con objetos
 * serializados en JSON mediante Jackson.
 * <p>
 * Ofrece manejo de cabeceras por defecto, soporte para agregar cabeceras personalizadas,
 * manejo de errores y respuestas automáticamente mapeadas a clases Java.
 *
 * @author Mario Acendra.
 */
public class HttpUtil {

    /**
     * Logger para imprimir errores y eventos importantes durante las peticiones HTTP.
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * Constantes para los métodos HTTP utilizados.
     */
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PUT = "PUT";

    /**
     * MediaType utilizado para especificar que el contenido de las peticiones es JSON codificado en UTF-8.
     * Se utiliza en el cuerpo de las solicitudes POST y PUT.
     */
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * Cliente HTTP reutilizable para ejecutar todas las solicitudes HTTP.
     */
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Mapper utilizado para serializar y deserializar objetos JSON usando Jackson.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Realiza una petición GET sin cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param responseType Clase del tipo de respuesta esperada
     * @param <T>          Tipo de la respuesta
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T> T get(String url, Class<T> responseType) {
        return get(url, responseType, null);
    }

    /**
     * Realiza una petición GET con cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param responseType Clase del tipo de respuesta esperada
     * @param headers      Cabeceras adicionales
     * @param <T>          Tipo de la respuesta
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T> T get(String url, Class<T> responseType, Map<String, String> headers) {
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(mergeHeaders(headers)))
                .get()
                .build();

        return executeRequest(request, responseType, GET, url);
    }

    /**
     * Realiza una petición POST sin cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param requestBody  Objeto a enviar en el cuerpo
     * @param responseType Clase del tipo de respuesta esperada
     * @param <T>          Tipo de la respuesta
     * @param <R>          Tipo del cuerpo enviado
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T, R> T post(String url, R requestBody, Class<T> responseType) {
        return post(url, requestBody, responseType, null);
    }

    /**
     * Realiza una petición POST con cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param requestBody  Objeto a enviar en el cuerpo
     * @param responseType Clase del tipo de respuesta esperada
     * @param headers      Cabeceras adicionales
     * @param <T>          Tipo de la respuesta
     * @param <R>          Tipo del cuerpo enviado
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T, R> T post(String url, R requestBody, Class<T> responseType, Map<String, String> headers) {
        try {
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            RequestBody body = RequestBody.create(jsonBody, JSON);

            Request request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(mergeHeaders(headers)))
                    .post(body)
                    .build();

            return executeRequest(request, responseType, POST, url);
        } catch (Exception e) {
            logHttpException(POST, url, e);
            return null;
        }
    }

    /**
     * Realiza una petición PUT sin cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param requestBody  Objeto a enviar en el cuerpo
     * @param responseType Clase del tipo de respuesta esperada
     * @param <T>          Tipo de la respuesta
     * @param <R>          Tipo del cuerpo enviado
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T, R> T put(String url, R requestBody, Class<T> responseType) {
        return put(url, requestBody, responseType, null);
    }

    /**
     * Realiza una petición PUT con cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param requestBody  Objeto a enviar en el cuerpo
     * @param responseType Clase del tipo de respuesta esperada
     * @param headers      Cabeceras adicionales
     * @param <T>          Tipo de la respuesta
     * @param <R>          Tipo del cuerpo enviado
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T, R> T put(String url, R requestBody, Class<T> responseType, Map<String, String> headers) {
        try {
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            RequestBody body = RequestBody.create(jsonBody, JSON);

            Request request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(mergeHeaders(headers)))
                    .put(body)
                    .build();

            return executeRequest(request, responseType, PUT, url);
        } catch (Exception e) {
            logHttpException(PUT, url, e);
            return null;
        }
    }

    /**
     * Realiza una petición DELETE sin cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param responseType Clase del tipo de respuesta esperada
     * @param <T>          Tipo de la respuesta
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T> T delete(String url, Class<T> responseType) {
        return delete(url, responseType, null);
    }

    /**
     * Realiza una petición DELETE con cabeceras personalizadas.
     *
     * @param url          URL del endpoint
     * @param responseType Clase del tipo de respuesta esperada
     * @param headers      Cabeceras adicionales
     * @param <T>          Tipo de la respuesta
     * @return Objeto de tipo T o null si ocurre error
     */
    public static <T> T delete(String url, Class<T> responseType, Map<String, String> headers) {
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(mergeHeaders(headers)))
                .delete()
                .build();

        return executeRequest(request, responseType, DELETE, url);
    }

    /**
     * Ejecuta una solicitud HTTP utilizando OkHttp y deserializa la respuesta en un objeto del tipo especificado.
     *
     * @param request      Objeto {@link okhttp3.Request} que contiene la información de la solicitud HTTP.
     * @param responseType Clase del tipo al que se desea deserializar la respuesta.
     * @param method       Método HTTP utilizado (por ejemplo, "GET", "POST"), usado para fines de registro/logging.
     * @param url          URL del endpoint al que se realiza la solicitud, usado para fines de registro/logging.
     * @param <T>          Tipo de la respuesta esperada.
     * @return Objeto deserializado de tipo {@code T} si la solicitud fue exitosa y la respuesta es válida;
     * {@code null} si ocurrió un error o la respuesta no fue exitosa.
     */

    private static <T> T executeRequest(Request request, Class<T> responseType, String method, String url) {
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, responseType);
            } else {
                logHttpError(method, url, response.code(), response.message());
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, responseType);
            }
        } catch (Exception e) {
            logHttpException(method, url, e);
        }
        return null;
    }

    /**
     * Combina las cabeceras por defecto con las personalizadas.
     *
     * @param customHeaders Cabeceras personalizadas
     * @return Mapa combinado de cabeceras
     */
    private static Map<String, String> mergeHeaders(Map<String, String> customHeaders) {
        Map<String, String> result = new HashMap<>();
        result.put("Content-Type", "application/json");
        result.put("Accept", "application/json");

        if (customHeaders != null) {
            result.putAll(customHeaders);
        }
        return result;
    }

    /**
     * Imprime un error en el log cuando ocurre una excepción durante la petición HTTP.
     *
     * @param method    Tipo de método HTTP
     * @param url       URL solicitada
     * @param exception Excepción ocurrida
     */
    private static void logHttpException(String method, String url, Exception exception) {
        logger.error("Error durante la petición HTTP '{}' a '{}' -> {}", method, url, exception.getMessage());
    }

    /**
     * Imprime un error en el log cuando la respuesta HTTP no tiene estado 200.
     *
     * @param method     Tipo de método HTTP
     * @param url        URL solicitada
     * @param statusCode Estado de la respuesta
     * @param message    Mensaje de la respuesta
     */
    private static void logHttpError(String method, String url, int statusCode, String message) {
        logger.error("HTTP '{}' respuesta '{}': '{}' -> {}", method, url, statusCode, message);
    }

}
