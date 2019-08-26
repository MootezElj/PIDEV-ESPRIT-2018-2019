/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.utilisateurs;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import java.io.File;
import java.io.FileInputStream;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



import java.io.FileNotFoundException;

/**
 *
 * @author SLIMEN
 */
public class PartageFb {
     public void partager(String titre,String image) throws FileNotFoundException{
    
        
          String domain="https://www.google.fr/";
          //domain="https://google.fr/";
         String appId=" 394239627827005";
         String appSecret="394239627827005|ryZs6UwUnD1egAUV2RVo2eAguZ0";
         String authURL="https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId+"&redirect_uri="+domain
                 +"&scope=ads_management,publish_actions";
         
         System.setProperty("webdriver.chrome.driver", "api/chromedriver_win32/chromedriver.exe");
         WebDriver driver= new ChromeDriver();
         driver.get(authURL);
         String accessToken="EAAFmjxD5Fz0BAEhaF3RkYqhjwuc0lwEQmNIV8i3GtmGDK9OUGE0gH8UzBDT5GdVjmxlYa1ubAje6hy46SJpdnfXjRmoCvON9P74ZCb8jtgWz94r4yS02FE1V4et9CV4TzTsHiJkjrJns920BrzQ8S841dhvdyQF2ltwvEMgypxXHYD4gAzkd74ZCeIUYuWrqHGLMhYFwZDZD" ;
         
         boolean ok=true;
         while(ok)
         {
             if ( (! driver.getCurrentUrl().contains("facebook.com")) && (driver.getCurrentUrl()!=authURL) )
             {
                 String url =driver.getCurrentUrl();
                 accessToken =url.replaceAll(".*#access_token=(.+)&.*", "$1");
                 System.out.println(accessToken);
                
                 ok=false;
              }
             
         }
         
         System.out.println("act:"+accessToken);
         driver.quit();
         FacebookClient fbClient = new DefaultFacebookClient(accessToken);
              User me = fbClient.fetchObject("me", User.class);
             // System.out.println(me.g0<etUsername());
              FileInputStream fs=new FileInputStream(new File(image));
//              FacebookType publishPhotoResponse = fbClient.publish("me/photos", FacebookType.class,
//  BinaryAttachment.with(image,fs),
//  Parameter.with("message", titre));
            
             FacebookType publishMessageResponse =
            fbClient.publish("me/feed", FacebookType.class,
                    com.restfb.Parameter.with("message","Evenement"));
          

              System.out.println("Published message ID: " + publishMessageResponse.getId());

    /**
     *
     */
    
        
    
}
//     

}
