package edu.sjsu.cmpe.cache.client;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) throws Exception {

        String[] values = new String[] {"a","b","c","d","e","f","g","h", "i", "j"};
        
		System.out.println("Starting Cache Client...");
        
		DistributedCacheService distributedCache1 = new DistributedCacheService("http://localhost:3000");
        DistributedCacheService distributedCache2 = new DistributedCacheService("http://localhost:3001");
        DistributedCacheService distributedCache3 = new DistributedCacheService("http://localhost:3002");

	    List<DistributedCacheService> serverList = new ArrayList<DistributedCacheService>();
        serverList.add(distributedCache1);
        serverList.add(distributedCache2);
        serverList.add(distributedCache3);

	    try {
            ConsistentHash consistentHash = new ConsistentHash(serverList, 3);

            System.out.println("--------------------------PUT Request------------------------------------");
		    for(int i=1; i<=10 ; i++)
            {
                    CacheServiceInterface cache = (CacheServiceInterface) consistentHash.get(i);
                    System.out.println("Putting the key-value pair" + " (" + i + ") => " + values[i-1]);
                    cache.put(i, values[i-1]);
            }

            System.out.println("--------------------------GET Request------------------------------------");
		
            for(int i=1; i<=10 ; i++)
            {
                    CacheServiceInterface cache = (CacheServiceInterface) consistentHash.get(i);
                    System.out.println("Getting the key-value pair" + " (" + i + ") => " + values[i-1]);
                    String value = cache.get(i);
            }

            System.out.println("Exiting Cache Client..");
        }
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ConsistentHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ConsistentHash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
