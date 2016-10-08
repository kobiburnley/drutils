package drutils.http;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Kobi
 * Date: 11/18/15
 * Time: 10:17 AM
 */
public class HttpRequest {

    private String body;
    int responseCode;


    public int getResponseCode() {
        return responseCode;
    }

    public HttpRequest addRejector(Rejector rejector) {
        this.rejectors.add(rejector);
        return this;
    }

//    public HttpRequest setParams(List<KeyValue<String, String>> qp) {
//        this.qp =
//                CollectionsUtils.join(
//                        CollectionsUtils.transform(qp, new Function<KeyValue<String,String>, String>() {
//                            @Override
//                            public String apply(KeyValue<String, String> params) {
//                                return params.getKey() + "=" + Service.UEncode(params.getValue());
//                            }
//                        }),
//                        "&"
//                );
//
//        return this;
//    }

    public interface Rejector {
        boolean reject(String data);

        String reason();
    }


    public String getBody() {
        return body;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    private ContentType contentType;

    public HttpRequest setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    enum Method {
        GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");
        private String name;

        Method(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum ContentType {
        JSON("application/json; charset=utf-8");
        String value;

        ContentType(String s) {
            value = s;
        }

        public String getValue() {
            return value;
        }
    }

    public static final String USER_AGENT = "Mozilla/5.0";

    private int connectTimeout = 15;
    private int readTimeout = 30;
    private Map<String, String> params;
    private Method method;
    private String url;
    private String path;
    private KVList<String, String> apiKeys;
    private Map<String, String> headers;

    private List<Rejector> rejectors;

    private HttpRequest(String url) throws IOException {
        this.url = url;
        this.path = "";
        params = new HashMap<>();
        headers = new HashMap<>();
        apiKeys = new KVList<>();
        rejectors = new ArrayList<>();
    }

    public HttpRequest setMethod(Method method) throws ProtocolException {
        this.method = method;
        return this;
    }

    public HttpRequest setRequestProperty(String property, String value) {
        return this;
    }

    public HttpRequest addParameter(String name, String value) {
        params.put(name, value);
        return this;
    }

    public HttpRequest addParameter(KeyValue<String, String> keyValue) {
        params.put(keyValue.getKey(), keyValue.getValue());
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public HttpRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public HttpRequest setApiKeys(KVList<String, String> apiKeys) {
        this.apiKeys = apiKeys;
        return this;
    }

    public HttpRequest setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public HttpRequest setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public HttpRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public HttpRequest addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public HttpURLConnection connect() throws IOException {
        String fullURL = String.format("%s%s", url, path);
        boolean withParams = params.keySet().size() > 0;
        String queryString = createQueryString(params, null);
        if (withParams && method == Method.GET) {
            fullURL += "?" + queryString;
        }

        URL obj = new URL(fullURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);

        if (contentType != null) {
            con.setRequestProperty("Content-Type", contentType.getValue());
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        con.setRequestMethod(method.getName());
        con.setConnectTimeout(connectTimeout * 1000);
        con.setReadTimeout(readTimeout * 1000);
//                  con.setRequestProperty(property, value);

        if (method != Method.GET) {
            con.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            if (contentType == null && withParams) {
                out.write(queryString);
            } else if (contentType == ContentType.JSON) {
                out.write(body);
            }
            out.flush();
            out.close();
        }

        System.out.println(String.format("\nSending '%s' request to URL : %s", method.getName(), fullURL));
        System.out.println("GET parameters : " + queryString);
        responseCode = con.getResponseCode();
        return con;
    }

    public String send() {

        try {
            HttpURLConnection con = connect();
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return response.toString();
        } catch (MalformedURLException e) {
            System.out.println("a");
        } catch (UnsupportedEncodingException e) {
            System.out.println("b");
        } catch (ProtocolException e) {
            System.out.println("c");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static String createQueryString(Map<String, String> params, KeyValue<String, String> keyValue) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> pair : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            if (pair.getKey() != null && pair.getValue() != null) {
                result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
        }

        if (keyValue != null && !keyValue.getKey().equals("0")) {
            if (first)
                first = false;
            else
                result.append("&");

            if (keyValue.getKey() != null && keyValue.getValue() != null) {
                result.append(URLEncoder.encode(keyValue.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(keyValue.getValue(), "UTF-8"));
            }
        }
        return result.toString();
    }

    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public static HttpRequest get(String url) throws IOException {
        return new HttpRequest(url).setMethod(Method.GET);
    }

    public static HttpRequest put(String url) throws IOException {
        return new HttpRequest(url).setMethod(Method.PUT);
    }

    public static HttpRequest post(String url) throws IOException {
        return new HttpRequest(url).setMethod(Method.POST);
    }

    public static HttpRequest delete(String url) throws IOException {
        return new HttpRequest(url).setMethod(Method.DELETE);
    }
}
