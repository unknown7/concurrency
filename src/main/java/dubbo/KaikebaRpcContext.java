package dubbo;

import java.util.HashMap;
import java.util.Map;


public class KaikebaRpcContext {
    private static ThreadLocal<KaikebaRpcContext> LOCAL = new ThreadLocal<KaikebaRpcContext>();
    private static ThreadLocal<KaikebaRpcContext> SERVER = new ThreadLocal<KaikebaRpcContext>();
    private final Map<String, String> attachments = new HashMap<String, String>();
    public static KaikebaRpcContext getContext(){
        KaikebaRpcContext kaikebaRpcContext = LOCAL.get();
        if(kaikebaRpcContext==null){
            kaikebaRpcContext = new KaikebaRpcContext();
            LOCAL.set(kaikebaRpcContext);
        }
        return kaikebaRpcContext;
    }

    public static void removeContext(){
        LOCAL.remove();
    }

    public static void removeServerContext(){
        SERVER.remove();
    }

    public static KaikebaRpcContext getServerContext(){
        KaikebaRpcContext kaikebaRpcContext = SERVER.get();
        if(kaikebaRpcContext==null){
            kaikebaRpcContext = new KaikebaRpcContext();
            SERVER.set(kaikebaRpcContext);
        }
        return kaikebaRpcContext;
    }

    public String getAttachment(String key){
        return attachments.get(key);
    }
    public void setAttachment(String key,String value){
        this.attachments.put(key,value);
    }
    public Map<String, String> getAttachments() {
        return attachments;
    }
    public void setAttachments(Map<String, String> attachments){
        this.attachments.putAll(attachments);
    }
    public void clearAttachments(){
        this.attachments.clear();
    }
}