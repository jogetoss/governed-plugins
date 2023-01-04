package org.joget.gcoe;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.joget.apps.app.model.AppDefinition;
import org.joget.apps.app.service.AppUtil;
import org.joget.commons.util.LogUtil;
import org.joget.commons.util.SecurityUtil;
import org.joget.plugin.base.Plugin;
import org.joget.plugin.base.PluginManager;
import org.joget.plugin.property.model.PropertyEditable;
import org.joget.plugin.property.service.PropertyUtil;
import org.joget.workflow.util.WorkflowUtil;
import org.json.JSONObject;

public class GcoeUtil {
    
    public static Plugin getPlugin(Map<String, Object> properties, String url, String key, String id) {
        if (url != null && !url.isEmpty() && key != null && !key.isEmpty() && id != null && !id.isEmpty()) {
            AppDefinition appDef = AppUtil.getCurrentAppDefinition();
            PluginManager pluginManager = (PluginManager) AppUtil.getApplicationContext().getBean("pluginManager");
            
            String data = callApi(url + "/api/gcoe/config/"+appDef.getAppId()+"/"+id, key);
            if (data != null && !data.isEmpty()) {
                try {
                    Map<String,Object> result = PropertyUtil.getProperties(new JSONObject(data));
                    if (result.containsKey("data")) {
                        String json = SecurityUtil.decrypt(result.get("data").toString());
                        
                        JSONObject p = new JSONObject(json);
                        
                        Plugin plugin = pluginManager.getPlugin(p.getString("className"));
                        if (plugin != null) {
                            ((PropertyEditable) plugin).setProperties(PropertyUtil.getProperties(p.getJSONObject("properties")));
                        }
                        return plugin;
                    }
                } catch (Exception e) {
                    LogUtil.error(GcoeUtil.class.getName(), e, "");
                }
            }
        }
        return null;
    }
    
    public static String getPropertiesOptions(String className, String type) {
        return AppUtil.readPluginResource(className, "/properties/options.json", new String[]{type}, true, Activator.MESSAGE_PATH);
    }
    
    public static String getOptions(String url, String key, String type) {
        if (url != null && !url.isEmpty() && key != null && !key.isEmpty() && type != null && !type.isEmpty()) {
            AppDefinition appDef = AppUtil.getCurrentAppDefinition();
            String data = callApi(url + "/api/gcoe/options/"+appDef.getAppId()+"/"+type, key);
            if (data != null && !data.isEmpty()) {
                return data;
            }
        }
        return "[]";
    }
    
    public static String callApi(String url, String key) {
        String jsonUrl = url;
        CloseableHttpClient client = null;
        HttpRequestBase request = null;

        try {
            HttpServletRequest httpRequest = WorkflowUtil.getHttpServletRequest();

            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            URL urlObj = new URL(jsonUrl);

//            if ("https".equals(urlObj.getProtocol())) {
//                SSLContextBuilder builder = new SSLContextBuilder();
//                builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
//                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
//                httpClientBuilder.setSSLSocketFactory(sslsf);
//            }

            client = httpClientBuilder.build();

            request = new HttpGet(jsonUrl);
            request.setHeader("api_id", "API-c83cfb8d-2c98-4672-8e62-b0c5bf76bfb4");
            request.setHeader("api_key", key);
            
            if (httpRequest != null) {
                String referer = httpRequest.getHeader("referer");
                if (referer == null || referer.isEmpty()) {
                    referer = httpRequest.getRequestURL().toString();
                }
                request.setHeader("referer", referer);
            }
            System.out.println(jsonUrl);
            CloseableHttpResponse response = client.execute(request);

            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception ex) {
            LogUtil.error(GcoeUtil.class.getName(), ex, "");
        } finally {
            try {
                if (request != null) {
                    request.releaseConnection();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException ex) {
                LogUtil.error(GcoeUtil.class.getName(), ex, "");
            }
        }
        
        return null;
    }
    
    public static Object getObjectFromMap(String key, Map object) {
        if (key.contains(".")) {
            String subKey = key.substring(key.indexOf(".") + 1);
            key = key.substring(0, key.indexOf("."));

            Map tempObject = (Map) getObjectFromMap(key, object);

            if (tempObject != null) {
                return getObjectFromMap(subKey, tempObject);
            }
        } else {
            if (key.contains("[") && key.contains("]")) {
                String tempKey = key.substring(0, key.indexOf("["));
                int number = Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
                Object tempObjectArray[] = (Object[]) object.get(tempKey);
                if (tempObjectArray != null && tempObjectArray.length > number) {
                    return tempObjectArray[number];
                }
            } else {
                return object.get(key);
            }
        }
        return null;
    }
}
