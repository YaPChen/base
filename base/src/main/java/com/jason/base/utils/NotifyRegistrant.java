package com.jason.base.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public class NotifyRegistrant {

    private static NotifyRegistrant INSTANCE = new NotifyRegistrant();
    
    private List<Handler> mRegistrant = null;
    
    /** 鎺ユ敹鍒伴�氱煡浜嬩欢锛屽弬鏁颁负Bundle */
    public static final int EVENT_NOTIFY_MESSAGE_RECEIVED = 0x3000;
  
    private NotifyRegistrant() {}
    
    public static NotifyRegistrant getInstance() {
        return INSTANCE;
    }
    /**
     * 娉ㄥ唽Handler
     * @param handler  浜嬩欢澶勭悊鍣�
     * @return 娉ㄥ唽鏄惁鎴愬姛
     */
     public boolean register(Handler handler){
         if(null == mRegistrant){
             mRegistrant = new ArrayList<Handler>();
         }
         
         if(!isHasExisted(handler)){
             mRegistrant.add(handler);
         }
         return true;
     }
     
     /**
      * 鍘绘敞鍐孒andler
      * @param handler  浜嬩欢澶勭悊鍣�
      * @return None
      */
     public void unRegister(Handler handler){
         if(null != mRegistrant){
             mRegistrant.remove(handler);
         }
     }
     
     /**
      * 娑堟伅閫氱煡
      * @param bundle  灏佽鐨勬秷鎭綋
      * @return None
      */
     public void notify(Bundle bundle){
         if(null == mRegistrant){
             return;
         }
         for(Handler h : mRegistrant){
            h.sendMessage(h.obtainMessage(EVENT_NOTIFY_MESSAGE_RECEIVED, bundle));
         }
     }  
     
     /**
      * 娑堟伅閫氱煡
      * @param what  灏佽鐨勬秷鎭綋绫诲瀷
      * @return None
      */
     public void notify(int what){
         if(null == mRegistrant){
             return;
         }
         for(Handler h : mRegistrant){
            h.sendMessage(h.obtainMessage(what));
         }
     }
     
     /**
      * 娑堟伅閫氱煡
      * @param what  灏佽鐨勬秷鎭綋绫诲瀷
      * @return None
      */
     public void notify(Handler handler, int what){
    	 if (handler != null) {
    		 handler.sendMessage(handler.obtainMessage(what));
    	 }
     }
     
     /**
      * 娑堟伅閫氱煡
      * @param msg
      * @return None
      */
     public void notify(Handler handler, Message msg){
    	 if (handler != null) {
    		 handler.sendMessage(msg);
    	 }
     }
     
     /**
      * 娑堟伅閫氱煡
      * @param msg
      * @return None
      */
     public void notify(Message msg){
         if(null != mRegistrant){
        	 for(Handler h : mRegistrant){
                 h.sendMessage(msg);
              }
         }
     }
     
     /**
      * 鍒ゆ柇娉ㄥ唽鍣ㄤ腑鏄惁鏈夐噸澶嶇殑浜嬩欢澶勭悊鍣�
      * @param handler  浜嬩欢澶勭悊鍣�
      * @return true閲嶅,false涓嶉噸澶�
      */
     private boolean isHasExisted(Handler handler){
         if(null == mRegistrant){
             return false;
         }
         for(Handler h : mRegistrant){
             if(h == handler){
                 return true;
             }
         }
         return false;
     }
}
